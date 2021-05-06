package api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import service.UserService;

@RequestMapping("")
@RestController
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(path = "/users")
	public ResponseEntity<String> getUsers() throws JsonProcessingException {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, String>> usersList = userService.getUsers();
		if (!usersList.isEmpty()) {
			map.put("status", HttpStatus.OK);
			map.put("code", "200");
			map.put("message", "Users retrieved!");
			map.put("response", usersList);
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.OK);
		} else {
			map.put("status", HttpStatus.NOT_FOUND);
			map.put("code", "404");
			map.put("message", "Users not found!");
			map.put("response", "");
			return new ResponseEntity<>(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(map),
					HttpStatus.NOT_FOUND);
		}
	}
}
