package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.UploadFileInterface;

@Service
public class FileUploadService {

	private final UploadFileInterface db;

	@Autowired
	public FileUploadService(@Qualifier("UploadFile") UploadFileInterface db) {
		this.db = db;
	}
	
	public boolean uploadFile(MultipartFile fileDetails) {
		return db.uploadFile(fileDetails);
	}
	
}
