package model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterDetails {
	@NotBlank(message = "Name is blank")
	private final String name;

	@NotBlank(message = "Email is blank")
	private final String email;

	@NotBlank(message = "Username is blank")
	private final String username;

	@NotBlank(message = "Password is blank")
	//@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password is not valid")
	private final String password;

	@NotBlank(message = "Repassword is blank")
	//@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Repassword is not valid")
	private final String rePassword;

	private final String userType;

	private final String yearOfStudy;

	private final String semester;

	private final String faculty;

	private final String specName;

	public RegisterDetails(@JsonProperty("name") String name,@JsonProperty("email") String email,@JsonProperty("username") String username, @JsonProperty("password") String password,
			@JsonProperty("repassword") String rePassword, @JsonProperty("userType") String userType,
			@JsonProperty("yearOfStudy") String yearOfStudy, @JsonProperty("semester") String semester,
			@JsonProperty("faculty") String faculty, @JsonProperty("specName") String specName
			) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.rePassword = rePassword;
		this.userType = userType;
		this.yearOfStudy = yearOfStudy;
		this.semester = semester;
		this.faculty = faculty;
		this.specName = specName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		System.out.println(password);
		return password;
	}

	public String getRePassword() {
		System.out.println(rePassword);
		return rePassword;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getUserType() {
		return userType;
	}

	public String getYearOfStudy() {
		return yearOfStudy;
	}

	public String getSemester() {
		return semester;
	}

	public String getFaculty() {
		return faculty;
	}

	public String getSpecName() {
		return specName;
	}
}
