package api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import service.CoursesService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/courses")
@RestController
public class CoursesController {

	private final CoursesService coursesService;

	@Autowired
	public CoursesController(CoursesService coursesService) {
		this.coursesService = coursesService;
	}
	
	@GetMapping(path = "/userId={userId}")
	public ResponseEntity<String> getCourses(@PathVariable("userId") String userId) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, String>> coursesList = coursesService.getCourses(userId);
		if (!coursesList.isEmpty()) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "Courses retrieved!");
			map.put("response", coursesList);
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		} else {
			map.put("status", HttpStatus.NOT_FOUND);
			map.put("code", "404");
			map.put("message", "Courses for user not found!");
			map.put("response", "");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/teacherId={teacherId}")
	public ResponseEntity<String> getCoursesForTeacher(@PathVariable("teacherId") String teacherId) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, String>> coursesList = coursesService.getCoursesForTeacher(teacherId);
		if (!coursesList.isEmpty()) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "Courses retrieved!");
			map.put("response", coursesList);
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		} else {
			map.put("status", HttpStatus.NOT_FOUND);
			map.put("code", "404");
			map.put("message", "Courses for teacher not found!");
			map.put("response", "");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/getCourseFiles/courseName={courseName}")
	public ResponseEntity<String> getCourseFiles(@PathVariable("courseName") String courseName) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		List<String> filesList = coursesService.getCourseFiles(courseName);
		if (filesList != null) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "Files list retrieved!");
			map.put("response", filesList);
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		} else {
			map.put("status", HttpStatus.NOT_FOUND);
			map.put("code", "404");
			map.put("message", "Files for course couldn't be found!");
			map.put("response", "");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/getCourseUserProjects/courseName={courseName}&userId={userId}")
	public ResponseEntity<String> getCourseUserProjects(@PathVariable("courseName") String courseName,@PathVariable("userId") String userId) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		List<String> projectList = coursesService.getCourseUserProjects(courseName,userId);
		if (projectList != null) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "Projects for user retrieved!");
			map.put("response", projectList);
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		} else {
			map.put("status", HttpStatus.NOT_FOUND);
			map.put("code", "404");
			map.put("message", "Projects for user couldn't be found!");
			map.put("response", "");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/getCourseProjects/courseName={courseName}")
	public ResponseEntity<String> getCourseProjects(@PathVariable("courseName") String courseName) throws JsonProcessingException, SQLException {
		Map<String, Object> map = new HashMap<>();
		List<Map<String,String>> projectList = coursesService.getCourseProjects(courseName);
		if (projectList != null) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "Projects for course retrieved!");
			map.put("response", projectList);
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		} else {
			map.put("status", HttpStatus.NOT_FOUND);
			map.put("code", "404");
			map.put("message", "Projects for course couldn't be found!");
			map.put("response", "");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/downloadFile/fileName={fileName}&courseName={courseName}&userId={userId}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> sendStudentProject(@PathVariable("fileName") String fileName, @PathVariable("courseName") String courseName, @PathVariable("userId") String userId, HttpServletResponse r, HttpServletRequest request) throws IOException {
		Resource file = coursesService.sendStudentProject(fileName, courseName, userId);

		if(file!=null) {
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(request.getServletContext().getMimeType(file.getFile().getAbsolutePath())))
					.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getFilename() + "\"")
					.body(file);

		}else {
			return ResponseEntity.badRequest().body(null);
		}
	}

}
