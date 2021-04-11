package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import model.LoginDetails;
import model.RegisterDetails;

@Repository("LoginRegister")
public class LoginRegister implements LoginRegisterInterface {

	private static Connection conn;

	public LoginRegister() throws ClassNotFoundException, SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "root");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public int checkUser(LoginDetails credentials) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select iduser from user where username ='" + credentials.getUsername()
					+ "' and pass='" + credentials.getPassword() + "';");
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int findUser(String username) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user where username ='" + username + "';");
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public boolean addUser(RegisterDetails credentials) {
		int userID = this.generateUserID();
		String specId;
		String yearSemId;
		if (userID != -1) {
			try {
				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery("select idSpec from specialization where faculty ='"
						+ credentials.getFaculty() + "' and name='" + credentials.getSpecName() + "';");
				rs.next();
				specId = rs.getString(1);

				ResultSet res = stmt.executeQuery("select idYearSemester from yearsemester where yearOfStudy ='"
						+ credentials.getYearOfStudy() + "' and semester='" + credentials.getSemester() + "';");
				res.next();
				yearSemId = res.getString(1);

				stmt.executeUpdate(
						"insert into user (idUser, name, email, username, pass, userType, idSpecialization, idYearSemester) values ('"
								+ userID + "', '" + credentials.getName() + "', '" + credentials.getEmail() + "', '"
								+ credentials.getUsername() + "', '" + credentials.getPassword() + "', '"
								+ credentials.getUserType() + "', '" + yearSemId + "', '" + specId + "');");
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private int generateUserID() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select max(iduser) from user;");
			rs.next();
			String max = rs.getString(1);
			if (max != null) {
				return Integer.parseInt(max) + 1;
			} else {
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
