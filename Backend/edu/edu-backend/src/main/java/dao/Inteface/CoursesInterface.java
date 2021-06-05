package dao.Inteface;

import org.springframework.core.io.Resource;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CoursesInterface {
	public List<Map<String, String>> getCourses(String userId);

	public List<String> getCourseFiles(String courseName);

	public List<String> getCourseUserProjects(String courseName, String userId);

	public List<Map<String,String>> getCourseProjects(String courseName) throws SQLException;

	public List<Map<String, String>> getCoursesForTeacher(String userId);

	Resource sendStudentProject(String fileName, String courseName, String userId);
}
