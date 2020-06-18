package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
public class Reunion_feedback implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int reunfb_id;
	
	@Temporal(TemporalType.DATE)
    private Date reunfb_date;
	private String reunfb_description;
	//////////
	/////////
	@ManyToOne
	private Parent parent;
	
	@ManyToOne
	private Reunion reunion;

	public int getReunfb_id() {
		return reunfb_id;
	}

	public void setReunfb_id(int reunfb_id) {
		this.reunfb_id = reunfb_id;
	}

	public Date getReunfb_date() {
		return reunfb_date;
	}

	public void setReunfb_date(Date reunfb_date) {
		this.reunfb_date = reunfb_date;
	}

	public String getReunfb_description() {
		return reunfb_description;
	}

	public void setReunfb_description(String reunfb_description) {
		this.reunfb_description = reunfb_description;
	}

	

	public Parent getParent() {
		return parent;
	}

	public void setParent(Parent parent) {
		this.parent = parent;
	}

	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}
	
	
	
}
