package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.management.relation.Relation;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity

public class Advertissement implements Serializable {
	
	private static final long serialVersionUID = 3876346912862238239L;
	@EmbeddedId
	private AdvertissementPK advertissementPK;

	
@JsonIgnore
@ManyToOne
@JoinColumn(name = "idsourceUser", referencedColumnName = "id", insertable=false, updatable=false)
private UserApp sourceUser;
	
@JsonIgnore
@ManyToOne
@JoinColumn(name = "idtargetUser", referencedColumnName = "id", insertable=false, updatable=false)
private UserApp targetUser;
@Enumerated(EnumType.STRING)
private tn.esprit.spring.entities.Relation relation;

private boolean active;

public Advertissement(AdvertissementPK advertissementPK, tn.esprit.spring.entities.Relation friend) {
	super();
	this.advertissementPK = advertissementPK;
	this.relation = friend;
	this.active=false;
}

public Advertissement() {
	super();
}

public AdvertissementPK getAdvertissementPK() {
	return advertissementPK;
}

public void setAdvertissementPK(AdvertissementPK advertissementPK) {
	this.advertissementPK = advertissementPK;
}

public UserApp getSourceUser() {
	return sourceUser;
}

public void setSourceUser(UserApp sourceUser) {
	this.sourceUser = sourceUser;
}

public UserApp getTargetUser() {
	return targetUser;
}

public void setTargetUser(UserApp targetUser) {
	this.targetUser = targetUser;
}

public tn.esprit.spring.entities.Relation getRelation() {
	return relation;
}

public void setRelation(tn.esprit.spring.entities.Relation relation) {
	this.relation = relation;
}

public boolean isActive() {
	return active;
}

public void setActive(boolean active) {
	this.active = active;
}

public static long getSerialversionuid() {
	return serialVersionUID;
}


}
