package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.Inteface.CoursesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

	public List<Map<String, String>> getCoursesForTeacher(String teacherId) {return conn.getCoursesForTeacher(teacherId);}

	public List<String> getCourseUserProjects(String courseName,String userId) {
		return conn.getCourseUserProjects(courseName,userId);
	}

	public List<Map<String,String>> getCourseProjects(String courseName) throws SQLException {
		return conn.getCourseProjects(courseName);
	}


	public Resource sendStudentProject(String fileName, String courseName, String userId) {
		return conn.sendStudentProject(fileName,courseName,userId);
	}

}
