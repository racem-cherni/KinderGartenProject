package tn.esprit.spring.entities;

import java.io.Serializable; 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity

public class Parent implements Serializable{
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private String image;
	private String Email;
	private String adresse;
	private int tel;
	@Temporal(TemporalType.DATE)
	private Date dateNaissance;
	@OneToOne
	private UserApp userApp;
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parents",fetch=FetchType.LAZY)
	private Collection<Child> childs= new ArrayList<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public int getTel() {
		return tel;
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	public Date getDateNaissance() {
		return dateNaissance;
	}
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
	public UserApp getUserApp() {
		return userApp;
	}
	public void setUserApp(UserApp userApp) {
		this.userApp = userApp;
	}
	public Collection<Child> getChilds() {
		return childs;
	}
	public void setChilds(Collection<Child> childs) {
		this.childs = childs;
	}
	public Parent(Long id, String firstName, String lastName, String image, String email, String adresse, int tel,
			Date dateNaissance, UserApp userApp, Collection<Child> childs) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.image = image;
		Email = email;
		this.adresse = adresse;
		this.tel = tel;
		this.dateNaissance = dateNaissance;
		this.userApp = userApp;
		this.childs = childs;
	}
	public Parent() {
		super();
	}

///////////////////////houssem
	
@JsonIgnore
@OneToMany(mappedBy="parent")
private List<Rating> ratingP;

@JsonIgnore
@OneToMany(mappedBy="parent")
private List<Rdv> rdvP;

@JsonIgnore
@OneToMany(mappedBy="parent")
private List<Reclamation> reclamationP;

@JsonIgnore
@OneToMany(mappedBy="parent")
private List<Reunion_feedback> reunfbP;

@JsonIgnore
@ManyToMany
@LazyCollection(LazyCollectionOption.FALSE)
private List<Reunion> reunionsP;
///////////////////////////
	/////////////////////////////////////////////////////////////////////////
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parent_invitation")
	private  List<Invitation_Event> invitations;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy="parent_evaluation")
//	private  List<Evaluation_Event> evaluations;
//	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parent_discussion")
	private  List<Discussion_Event> discussions;

	
	
	public List<Invitation_Event> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<Invitation_Event> invitations) {
		this.invitations = invitations;
	}

//	public List<Evaluation_Event> getEvaluations() {
//		return evaluations;
//	}
//
//	public void setEvaluations(List<Evaluation_Event> evaluations) {
//		this.evaluations = evaluations;
//	}

	


	

	public List<Discussion_Event> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(List<Discussion_Event> discussions) {
		this.discussions = discussions;
	}
	public List<Rating> getRatingP() {
		return ratingP;
	}
	public void setRatingP(List<Rating> ratingP) {
		this.ratingP = ratingP;
	}
	public List<Rdv> getRdvP() {
		return rdvP;
	}
	public void setRdvP(List<Rdv> rdvP) {
		this.rdvP = rdvP;
	}
	public List<Reclamation> getReclamationP() {
		return reclamationP;
	}
	public void setReclamationP(List<Reclamation> reclamationP) {
		this.reclamationP = reclamationP;
	}
	public List<Reunion_feedback> getReunfbP() {
		return reunfbP;
	}
	public void setReunfbP(List<Reunion_feedback> reunfbP) {
		this.reunfbP = reunfbP;
	}
	public List<Reunion> getReunionsP() {
		return reunionsP;
	}
	public void setReunionsP(List<Reunion> reunionsP) {
		this.reunionsP = reunionsP;
	}

	

	
	
	
	
	
	
	
}
