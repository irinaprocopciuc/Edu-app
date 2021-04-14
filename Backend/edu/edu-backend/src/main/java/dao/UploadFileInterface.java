package dao;

import org.springframework.web.multipart.MultipartFile;


public interface UploadFileInterface {
	

	boolean uploadFile(MultipartFile fileDetails);
}
