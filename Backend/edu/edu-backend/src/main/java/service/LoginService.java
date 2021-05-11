package service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.Inteface.LoginRegisterInterface;
import model.LoginDetails;

@Service
public class LoginService {

	private final LoginRegisterInterface db;

	@Autowired
	public LoginService(@Qualifier("LoginRegister") LoginRegisterInterface db) {
		this.db = db;
	}

	public List<Map<String, String>> checkUser(LoginDetails credentials) {
		return db.checkUser(credentials);
	}
}
