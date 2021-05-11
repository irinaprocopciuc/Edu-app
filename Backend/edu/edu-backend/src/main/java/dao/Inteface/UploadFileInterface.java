package dao.Inteface;

import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface UploadFileInterface {


	boolean storeFile(MultipartFile fileDetails,String courseName);

	boolean deleteFile(String fileName,String courseName);

	Resource sendFile(String fileName, String courseName);
}
