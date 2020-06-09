package tn.esprit.spring.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class callories {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private float value;
	@JsonIgnore
	@OneToOne 
	private MedicalRec MedicalRec;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public MedicalRec getMedicalRec() {
		return MedicalRec;
	}

	public void setMedicalRec(MedicalRec medicalRec) {
		MedicalRec = medicalRec;
	}

	public callories() {
		super();
	}


	
}
