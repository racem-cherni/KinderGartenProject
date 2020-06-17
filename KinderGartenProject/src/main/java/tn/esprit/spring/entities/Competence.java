package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Competence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)

private Long id;
	@Column(unique=true)
private String competenceName;
private Float note;
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getCompetenceName() {
	return competenceName;
}
public void setCompetenceName(String competenceName) {
	this.competenceName = competenceName;
}
public Float getNote() {
	return note;
}
public void setNote(Float note) {
	this.note = note;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}
public Competence(Long id, String competenceName, Float note) {
	super();
	this.id = id;
	this.competenceName = competenceName;
	this.note = note;
}
public Competence() {
	super();
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Competence other = (Competence) obj;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	return true;
}

}
