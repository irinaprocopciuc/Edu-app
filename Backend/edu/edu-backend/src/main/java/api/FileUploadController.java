package api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
	public ResponseEntity<String> login(@RequestParam("file") MultipartFile file,@RequestParam("course") String course, HttpServletResponse r) throws JsonProcessingException {

		Map<String, Object> map = new HashMap<>();

		System.out.println(file.getName());
		
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
}
