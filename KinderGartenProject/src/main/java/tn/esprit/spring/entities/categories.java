package tn.esprit.spring.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class categories {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nameofcategory;
	
	@JsonIgnore
	@Transient 
	@OneToMany(cascade = CascadeType.ALL, mappedBy="categories",fetch=FetchType.LAZY)
	private Collection<MedicalRec> MedicalRec= new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameofcategory() {
		return nameofcategory;
	}

	public void setNameofcategory(String nameofcategory) {
		this.nameofcategory = nameofcategory;
	}

	public Collection<MedicalRec> getMedicalRec() {
		return MedicalRec;
	}

	public void setMedicalRec(Collection<MedicalRec> medicalRec) {
		MedicalRec = medicalRec;
	}

	

	public categories(Long id, String nameofcategory, Collection<tn.esprit.spring.entities.MedicalRec> medicalRec) {
		super();
		this.id = id;
		this.nameofcategory = nameofcategory;
		MedicalRec = medicalRec;
	}

	public categories() {
		super();
	}

}
