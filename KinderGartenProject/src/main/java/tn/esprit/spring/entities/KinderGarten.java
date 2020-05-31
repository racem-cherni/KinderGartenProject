
package tn.esprit.spring.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KinderGarten implements Serializable {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String KinderGartenName;
	private String adresse;
	private String Email;
	private int capacite ;
	private int tel;
	private Float prix;
	@JsonIgnore
	@OneToOne
	private UserApp userapp;
	@JsonIgnore
	 @Transient 
	@OneToMany(cascade = CascadeType.ALL, mappedBy="kindergarten",fetch=FetchType.LAZY)
	private Collection<Child> kid= new ArrayList<>();
	@JsonIgnore
	 @Transient 
	@OneToMany(cascade = CascadeType.ALL, mappedBy="kinderGarten",fetch=FetchType.LAZY)
	private Collection<Classe> classes= new ArrayList<>();
	@JsonIgnore
	 @Transient 
	@OneToMany(cascade = CascadeType.ALL, mappedBy="kinderGarten",fetch=FetchType.LAZY)
	private Collection<Teacher> teachers = new ArrayList<>();
	
	
//	@JsonIgnore
	// @Transient 
	//@OneToMany(cascade = CascadeType.ALL, mappedBy="kinderGarten",fetch=FetchType.LAZY)
	//private Collection<Event> events = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="kindereventmaker")
	private List<Event> eventm;

     
	public List<Event> getEventm() {
		return eventm;
	}


	public void setEventm(List<Event> eventm) {
		this.eventm = eventm;
	}


	public Collection<Child> getKid() {
		return kid;
	}


	public void setKid(Collection<Child> kid) {
		this.kid = kid;
	}


	public UserApp getUserapp() {
		return userapp;
	}


	public void setUserapp(UserApp userapp) {
		this.userapp = userapp;
	}
	
	
	
	
}
