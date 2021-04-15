package api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import service.FileUploadService;

@RequestMapping(value = "")
@RestController
public class FileUploadController {

	private final FileUploadService uploadService;
	
	@Autowired
	public FileUploadController(FileUploadService uploadService) {
		this.uploadService = uploadService;
	}
	
	@PostMapping(path = "/uploadFile", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestParam("fileDetails") MultipartFile fileDetails, HttpServletResponse r) throws JsonProcessingException {
		System.out.println("ddddddddddddddddddddddddddddddd");
		Map<String,Object> map = new HashMap<>();
		boolean resp = uploadService.uploadFile(fileDetails);
        if(resp == false){
            map.put("status", HttpStatus.UNAUTHORIZED);
            map.put("code", "401");
            map.put("message","File not uploaded!");
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map), HttpStatus.UNAUTHORIZED);
        }else{
            map.put("status", HttpStatus.OK);
            map.put("code", "200");
            map.put("message","File uploaded!");
            return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map), HttpStatus.OK);
        }
	}
}
