package tn.esprit.spring.entities;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class Rdv_dispo implements Serializable{
		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		@ManyToOne
		private KinderGarten jardin;	
		@Enumerated (EnumType.STRING)
		Lesjours jour;
		
		private Time heuredm;
		private Time heurefm;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		
		public KinderGarten getJardin() {
			return jardin;
		}
		public void setJardin(KinderGarten jardin) {
			this.jardin = jardin;
		}
		public Lesjours getJour() {
			return jour;
		}
		public void setJour(Lesjours jour) {
			this.jour = jour;
		}
		public Time getHeuredm() {
			return heuredm;
		}
		public void setHeuredm(Time heuredm) {
			this.heuredm = heuredm;
		}
		public Time getHeurefm() {
			return heurefm;
		}
		public void setHeurefm(Time heurefm) {
			this.heurefm = heurefm;
		}
	
		
		
		
		

}
