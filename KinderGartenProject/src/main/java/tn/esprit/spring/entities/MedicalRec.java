package tn.esprit.spring.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRec {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column (name="allergy")
	private String Allergy;

	@Column(name="Medical_problem")
	private String Medicalprob;
	
	@Column(name="Medical_treatment")
	private String Medicaltreat;
	
	@Column(name="blood_groups")
	private String blood_groups;
	
	@Column(name="weight")
	private float weight;
	
	@Column(name="height")
	private float height;
	
	@JsonIgnore
	@OneToOne (mappedBy="MedicalRec")
	private Child child;
	
	/*@JsonIgnore
	@ManyToOne 
	private MENUOFTHEDAY MENUOFTHEDAY ;*/

	@JsonIgnore
	@ManyToOne
	private categories categroy ;
	
	@JsonIgnore
	@OneToOne(mappedBy="MedicalRec")
	private callories callories;
	
	@JsonIgnore
	@ManyToOne
	private menucategory menucategory ;
	
	@JsonIgnore
	@OneToMany(mappedBy="MedicalRec",fetch=FetchType.LAZY)
	private Collection<foodmedrecwithgramage> foodmedrecwithgramage= new ArrayList<>();
	
	
	public menucategory getMenucategory() {
		return menucategory;
	}

	public void setMenucategory(menucategory menucategory) {
		this.menucategory = menucategory;
	}

	/*public MENUOFTHEDAY getMENUOFTHEDAY() {
		return MENUOFTHEDAY;
	}

	public void setMENUOFTHEDAY(MENUOFTHEDAY mENUOFTHEDAY) {
		MENUOFTHEDAY = mENUOFTHEDAY;
	}*/

	public callories getCallories() {
		return callories;
	}

	

	public void setCallories(callories callories) {
		this.callories = callories;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAllergy() {
		return Allergy;
	}

	public void setAllergy(String allergy) {
		Allergy = allergy;
	}

	public String getMedicalprob() {
		return Medicalprob;
	}

	public void setMedicalprob(String medicalprob) {
		Medicalprob = medicalprob;
	}

	public String getMedicaltreat() {
		return Medicaltreat;
	}

	public void setMedicaltreat(String medicaltreat) {
		Medicaltreat = medicaltreat;
	}

	public String getBlood_groups() {
		return blood_groups;
	}

	public void setBlood_groups(String blood_groups) {
		this.blood_groups = blood_groups;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Child getChild() {
		return child;
	}

	public void setChild(Child child) {
		this.child = child;
	}

	/*public MENUOFTHEDAY getMenuoftheday() {
		return MENUOFTHEDAY;
	}

	public void setMenuoftheday(MENUOFTHEDAY menuoftheday) {
		MENUOFTHEDAY = menuoftheday;
	}
*/
	public MedicalRec(String allergy, String medicalprob, String medicaltreat, String blood_groups, float weight,
			float height) {
		super();
		Allergy = allergy;
		Medicalprob = medicalprob;
		Medicaltreat = medicaltreat;
		this.blood_groups = blood_groups;
		this.weight = weight;
		this.height = height;
	}

	

	public categories getCategroy() {
		return categroy;
	}

	public void setCategroy(categories categroy) {
		this.categroy = categroy;
	}

	public Collection<foodmedrecwithgramage> getFoodmedrecwithgramage() {
		return foodmedrecwithgramage;
	}

	public void setFoodmedrecwithgramage(Collection<foodmedrecwithgramage> foodmedrecwithgramage) {
		this.foodmedrecwithgramage = foodmedrecwithgramage;
	}

	public MedicalRec() {
		super();
	}
	

	
	
	
	
	
}
