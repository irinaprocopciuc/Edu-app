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

import dao.Inteface.FacultyInterface;
import org.springframework.stereotype.Repository;

@Repository("Faculty")
public class Faculty implements FacultyInterface {

	private static Connection conn;
	
	public Faculty() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "root");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override
	public  List<Map<String, String>> getFacultyandSpecialization() {
		List<Map<String, String>> allFacultyList = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select faculty, name from specialization;");
			while(rs.next()) {
				Map<String, String> facultyList = new HashMap<>();
				facultyList.put("faculty", rs.getString(1));
				facultyList.put("specialization", rs.getString(2));
				allFacultyList.add(facultyList);
			}
			return allFacultyList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allFacultyList;
	}
}
