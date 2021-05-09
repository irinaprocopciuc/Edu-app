package api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.FileToUpload;
import service.FileUploadService;

@RequestMapping(value = "")
@RestController
public class FileUploadController {

	private final FileUploadService uploadService;

	@Autowired
	public FileUploadController(FileUploadService uploadService) {
		this.uploadService = uploadService;
	}

	@PostMapping(path = "/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("course") String course, HttpServletResponse r) throws JsonProcessingException {

		Map<String, Object> map = new HashMap<>();

		
		if (uploadService.storeFile(file, course)) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "File stored!");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		}

		map.put("status", HttpStatus.BAD_REQUEST);
		map.put("code", "400");
		map.put("message", "Couldn't store file!");
		map.put("response", "");
		return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
				HttpStatus.BAD_REQUEST);

	}
	
	@GetMapping(path = "/downloadFile/fileName={fileName}&courseName={courseName}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody byte[] sendFile(@PathVariable("fileName") String fileName,@PathVariable("courseName") String courseName, HttpServletResponse r) throws IOException {


		if(uploadService.sendFile(fileName, courseName)) {
			InputStream in = getClass().getResourceAsStream(System.getProperty("user.dir")+"\\courseFiles\\"+courseName+"\\");
			return IOUtils.toByteArray(in);
		}else {
			return null;
		}
		/*
		Map<String, Object> map = new HashMap<>();

		Map<String,Object> file = uploadService.sendFile(fileName, courseName);
		
		if (file.containsKey("message")) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", file.get("message"));
			map.put("file", file.get("file"));
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		}

		map.put("status", HttpStatus.BAD_REQUEST);
		map.put("code", "400");
		map.put("error", file.get("message"));
		return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
				HttpStatus.BAD_REQUEST);
		*/
	}
	
}
