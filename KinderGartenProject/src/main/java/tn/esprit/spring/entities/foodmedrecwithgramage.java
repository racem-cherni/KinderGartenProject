package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class foodmedrecwithgramage implements Serializable{
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	
	    @JsonIgnore
	    @ManyToOne
	    @JoinColumn
	    private MedicalRec MedicalRec;

	    @JsonIgnore
	    @ManyToOne
	    @JoinColumn
	    private foodsandtheircallories foodsandtheircallories;
	   
	    @Temporal(TemporalType.TIMESTAMP)
	    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	    private Date publishedDate;
	    
	    private float gramsneeded;

		public MedicalRec getMedicalRec() {
			return MedicalRec;
		}

		public void setMedicalRec(MedicalRec medicalRec) {
			MedicalRec = medicalRec;
		}

		public foodsandtheircallories getFoodsandtheircallories() {
			return foodsandtheircallories;
		}

		public void setFoodsandtheircallories(foodsandtheircallories foodsandtheircallories) {
			this.foodsandtheircallories = foodsandtheircallories;
		}

		public Date getPublishedDate() {
			return publishedDate;
		}

		public void setPublishedDate(Date publishedDate) {
			this.publishedDate = publishedDate;
		}

		public float getGramsneeded() {
			return gramsneeded;
		}

		public void setGramsneeded(float gramsneeded) {
			this.gramsneeded = gramsneeded;
		}

		public foodmedrecwithgramage(tn.esprit.spring.entities.MedicalRec medicalRec,
				tn.esprit.spring.entities.foodsandtheircallories foodsandtheircallories, Date publishedDate,
				float gramsneeded) {
			super();
			MedicalRec = medicalRec;
			this.foodsandtheircallories = foodsandtheircallories;
			this.publishedDate = publishedDate;
			this.gramsneeded = gramsneeded;
		}

		public foodmedrecwithgramage() {
			super();
		}
		
		
}
