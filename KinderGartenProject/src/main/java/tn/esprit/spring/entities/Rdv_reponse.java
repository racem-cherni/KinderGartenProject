package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Rdv_reponse implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int rdvr_id;
	@Enumerated (EnumType.STRING)
	Reponse reponse;
	private String rdvr_message;
	private Date rdvr_date ;

	/////////////
	////////////
	@OneToOne
	private Rdv rdv;
	////
	public int getRdvr_id() {
		return rdvr_id;
	}
	public void setRdvr_id(int rdvr_id) {
		this.rdvr_id = rdvr_id;
	}
	public Reponse getReponse() {
		return reponse;
	}
	public void setReponse(Reponse reponse) {
		this.reponse = reponse;
	}
	public String getRdvr_message() {
		return rdvr_message;
	}
	public void setRdvr_message(String rdvr_message) {
		this.rdvr_message = rdvr_message;
	}
	public Rdv getRdv() {
		return rdv;
	}
	public void setRdv(Rdv rdv) {
		this.rdv = rdv;
	}
	public Date getRdvr_date() {
		return rdvr_date;
	}
	public void setRdvr_date(Date rdvr_date) {
		this.rdvr_date = rdvr_date;
	}
	
	
}
