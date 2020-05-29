package tn.esprit.spring.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity

public class Vote implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@EmbeddedId
	private VotePK votePK;

	
@JsonIgnore
@ManyToOne
@JoinColumn(name = "idsourceUser", referencedColumnName = "id", insertable=false, updatable=false)
private KinderGarten sourceUser;
	
@JsonIgnore
@ManyToOne
@JoinColumn(name = "idtargetUser", referencedColumnName = "id", insertable=false, updatable=false)
private Parent targetUser;
@Range(min=0, max=10)
private int note;
	
private Date date;
	




	
public Vote() {
	super();
}











public Vote(VotePK votePK, KinderGarten sourceUser, Parent targetUser, int note, Date date) {
	super();
	this.votePK = votePK;
	this.sourceUser = sourceUser;
	this.targetUser = targetUser;
	this.note = note;
	this.date = date;
}











public VotePK getVotePK() {
	return votePK;
}











public void setVotePK(VotePK votePK) {
	this.votePK = votePK;
}











public KinderGarten getSourceUser() {
	return sourceUser;
}











public void setSourceUser(KinderGarten sourceUser) {
	this.sourceUser = sourceUser;
}











public Parent getTargetUser() {
	return targetUser;
}











public void setTargetUser(Parent targetUser) {
	this.targetUser = targetUser;
}











public int getNote() {
	return note;
}











public void setNote(int note) {
	this.note = note;
}











public Date getDate() {
	return date;
}











public void setDate(Date date) {
	this.date = date;
}











public static long getSerialversionuid() {
	return serialVersionUID;
}











public Vote(KinderGarten sourceUser, Parent targetUser, int note, Date date) {
	super();
	this.sourceUser = sourceUser;
	this.targetUser = targetUser;
	this.note = note;
	this.date = date;
}











public Date calculateExpiryDate(int expiryTimeInMinutes) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Timestamp(cal.getTime().getTime()));
    cal.add(Calendar.MINUTE, expiryTimeInMinutes);
    return new Date(cal.getTime().getTime());
}





}
