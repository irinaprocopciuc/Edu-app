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

@RequestMapping("")
@RestController
public class CoursesController {

	private final CoursesService coursesService;

	@Autowired
	public CoursesController(CoursesService coursesService) {
		this.coursesService = coursesService;
	}
	
	@GetMapping(path = "/courses/userId={userId}")
	public ResponseEntity<String> getTrips(@PathVariable("userId") String userId) throws JsonProcessingException {
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
}
