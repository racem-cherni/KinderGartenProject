package tn.esprit.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class Adresse {

	private String country;
	private String ville;
	private String village;
	private String rue;
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
	public Adresse(String country, String ville, String village, String rue) {
		super();
		this.country = country;
		this.ville = ville;
		this.village = village;
		this.rue = rue;
	}
	public Adresse() {
		super();
	}
	
}
