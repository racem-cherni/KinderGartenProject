package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Parent implements Serializable{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private String image;
	private String Email;
	private String adresse;
	private int tel;
	private Date dateNaissance;
	@OneToOne
	private UserApp userApp;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parents",fetch=FetchType.LAZY)
	private Collection<Child> childs= new ArrayList<>();

	
	
}
