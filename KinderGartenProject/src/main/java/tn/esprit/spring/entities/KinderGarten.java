
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity

public class KinderGarten implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String KinderGartenName;
	private String adresse;
	private String Email;
	private int capacite ;
	private int tel;
	private Float prix;
	private  int maxRdv;
	private String image;
	private String imageBack;
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
	
	@JsonIgnore
	@Transient 
	@ManyToMany(cascade = CascadeType.ALL, mappedBy="kinderGarten",fetch=FetchType.LAZY)
	private Collection<foodsandtheircallories> foodsandtheircallories = new ArrayList<>();

///////////////////////houssem
	
	
@JsonIgnore
@OneToMany(mappedBy="jardin")
private List<Rating> ratingJ;

@JsonIgnore
@OneToMany(mappedBy="jardin")
private List<Rdv_dispo> rdvdispo;

@JsonIgnore
@OneToMany(mappedBy="jardin")
private List<Reunion_dispo> reundispo;

@JsonIgnore
@OneToMany(mappedBy="jardin")
private List<Rdv> rdvJ;

@JsonIgnore
@OneToMany(mappedBy="jardin")
private List<Reclamation> reclamationJ;

@JsonIgnore
@OneToMany(mappedBy="jardin")
private List<Reunion> reunionsJ;
///////////////////


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getKinderGartenName() {
		return KinderGartenName;
	}


	public void setKinderGartenName(String kinderGartenName) {
		KinderGartenName = kinderGartenName;
	}


	public String getAdresse() {
		return adresse;
	}


	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getMaxRdv() {
		return maxRdv;
	}


	public void setMaxRdv(int maxRdv) {
		this.maxRdv = maxRdv;
	}


	public String getImageBack() {
		return imageBack;
	}


	public void setImageBack(String imageBack) {
		this.imageBack = imageBack;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public Collection<foodsandtheircallories> getFoodsandtheircallories() {
		return foodsandtheircallories;
	}


	public void setFoodsandtheircallories(Collection<foodsandtheircallories> foodsandtheircallories) {
		this.foodsandtheircallories = foodsandtheircallories;
	}


	public List<Rating> getRatingJ() {
		return ratingJ;
	}


	public void setRatingJ(List<Rating> ratingJ) {
		this.ratingJ = ratingJ;
	}


	public List<Rdv_dispo> getRdvdispo() {
		return rdvdispo;
	}


	public void setRdvdispo(List<Rdv_dispo> rdvdispo) {
		this.rdvdispo = rdvdispo;
	}


	public List<Reunion_dispo> getReundispo() {
		return reundispo;
	}


	public void setReundispo(List<Reunion_dispo> reundispo) {
		this.reundispo = reundispo;
	}


	public List<Rdv> getRdvJ() {
		return rdvJ;
	}


	public void setRdvJ(List<Rdv> rdvJ) {
		this.rdvJ = rdvJ;
	}


	public List<Reclamation> getReclamationJ() {
		return reclamationJ;
	}


	public void setReclamationJ(List<Reclamation> reclamationJ) {
		this.reclamationJ = reclamationJ;
	}


	public List<Reunion> getReunionsJ() {
		return reunionsJ;
	}


	public void setReunionsJ(List<Reunion> reunionsJ) {
		this.reunionsJ = reunionsJ;
	}
	
	
	
	
}
