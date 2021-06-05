package api;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;
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
		return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
				HttpStatus.BAD_REQUEST);
	}

	@PostMapping(path = "/uploadProject")
	public ResponseEntity<String> uploadProject(@RequestParam("file") MultipartFile file,@RequestParam("course") String course,@RequestParam("userId") String userId, HttpServletResponse r) throws JsonProcessingException {

		Map<String, Object> map = new HashMap<>();

		if (uploadService.storeProject(file, course, userId)) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "Project stored!");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		}

		map.put("status", HttpStatus.BAD_REQUEST);
		map.put("code", "400");
		map.put("message", "Couldn't store project!");
		return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
				HttpStatus.BAD_REQUEST);

	}
	
	@GetMapping(path = "/downloadFile/fileName={fileName}&courseName={courseName}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> sendFile(@PathVariable("fileName") String fileName, @PathVariable("courseName") String courseName, HttpServletResponse r, HttpServletRequest request) throws IOException {
		Resource file = uploadService.sendFile(fileName, courseName);

		if(file!=null) {
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(request.getServletContext().getMimeType(file.getFile().getAbsolutePath())))
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);

		}else {
			return ResponseEntity.badRequest().body(null);
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

	@DeleteMapping(path = "/deleteFile/fileName={fileName}&courseName={courseName}")
	public ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName,@PathVariable("courseName") String courseName, HttpServletResponse r) throws JsonProcessingException {

		Map<String, Object> map = new HashMap<>();

		if (uploadService.deleteFile(fileName, courseName)) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "File deleted!");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		}

		map.put("status", HttpStatus.BAD_REQUEST);
		map.put("code", "400");
		map.put("message", "Couldn't delete file!");
		return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
				HttpStatus.BAD_REQUEST);

	}




}
