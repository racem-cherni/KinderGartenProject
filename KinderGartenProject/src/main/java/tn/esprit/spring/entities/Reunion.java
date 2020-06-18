package tn.esprit.spring.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Reunion implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reun_id;
	private int reun_nbrparticipant;
	private int reun_nbrplace;
	
	private Time reun_heureD;
	private Time reun_heureF;
	@Enumerated (EnumType.STRING)
	TypeReunion reun_type;
	private String reun_title;
	@Temporal(TemporalType.DATE)
    private Date reun_date;
	private String reun_description;
	
	private int etat_R;
	private String reun_image;
	///////////////
	///////////////
	@JsonIgnore
	@ManyToMany(mappedBy="reunionsP")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Parent> parents;
	
	@ManyToOne
	private KinderGarten jardin;
	@JsonIgnore
	@OneToMany(mappedBy="reunion",cascade=CascadeType.ALL)
	private List<Reunion_feedback> reunfb;

	public int getReun_id() {
		return reun_id;
	}

	public void setReun_id(int reun_id) {
		this.reun_id = reun_id;
	}

	public int getReun_nbrparticipant() {
		return reun_nbrparticipant;
	}

	public void setReun_nbrparticipant(int reun_nbrparticipant) {
		this.reun_nbrparticipant = reun_nbrparticipant;
	}

	public int getReun_nbrplace() {
		return reun_nbrplace;
	}

	public void setReun_nbrplace(int reun_nbrplace) {
		this.reun_nbrplace = reun_nbrplace;
	}

	
	public String getReun_title() {
		return reun_title;
	}

	public void setReun_title(String reun_title) {
		this.reun_title = reun_title;
	}

	public Date getReun_date() {
		return reun_date;
	}

	public void setReun_date(Date reun_date) {
		this.reun_date = reun_date;
	}

	public String getReun_description() {
		return reun_description;
	}

	public void setReun_description(String reun_description) {
		this.reun_description = reun_description;
	}

	
	@Transactional
	public List<Parent> getParents() {
		return parents;
	}

	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}

	public KinderGarten getJardin() {
		return jardin;
	}

	public void setJardin(KinderGarten jardin) {
		this.jardin = jardin;
	}

	public List<Reunion_feedback> getReunfb() {
		return reunfb;
	}

	public void setReunfb(List<Reunion_feedback> reunfb) {
		this.reunfb = reunfb;
	}

	
	public Time getReun_heureD() {
		return reun_heureD;
	}

	public void setReun_heureD(Time reun_heureD) {
		this.reun_heureD = reun_heureD;
	}

	public Time getReun_heureF() {
		return reun_heureF;
	}

	public void setReun_heureF(Time reun_heureF) {
		this.reun_heureF = reun_heureF;
	}

	public TypeReunion getReun_type() {
		return reun_type;
	}

	public void setReun_type(TypeReunion reun_type) {
		this.reun_type = reun_type;
	}

	public int getEtat_R() {
		return etat_R;
	}

	public void setEtat_R(int etat_R) {
		this.etat_R = etat_R;
	}

	public String getReun_image() {
		return reun_image;
	}

	public void setReun_image(String reun_image) {
		this.reun_image = reun_image;
	}


	
	
	///////////////////
	
}
