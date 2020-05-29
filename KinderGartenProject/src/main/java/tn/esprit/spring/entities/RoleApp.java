package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

public class RoleApp implements Serializable  {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@Id
@GeneratedValue
private Long id;
@Column(unique=true)
private String roleName;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getRoleName() {
	return roleName;
}
public void setRoleName(String roleName) {
	this.roleName = roleName;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}
public RoleApp(Long id, String roleName) {
	super();
	this.id = id;
	this.roleName = roleName;
}
public RoleApp() {
	super();
}




}
