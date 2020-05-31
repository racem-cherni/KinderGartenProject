package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable

public class AdvertissementPK implements Serializable {

	


	private static final long serialVersionUID = 5377539445871317492L;
	
	private Long idsourceUser;
		
	
	
	private Long idtargetUser;
	

	public Long getIdsourceUser() {
		return idsourceUser;
	}



	public void setIdsourceUser(Long idsourceUser) {
		this.idsourceUser = idsourceUser;
	}



	public Long getIdtargetUser() {
		return idtargetUser;
	}



	public void setIdtargetUser(Long idtargetUser) {
		this.idtargetUser = idtargetUser;
	}



	public AdvertissementPK() {
		super();
	}



	public AdvertissementPK(Long idsourceUser, Long idtargetUser) {
		super();
		this.idsourceUser = idsourceUser;
		this.idtargetUser = idtargetUser;
	}

	
}
