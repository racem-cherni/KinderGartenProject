package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
public class UserApp implements Serializable {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	@Column(name="username",unique=true)
	private String username;
	@Column(name="password")
	
	private String password;
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<RoleApp> roles= new ArrayList<>();
	private boolean actived;
	private int score;
	private int point ;
@JsonIgnore
	@OneToOne(mappedBy="userApp")
	private Parent parent;
@JsonIgnore
	@OneToOne(mappedBy="userapp")
	private KinderGarten kindergarten;
@Transient 
@OneToMany(cascade = CascadeType.ALL, mappedBy="sourceUser",fetch=FetchType.LAZY)
private Collection<Advertissement> advertissemented= new ArrayList<>();

public UserApp(String username, String password, Collection<RoleApp> roles, boolean actived, int score,int point) {
	super();
	
	this.username = username;
	this.password = password;
	this.roles = roles;
	this.actived = false;
	this.score = score;
	this.point=point;
}
public UserApp( String username, String password, Collection<RoleApp> roles, boolean actived, int score) {
	super();
	
	this.username = username;
	this.password = password;
	this.roles = roles;
	this.actived = false;
	this.score = score;
	this.point=3;
}
public UserApp() {
	super();
}





}
