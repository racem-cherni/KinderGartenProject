package tn.esprit.spring.Service;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import lombok.Data;
import tn.esprit.spring.entities.RoleApp;

@Data
public class RegisterUser {
	
	private String username;
	private String password;
	private String cfpassword;
	private boolean actived;
	private int Score;
	
	
	
}
