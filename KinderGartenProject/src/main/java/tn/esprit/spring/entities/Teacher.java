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
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Teacher implements Serializable{
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


@ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
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


}
