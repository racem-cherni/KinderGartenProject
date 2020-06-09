package tn.esprit.spring.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class foodsandtheircallories {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	String foodgroup;
	String callories;
	
	@JsonIgnore 
	@OneToMany(mappedBy="foodsandtheircallories",fetch=FetchType.LAZY)
	private Collection<foodmedrecwithgramage> foodmedrecwithgramage = new ArrayList<>();
	
	
	public Collection<foodmedrecwithgramage> getFoodmedrecwithgramage() {
		return foodmedrecwithgramage;
	}
	public void setFoodmedrecwithgramage(Collection<foodmedrecwithgramage> foodmedrecwithgramage) {
		this.foodmedrecwithgramage = foodmedrecwithgramage;
	}
	@JsonIgnore
	@ManyToMany
	private Collection<KinderGarten> KinderGarten=new ArrayList<>();
	
	public Collection<KinderGarten> getKinderGarten() {
		return KinderGarten;
	}
	public void setKinderGarten(Collection<KinderGarten> kinderGarten) {
		KinderGarten = kinderGarten;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFoodgroup() {
		return foodgroup;
	}
	public void setFoodgroup(String foodgroup) {
		this.foodgroup = foodgroup;
	}
	public String getCallories() {
		return callories;
	}
	public void setCallories(String callories) {
		this.callories = callories;
	}
	public foodsandtheircallories(Long id, String foodgroup, String callories) {
		super();
		this.id = id;
		this.foodgroup = foodgroup;
		this.callories = callories;
	}
	public foodsandtheircallories() {
		super();
	}
	
	/*public Collection<MedicalRec> getMedicalRec() {
		return MedicalRec;
	}
	public void setMedicalRec(Collection<MedicalRec> medicalRec) {
		MedicalRec = medicalRec;
	}*/
	
}
