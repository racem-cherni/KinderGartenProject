package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity

public class RechercheMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.AUTO)

	
	private Long id;
	private Float maxprix;
	private Float minprix;
	private int score;
	private boolean trieByscore;

	private String country;
	private String ville;
	private String village;
	private String rue;
	@JsonIgnore
	@ManyToOne
	private UserApp userapp;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Float getMaxprix() {
		return maxprix;
	}
	public void setMaxprix(Float maxprix) {
		this.maxprix = maxprix;
	}
	public Float getMinprix() {
		return minprix;
	}
	public void setMinprix(Float minprix) {
		this.minprix = minprix;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isTrieByscore() {
		return trieByscore;
	}
	public void setTrieByscore(boolean trieByscore) {
		this.trieByscore = trieByscore;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public UserApp getUserapp() {
		return userapp;
	}
	public void setUserapp(UserApp userapp) {
		this.userapp = userapp;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public RechercheMenu(Long id, Float maxprix, Float minprix, int score, boolean trieByscore, String country,
			String ville, String village, String rue, UserApp userapp) {
		super();
		this.id = id;
		this.maxprix = maxprix;
		this.minprix = minprix;
		this.score = score;
		this.trieByscore = trieByscore;
		this.country = country;
		this.ville = ville;
		this.village = village;
		this.rue = rue;
		this.userapp = userapp;
	}
	public RechercheMenu() {
		super();
	}
	
}
