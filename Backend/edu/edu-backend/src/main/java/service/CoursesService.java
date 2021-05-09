package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.CoursesInterface;

@Service
public class CoursesService {
	private final CoursesInterface conn;
	

	@Autowired
	public CoursesService(@Qualifier("Courses") CoursesInterface connection) {
		this.conn = connection;
	}
	
	public List<Map<String, String>> getCourses(String userId) {
		return conn.getCourses(userId);
	}
	
	public List<String> getCourseFiles(String courseName) {
		return conn.getCourseFiles(courseName);
	}
}
