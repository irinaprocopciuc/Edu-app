package dao;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import dao.Inteface.UploadFileInterface;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository("UploadFile")
public class UploadFile implements UploadFileInterface {

	@Override
	public boolean storeFile(MultipartFile fileDetails, String courseName) {
		try {
			System.out.println(fileDetails.getName());
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

}
