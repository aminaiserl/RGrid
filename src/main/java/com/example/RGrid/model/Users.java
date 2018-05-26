package com.example.RGrid.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table (name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_family_name")
	private String userFamilyName;
	
	@Column(name = "user_login")
	private String login;
	
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;
	
	@Column(name = "reg_date")
	private Date registrationDate;
	
	@Column(name = "active")
	private boolean active;
	
	@Column(name = "user_type")
	private int userType;

	public int getId() {
		return id;
	}
	
	public Users(){
		
	}

	public Users(String userName, String userFamilyName, String login, String email,
			String password) {
		super();
		this.userName = userName;
		this.userFamilyName = userFamilyName;
		this.login = login;
		this.password = password;
		this.email = email;
		this.registrationDate = new Date();
		this.active = false;
		this.userType = 0;
	}
	
	public Users(Users user){
		this.login = user.getLogin();
		this.active = user.isActive();
		this.registrationDate = user.getRegistrationDate();
		this.password = user.getPassword();
		this.userFamilyName = user.getUserFamilyName();
		this.userName = user.getUserName();
		this.userType = user.getUserType();
		this.id = user.getId();
		this.email = user.getEmail();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserFamilyName() {
		return userFamilyName;
	}

	public void setUserFamilyName(String userFamilyName) {
		this.userFamilyName = userFamilyName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", userName=" + userName
				+ ", userFamilyName=" + userFamilyName + ", login=" + login
				+ ", email=" + email + ", password=" + password
				+ ", registrationDate=" + registrationDate + ", active="
				+ active + ", userType=" + userType + "]";
	}
	
	
	
}
