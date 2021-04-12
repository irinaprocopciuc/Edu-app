package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository("Courses")
public class Courses implements CoursesInterface {
	private static Connection conn;

	public Courses() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "root");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public List<Map<String, String>> getCourses(String userId) {
		List<Map<String, String>> coursesList = new ArrayList<>();
		String specId = null;
		String yearSemId = null;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select idSpecialization, idYearSemester from user where idUser ='" + userId + "';");
			rs.next();
			specId = rs.getString(1);
			yearSemId = rs.getString(2);

			ResultSet res = stmt.executeQuery("select idclass,name, numberofcredits from class where idSpec ='" + specId
					+ "' and idYearSem ='" + yearSemId + "';");

			while (res.next()) {
				Map<String, String> courses = new HashMap<>();
				courses.put("isclass", res.getString(1));
				courses.put("name", res.getString(2));
				courses.put("credits", res.getString(3));
				coursesList.add(courses);
			}
			return coursesList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return coursesList;
	}
}
