package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Rating implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int rating_id;
	private double rating_note;
	private double note_nourriture;
	private double note_maitres;
	private double note_activites;

	public double getNote_nourriture() {
		return note_nourriture;
	}

	public void setNote_nourriture(double note_nourriture) {
		this.note_nourriture = note_nourriture;
	}

	public double getNote_maitres() {
		return note_maitres;
	}

	public void setNote_maitres(double note_maitres) {
		this.note_maitres = note_maitres;
	}

	
	public double getNote_activites() {
		return note_activites;
	}

	public void setNote_activites(double note_activites) {
		this.note_activites = note_activites;
	}


	@Temporal(TemporalType.DATE)
    private Date rating_date;
	private String rating_description;
	/////////
	/////////
	@ManyToOne
	private Parent parent;
	
	@ManyToOne
	private KinderGarten jardin;

	public int getRating_id() {
		return rating_id;
	}

	public void setRating_id(int rating_id) {
		this.rating_id = rating_id;
	}



	

	public double getRating_note() {
		return rating_note;
	}

	public void setRating_note(double rating_note) {
		this.rating_note = rating_note;
	}

	public Date getRating_date() {
		return rating_date;
	}

	public void setRating_date(Date rating_date) {
		this.rating_date = rating_date;
	}

	public String getRating_description() {
		return rating_description;
	}

	public void setRating_description(String rating_description) {
		this.rating_description = rating_description;
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

	
	
	
	//////////////////
	
}
