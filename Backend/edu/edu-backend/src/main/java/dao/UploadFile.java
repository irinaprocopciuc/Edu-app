package dao;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import dao.Inteface.UploadFileInterface;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository("UploadFile")
public class UploadFile implements UploadFileInterface {

	private static Connection conn;

	public UploadFile() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "root");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public boolean storeFile(MultipartFile fileDetails, String courseName) {
		try {
			byte[] bytes = fileDetails.getBytes();
			File courseFiles = new File(System.getProperty("user.dir") + "\\courseFiles");
			if (!courseFiles.exists()) {
				courseFiles.mkdir();
			}

			courseFiles = new File(System.getProperty("user.dir") + "\\courseFiles\\" + courseName);
			if (!courseFiles.exists()) {
				courseFiles.mkdir();
			}

			Path path = Paths.get(System.getProperty("user.dir") + "\\courseFiles\\" + courseName + "\\" + fileDetails.getOriginalFilename());
			Files.write(path, bytes);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean storeProject(MultipartFile fileDetails, String courseName, String userId) {
		try {
			System.out.println(fileDetails.getName());
			byte[] bytes = fileDetails.getBytes();
			File projectsFolder = new File(System.getProperty("user.dir") + "\\projects");
			if (!projectsFolder.exists()) {
				projectsFolder.mkdir();
			}

			File projectCourse = new File(System.getProperty("user.dir") + "\\projects\\" + courseName);
			if (!projectCourse.exists()) {
				projectCourse.mkdir();
			}

			if(!findUserById(userId)){
				return false;
			}
			File userProject = new File(System.getProperty("user.dir") + "\\projects\\" + courseName+"\\"+userId);
			if (!userProject.exists()) {
				userProject.mkdir();
			}

			Path path = Paths.get(System.getProperty("user.dir") + "\\projects\\" + courseName + "\\"+ userId + "\\" + fileDetails.getOriginalFilename());
			Files.write(path, bytes);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean deleteFile(String fileName, String courseName) {
		File fileToDelete = new File(System.getProperty("user.dir")+"\\courseFiles\\"+courseName+"\\"+fileName);
		if(fileToDelete.exists()){
			fileToDelete.delete();
			return true;
		}
		return false;
	}

	@Override
	public Resource sendFile(String fileName, String courseName) {

		if(new File(System.getProperty("user.dir")+"\\courseFiles\\"+courseName+"\\"+fileName).exists()){
			Path filePath = Paths.get(System.getProperty("user.dir")+"\\courseFiles\\"+courseName+"\\"+fileName);
			try {
				Resource fileToReturn = new UrlResource(filePath.toUri());
				return fileToReturn;

			} catch (MalformedURLException e) {
				return null;
			}
		}
		return null;
	}

	private boolean findUserById(String userId){
		try {
			Statement stmt = conn.createStatement();

			ResultSet res = stmt.executeQuery("select name from user where idUser ='"+userId+"';");

			if(res.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
