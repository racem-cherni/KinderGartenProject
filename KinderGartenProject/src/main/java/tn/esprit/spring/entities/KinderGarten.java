package tn.esprit.spring.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KinderGarten implements Serializable {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String KinderGartenName;
	private String adresse;
	private String Email;
	private int capacite ;
	private int tel;
	@OneToOne
	private UserApp userapp;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="kindergarten")
	private Collection<Child> kid= new ArrayList<>();
	
	
	
	
}
