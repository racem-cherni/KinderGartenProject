package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable

public class VotePK implements Serializable{
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



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public VotePK(Long idsourceUser, Long idtargetUser) {
		super();
		this.idsourceUser = idsourceUser;
		this.idtargetUser = idtargetUser;
	}



	public VotePK() {
		super();
	}
	
}
