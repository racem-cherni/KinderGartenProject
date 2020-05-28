package tn.esprit.spring.entities;

import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Seance {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idSeance ;
	
    private String matiere ;
	
    @JsonFormat(pattern="HH:mm")
	private LocalTime heure_debut ;
	
	@JsonFormat(pattern="HH:mm")
	private LocalTime heure_fin ;
	
	 @JsonFormat(pattern="yyyy-MM-dd")
	private Date jour ;
	
	
	@ManyToOne
	private Emploi emploi ;
	
	

	@OneToOne
	private Presence presence;


	public int getIdSeance() {
		return idSeance;
	}


	public void setIdSeance(int idSeance) {
		this.idSeance = idSeance;
	}


	public String getMatiere() {
		return matiere;
	}


	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}


	public LocalTime getHeure_debut() {
		return heure_debut;
	}


	public void setHeure_debut(LocalTime heure_debut) {
		this.heure_debut = heure_debut;
	}


	public LocalTime getHeure_fin() {
		return heure_fin;
	}


	public void setHeure_fin(LocalTime heure_fin) {
		this.heure_fin = heure_fin;
	}


	public Date getJour() {
		return jour;
	}


	public void setJour(Date jour) {
		this.jour = jour;
	}


	public Emploi getEmploi() {
		return emploi;
	}


	public void setEmploi(Emploi emploi) {
		this.emploi = emploi;
	}


	public Presence getPresence() {
		return presence;
	}


	public void setPresence(Presence presence) {
		this.presence = presence;
	}

}
