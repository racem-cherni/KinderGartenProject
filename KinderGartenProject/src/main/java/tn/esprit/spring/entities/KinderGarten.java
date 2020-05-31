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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String kinderGartenName;
	private String adresse;
	private String email;
	private int capacite ;
	private int tel;
	private Float prix;
	private  int maxRdv;
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
	 @Transient 
	@OneToMany(cascade = CascadeType.ALL, mappedBy="kinderGarten",fetch=FetchType.LAZY)
	private Collection<Event> events = new ArrayList<>();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getKinderGartenName() {
		return kinderGartenName;
	}


	public void setKinderGartenName(String kinderGartenName) {
		this.kinderGartenName = kinderGartenName;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getCapacite() {
		return capacite;
	}


	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}


	public int getTel() {
		return tel;
	}


	public void setTel(int tel) {
		this.tel = tel;
	}


	public Float getPrix() {
		return prix;
	}


	public void setPrix(Float prix) {
		this.prix = prix;
	}


	public int getMaxRdv() {
		return maxRdv;
	}


	public void setMaxRdv(int maxRdv) {
		this.maxRdv = maxRdv;
	}


	public UserApp getUserapp() {
		return userapp;
	}


	public void setUserapp(UserApp userapp) {
		this.userapp = userapp;
	}


	


	


	public Collection<Classe> getClasses() {
		return classes;
	}


	public void setClasses(Collection<Classe> classes) {
		this.classes = classes;
	}


	public Collection<Teacher> getTeachers() {
		return teachers;
	}


	public void setTeachers(Collection<Teacher> teachers) {
		this.teachers = teachers;
	}


	public Collection<Event> getEvents() {
		return events;
	}


	public void setEvents(Collection<Event> events) {
		this.events = events;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public KinderGarten(Long id, String kinderGartenName, String adresse, String email, int capacite, int tel,
			Float prix, int maxRdv, UserApp userapp, Collection<Child> kid, Collection<Classe> classes,
			Collection<Teacher> teachers, Collection<Event> events) {
		super();
		this.id = id;
		this.kinderGartenName = kinderGartenName;
		this.adresse = adresse;
		this.email = email;
		this.capacite = capacite;
		this.tel = tel;
		this.prix = prix;
		this.maxRdv = maxRdv;
		this.userapp = userapp;
		this.kid = kid;
		this.classes = classes;
		this.teachers = teachers;
		this.events = events;
	}


	public KinderGarten() {
		super();
	}
	
	
	
	
	
	
}
