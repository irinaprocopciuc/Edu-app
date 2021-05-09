package dao;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


public interface UploadFileInterface {
	

	boolean storeFile(MultipartFile fileDetails,String courseName);
	
	boolean sendFile(String fileName,String courseName);
}
