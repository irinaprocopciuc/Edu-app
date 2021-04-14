package dao;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Repository("UploadFile")
public class UploadFile implements UploadFileInterface{

	@Override
	public boolean uploadFile(MultipartFile fileDetails) {
		System.out.println(fileDetails);
		 try {
	            Path copyLocation = Paths
	                .get("C:\\Users\\iprocopc\\OneDrive - Centric\\Desktop\\Test-upload" + File.separator + StringUtils.cleanPath(fileDetails.getOriginalFilename()));
	            Files.copy(fileDetails.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return false;
	}

	

}
