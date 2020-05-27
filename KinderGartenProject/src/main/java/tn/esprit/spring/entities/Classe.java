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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
@Entity
@Data
public class Classe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  Long id;
	private String nom;
	private int capacitie;
	private int age;
	
	@JsonIgnore
	@ManyToOne
	private KinderGarten  kinderGarten;
	
	@JsonIgnore
	 @Transient 
	@OneToMany(cascade = CascadeType.ALL, mappedBy="classe",fetch=FetchType.EAGER)
	private Collection<Child> kid= new ArrayList<>();
	
	@JsonIgnore
	@OneToOne(mappedBy="classe")
	private Teacher teacher;
	
	
	
	
	public Classe(Long id, String nom, int capacitie, int age, KinderGarten kinderGarten, Teacher teacher) {
		super();
		this.id = id;
		this.nom = nom;
		this.capacitie = capacitie;
		this.age = age;
		this.kinderGarten = kinderGarten;
		this.teacher = teacher;
	}
	public Classe() {
		super();
	}
	public Classe(String nom, int capacitie, int age) {
		super();
		this.nom = nom;
		this.capacitie = capacitie;
		this.age = age;
	}

	
	
	
	
	
	
}
