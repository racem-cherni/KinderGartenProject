package tn.esprit.spring.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Rdv implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int rdv_id;
    private String rdv_title;
	@Temporal(TemporalType.DATE)
    private Date rdv_date;
	private String rdv_description;
	private int rdv_etat;
	private Time rdv_heure;
	//////////
	/////////
	@JsonIgnore
	@ManyToOne
	private KinderGarten jardin;
	@ManyToOne
	private Parent parent;
	@JsonIgnore
	@OneToOne(mappedBy="rdv")
	private Rdv_reponse rdvreponse;
//////////////////////////////
	
	
	public int getRdv_id() {
		return rdv_id;
	}
	public Rdv() {
		
	}
	public Rdv(String rdv_title, Date rdv_date, String rdv_description) {
	super();
	this.rdv_title = rdv_title;
	this.rdv_date = rdv_date;
	this.rdv_description = rdv_description;
}
	public Rdv(String rdv_title,String rdv_description,Date rdv_date) {
		super();
		this.rdv_title = rdv_title;
		this.rdv_description = rdv_description;
		this.rdv_date = rdv_date;

	}
	

	public Rdv(int rdv_id, String rdv_title, Date rdv_date, String rdv_description, int rdv_etat,
			Parent parent) {
		super();
		this.rdv_id = rdv_id;
		this.rdv_title = rdv_title;
		this.rdv_date = rdv_date;
		this.rdv_description = rdv_description;
		this.rdv_etat = rdv_etat;
		this.parent = parent;
	}
	
	public Rdv(int rdv_id, String rdv_title, Date rdv_date, String rdv_description, int rdv_etat) {
		super();
		this.rdv_id = rdv_id;
		this.rdv_title = rdv_title;
		this.rdv_date = rdv_date;
		this.rdv_description = rdv_description;
		this.rdv_etat = rdv_etat;
	}
	
	public void setRdv_id(int rdv_id) {
		this.rdv_id = rdv_id;
	}

	public String getRdv_title() {
		return rdv_title;
	}

	public void setRdv_title(String rdv_title) {
		this.rdv_title = rdv_title;
	}

	public Date getRdv_date() {
		return rdv_date;
	}

	public void setRdv_date(Date rdv_date) {
		this.rdv_date = rdv_date;
	}

	public String getRdv_description() {
		return rdv_description;
	}

	public void setRdv_description(String rdv_description) {
		this.rdv_description = rdv_description;
	}

	public int getRdv_etat() {
		return rdv_etat;
	}

	public void setRdv_etat(int rdv_etat) {
		this.rdv_etat = rdv_etat;
	}

	

	public KinderGarten getJardin() {
		return jardin;
	}
	public void setJardin(KinderGarten jardin) {
		this.jardin = jardin;
	}
	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	public Rdv_reponse getRdvreponse() {
		return rdvreponse;
	}

	public void setRdvreponse(Rdv_reponse rdvreponse) {
		this.rdvreponse = rdvreponse;
	}
	public Time getRdv_heure() {
		return rdv_heure;
	}
	public void setRdv_heure(Time rdv_heure) {
		this.rdv_heure = rdv_heure;
	}

	
	
	
	
/////////////////////
	
}
