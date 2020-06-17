package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
@Entity

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Teacher implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private String nom;

private int age;
private int numtel;
private String email;
private String image;

@JsonIgnore
@ManyToOne
private KinderGarten  kinderGarten;
@JsonIgnore
@OneToOne
private Classe classe;


@ManyToMany(fetch=FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
private Collection<Competence> competences= new ArrayList<>();





public Teacher() {
	super();
}
public Teacher(Long id, String nom, int age, int numtel, String email) {
	super();
	this.id = id;
	this.nom = nom;
	this.age = age;
	this.numtel = numtel;
	this.email = email;
}
public Teacher(String nom, int age, int numtel, String email) {
	super();
	this.nom = nom;
	this.age = age;
	this.numtel = numtel;
	this.email = email;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public int getAge() {
	return age;
}
public void setAge(int age) {
	this.age = age;
}
public int getNumtel() {
	return numtel;
}
public void setNumtel(int numtel) {
	this.numtel = numtel;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public KinderGarten getKinderGarten() {
	return kinderGarten;
}
public void setKinderGarten(KinderGarten kinderGarten) {
	this.kinderGarten = kinderGarten;
}
public Classe getClasse() {
	return classe;
}
public void setClasse(Classe classe) {
	this.classe = classe;
}
public Collection<Competence> getCompetences() {
	return competences;
}
public void setCompetences(Collection<Competence> competences) {
	this.competences = competences;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}


}
