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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parent implements Serializable{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String firstName;
	private String lastName;
	private String image;
	private String Email;
	private String adresse;
	private int tel;
	private Date dateNaissance;
	@OneToOne
	private UserApp userApp;
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parents",fetch=FetchType.LAZY)
	private Collection<Child> childs= new ArrayList<>();

	
	/////////////////////////////////////////////////////////////////////////
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parent_invitation")
	private  List<Invitation_Event> invitations;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parent_evaluation")
	private  List<Evaluation_Event> evaluations;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="parent_discussion")
	private  List<Discussion_Event> discussions;

	
	
	public List<Invitation_Event> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<Invitation_Event> invitations) {
		this.invitations = invitations;
	}

	public List<Evaluation_Event> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation_Event> evaluations) {
		this.evaluations = evaluations;
	}

	public Collection<Child> getChilds() {
		return childs;
	}

	public void setChilds(Collection<Child> childs) {
		this.childs = childs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Discussion_Event> getDiscussions() {
		return discussions;
	}

	public void setDiscussions(List<Discussion_Event> discussions) {
		this.discussions = discussions;
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
	

	
	
	
	
	
	
	
}
