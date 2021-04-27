package dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
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

}
