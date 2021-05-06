package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.UserInterface;

@Service
public class UserService {
	private final UserInterface conn;
	

	@Autowired
	public UserService(@Qualifier("Users") UserInterface connection) {
		this.conn = connection;
	}
	
	public List<Map<String, String>> getUsers() {
		return conn.getUsers();
	}
}
