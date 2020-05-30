package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
@Entity
@Table(name="Discussion_Event")
public class Discussion_Event implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private Discussion_EventPk discussioneventpk ;
	
	
	
	private String commentaire ;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idEvent", referencedColumnName = "Event_id", insertable=false, updatable=false)
	private Event event_discussion ;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idparent", referencedColumnName = "id", insertable=false, updatable=false)
	private Parent parent_discussion ;

	public Discussion_Event() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Discussion_EventPk getDiscussioneventpk() {
		return discussioneventpk;
	}

	public void setDiscussioneventpk(Discussion_EventPk discussioneventpk) {
		this.discussioneventpk = discussioneventpk;
	}

	

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Event getEvent_discussion() {
		return event_discussion;
	}

	public void setEvent_discussion(Event event_discussion) {
		this.event_discussion = event_discussion;
	}

	public Parent getParent_discussion() {
		return parent_discussion;
	}

	public void setParent_discussion(Parent parent_discussion) {
		this.parent_discussion = parent_discussion;
	}

	@Override
	public String toString() {
		return "Discussion_Event [discussioneventpk=" + discussioneventpk 
				+ ", commentaire=" + commentaire + ", event_discussion=" + event_discussion + ", parent_discussion="
				+ parent_discussion + "]";
	}
	
	
	
	

	
}
