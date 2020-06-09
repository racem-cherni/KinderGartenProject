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
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class menucategory {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String catemenuname;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="menucategory",fetch=FetchType.LAZY)
	private Collection<MedicalRec> MedicalRec= new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCatemenuname() {
		return catemenuname;
	}

	public void setCatemenuname(String catemenuname) {
		this.catemenuname = catemenuname;
	}

	public Collection<MedicalRec> getMedicalRec() {
		return MedicalRec;
	}

	public void setMedicalRec(Collection<MedicalRec> medicalRec) {
		MedicalRec = medicalRec;
	}

	public menucategory() {
		super();
	}



	
	
}
