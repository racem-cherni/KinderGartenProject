package tn.esprit.spring.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="Invitation_Event")
public class Invitation_Event implements Serializable {
	
	private static final long serialVersionUID = 1L;
    
	@EmbeddedId
	private Invitation_EventPk invitationpk ;
	
	  
	
    @Column(name="Date_Invitation")
	private Date date_invitation ;
    
    @Column(name="Date_Reponse")
	private Date date_reponse ;
    
    private String reponse ;
    
    @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idEvent", referencedColumnName = "Event_id", insertable=false, updatable=false)
	private Event event_invitation ;
    
    @JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idparent", referencedColumnName = "id", insertable=false, updatable=false)
    private Parent parent_invitation ;

	public Invitation_Event() {
		super();
		// TODO Auto-generated constructor stub//
	}

	public Invitation_EventPk getInvitationpk() {
		return invitationpk;
	}

	public void setInvitationpk(Invitation_EventPk invitationpk) {
		this.invitationpk = invitationpk;
	}

	

	
	

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public Date getDate_invitation() {
		return date_invitation;
	}

	public void setDate_invitation(Date date_invitation) {
		this.date_invitation = date_invitation;
	}

	public Event getEvent_invitation() {
		return event_invitation;
	}

	public void setEvent_invitation(Event event_invitation) {
		this.event_invitation = event_invitation;
	}

	public Parent getParent_invitation() {
		return parent_invitation;
	}

	public void setParent_invitation(Parent parent_invitation) {
		this.parent_invitation = parent_invitation;
	}

	public Date getDate_reponse() {
		return date_reponse;
	}

	public void setDate_reponse(Date date_reponse) {
		this.date_reponse = date_reponse;
	}

	@Override
	public String toString() {
		return "Invitation_Event [invitationpk=" + invitationpk + ", date_invitation=" + date_invitation
				+ ", date_reponse=" + date_reponse + ", reponse=" + reponse + ", event_invitation=" + event_invitation
				+ ", parent_invitation=" + parent_invitation + "]";
	}
	
	

	
    
    
    

}
