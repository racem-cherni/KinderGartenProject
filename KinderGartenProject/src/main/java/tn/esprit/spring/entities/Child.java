package tn.esprit.spring.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity

public class Child {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private String childName;
@Temporal(TemporalType.DATE)

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
@JsonIgnore
@ManyToOne
private Presence presence ;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getChildName() {
	return childName;
}
public void setChildName(String childName) {
	this.childName = childName;
}
public Date getDateNaissance() {
	return dateNaissance;
}
public void setDateNaissance(Date dateNaissance) {
	this.dateNaissance = dateNaissance;
}
public String getHealth() {
	return health;
}
public void setHealth(String health) {
	this.health = health;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public Parent getParents() {
	return parents;
}
public void setParents(Parent parents) {
	this.parents = parents;
}
public KinderGarten getKindergarten() {
	return kindergarten;
}
public void setKindergarten(KinderGarten kindergarten) {
	this.kindergarten = kindergarten;
}
public Classe getClasse() {
	return classe;
}
public void setClasse(Classe classe) {
	this.classe = classe;
}
public Presence getPresence() {
	return presence;
}
public void setPresence(Presence presence) {
	this.presence = presence;
}
public Child(Long id, String childName, Date dateNaissance, String health, String image, Parent parents,
		KinderGarten kindergarten, Classe classe, Presence presence) {
	super();
	this.id = id;
	this.childName = childName;
	this.dateNaissance = dateNaissance;
	this.health = health;
	this.image = image;
	this.parents = parents;
	this.kindergarten = kindergarten;
	this.classe = classe;
	this.presence = presence;
}
public Child() {
	super();
}












@Override
public String toString() {
	return "Child [id=" + id + ", childName=" + childName + ", dateNaissance=" + dateNaissance + ", health=" + health
			+ ", image=" + image + ", parents=" + parents + ", kindergarten=" + kindergarten + ", classe=" + classe
			+ "]";
}



}
