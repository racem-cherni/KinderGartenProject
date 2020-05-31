package tn.esprit.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ModifierPassword {
	private String username;
	private String password;
	private String cfpassword;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCfpassword() {
		return cfpassword;
	}
	public void setCfpassword(String cfpassword) {
		this.cfpassword = cfpassword;
	}
	public ModifierPassword(String username, String password, String cfpassword) {
		super();
		this.username = username;
		this.password = password;
		this.cfpassword = cfpassword;
	}
	public ModifierPassword() {
		super();
	}
	
	
}
