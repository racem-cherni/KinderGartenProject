package tn.esprit.spring.entities;

import java.io.Serializable;  
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
public class UserApp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
@JsonIgnore
@Transient 
@OneToMany(cascade = CascadeType.ALL,mappedBy="userapp",fetch=FetchType.LAZY)
private Collection<RechercheMenu> mySearch= new ArrayList<>();










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
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
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
public Collection<RoleApp> getRoles() {
	return roles;
}
public void setRoles(Collection<RoleApp> roles) {
	this.roles = roles;
}
public boolean isActived() {
	return actived;
}
public void setActived(boolean actived) {
	this.actived = actived;
}
public int getScore() {
	return score;
}
public void setScore(int score) {
	this.score = score;
}
public int getPoint() {
	return point;
}
public void setPoint(int point) {
	this.point = point;
}
public Parent getParent() {
	return parent;
}
public void setParent(Parent parent) {
	this.parent = parent;
}
public KinderGarten getKindergarten() {
	return kindergarten;
}
public void setKindergarten(KinderGarten kindergarten) {
	this.kindergarten = kindergarten;
}
public Collection<Advertissement> getAdvertissemented() {
	return advertissemented;
}
public void setAdvertissemented(Collection<Advertissement> advertissemented) {
	this.advertissemented = advertissemented;
}






	
public Collection<RechercheMenu> getMySearch() {
	return mySearch;
}
public void setMySearch(Collection<RechercheMenu> mySearch) {
	this.mySearch = mySearch;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}

}
