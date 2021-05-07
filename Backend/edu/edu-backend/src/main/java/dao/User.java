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


@Repository("Users")
public class User implements UserInterface{
	private static Connection conn;
	
	
	public User() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "root");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@Override 
	public List<Map<String, String>> getUsers() {
		List<Map<String, String>> usersList = new ArrayList<>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select idUser, username from user;");
			while (rs.next()) {
				Map<String, String> user = new HashMap<>();
				user.put("iduser", rs.getString(1));
				user.put("name", rs.getString(2));
				usersList.add(user);
			}
			System.out.println(usersList);
			return usersList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersList;
	}
}
