package api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import service.CoursesService;

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
	
	@GetMapping(path = "/getCourseFiles/courseName={courseName}")
	public ResponseEntity<String> getCourseFiles(@PathVariable("courseName") String courseName) throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		List<String> filesList = coursesService.getCourseFiles(courseName);
		if (!filesList.isEmpty()) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "Files list retrieved!");
			map.put("response", filesList);
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		} else {
			map.put("status", HttpStatus.NOT_FOUND);
			map.put("code", "404");
			map.put("message", "Course couldn't be found!");
			map.put("response", "");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.NOT_FOUND);
		}
	}
	
}
