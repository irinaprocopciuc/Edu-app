package dao;

import org.springframework.web.multipart.MultipartFile;


public interface UploadFileInterface {
	

	boolean storeFile(MultipartFile fileDetails,String courseName);
}
