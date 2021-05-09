package dao;

import java.util.List;
import java.util.Map;

public interface CoursesInterface {
	public List<Map<String, String>> getCourses(String userId);
	
	public List<String> getCourseFiles(String courseName);
}
