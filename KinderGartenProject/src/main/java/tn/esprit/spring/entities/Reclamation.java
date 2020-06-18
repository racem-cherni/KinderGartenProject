package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Reclamation implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reclamation_id;
	
	@Temporal(TemporalType.DATE)
    private Date reclamation_date;
	private String reclamation_description;
	private String reclam_reponse;
	
	@Enumerated(EnumType.STRING)
	SujetReclam sujet_reclam;
	/////////////////////
	
	@ManyToOne
	private Parent parent;
	
	@ManyToOne
	private KinderGarten jardin;
	
	/*private int etat_R;
	private String reponse;*/

	public int getReclamation_id() {
		return reclamation_id;
	}

	public void setReclamation_id(int reclamation_id) {
		this.reclamation_id = reclamation_id;
	}

	public Date getReclamation_date() {
		return reclamation_date;
	}

	public void setReclamation_date(Date reclamation_date) {
		this.reclamation_date = reclamation_date;
	}

	public String getReclamation_description() {
		return reclamation_description;
	}

	public void setReclamation_description(String reclamation_description) {
		this.reclamation_description = reclamation_description;
	}

	
	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public KinderGarten getJardin() {
		return jardin;
	}

	public void setJardin(KinderGarten jardin) {
		this.jardin = jardin;
	}

	public SujetReclam getSujet_reclam() {
		return sujet_reclam;
	}

	public void setSujet_reclam(SujetReclam sujet_reclam) {
		this.sujet_reclam = sujet_reclam;
	}

	public String getReclam_reponse() {
		return reclam_reponse;
	}

	public void setReclam_reponse(String reclam_reponse) {
		this.reclam_reponse = reclam_reponse;
	}



	/*public int getEtat_R() {
		return etat_R;
	}

	public void setEtat_R(int etat_R) {
		this.etat_R = etat_R;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}*/

	
	
	
}
