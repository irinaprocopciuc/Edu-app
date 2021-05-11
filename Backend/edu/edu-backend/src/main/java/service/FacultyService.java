package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.Inteface.FacultyInterface;

@Service
public class FacultyService {
	private final FacultyInterface conn;

	@Autowired
	public FacultyService(@Qualifier("Faculty") FacultyInterface connection) {
		this.conn = connection;
	}
	
	public List<Map<String, String>> getFacultyandSpecialization() {
		return conn.getFacultyandSpecialization();
	}
}
