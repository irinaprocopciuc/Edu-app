package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.Inteface.UploadFileInterface;

@Service
public class FileUploadService {

	private final UploadFileInterface db;

	@Autowired
	public FileUploadService(@Qualifier("UploadFile") UploadFileInterface db) {
		this.db = db;
	}

	public boolean storeFile(MultipartFile fileDetails,String courseName) {
		return db.storeFile(fileDetails,courseName);
	}

	public boolean deleteFile(String fileName,String courseName) {
		return db.deleteFile(fileName,courseName);
	}

	public Resource sendFile(String fileName, String courseName) {
		return db.sendFile(fileName,courseName);
	}

	public boolean storeProject(MultipartFile fileDetails,String courseName, String userId) {
		return db.storeProject(fileDetails,courseName, userId);
	}
}
