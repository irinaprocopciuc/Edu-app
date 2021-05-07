package model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileToUpload {

	private final MultipartFile file;



	public FileToUpload(@JsonProperty("file") MultipartFile file) {
		this.file = file;
	}


	public MultipartFile getFile() {
		return file;
	}


	
}
