package tn.esprit.spring.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Child {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private String childName;
private Date dateNaissance;
private String health;
private String image;

@JsonIgnore
@ManyToOne 
private Parent parents;
@JsonIgnore
@ManyToOne 
private KinderGarten kindergarten;
@JsonIgnore
@ManyToOne 
private Classe classe;

public Parent getParents() {
	return parents;
}
public void setParents(Parent parents) {
	this.parents = parents;
}
@Override
public String toString() {
	return "Child [id=" + id + ", childName=" + childName + ", dateNaissance=" + dateNaissance + ", health=" + health
			+ ", image=" + image + ", parents=" + parents + ", kindergarten=" + kindergarten + ", classe=" + classe
			+ "]";
}



}
