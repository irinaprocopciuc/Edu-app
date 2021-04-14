package dao;

import java.util.List;
import java.util.Map;

import model.LoginDetails;
import model.RegisterDetails;

public interface LoginRegisterInterface {
	
	public List<Map<String, String>> checkUser(LoginDetails details);
	public int findUser(String userame);
	public boolean addUser(RegisterDetails details);
	
}
