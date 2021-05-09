package dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
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
	public boolean sendFile(String fileName, String courseName) {
		
		
		if(new File(System.getProperty("user.dir")+"\\courseFiles\\",courseName).exists()) {
			File dir = new File(System.getProperty("user.dir")+"\\courseFiles\\"+courseName+"\\");
			
			for (File file : dir.listFiles()) {
                if(file.getName().equals(fileName)) {
                	return true;
                }
	        }
		}else {
			return false;
		}
		return false;
	}

}
