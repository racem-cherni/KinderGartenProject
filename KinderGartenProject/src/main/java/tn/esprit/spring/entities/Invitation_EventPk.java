package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Invitation_EventPk implements Serializable {
	
	private static final long serialVersionUID = 5377539445871317492L;

	private Long idEvent;
	
	private Long idparent;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEvent == null) ? 0 : idEvent.hashCode());
		result = prime * result + ((idparent == null) ? 0 : idparent.hashCode());
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
		Invitation_EventPk other = (Invitation_EventPk) obj;
		if (idEvent == null) {
			if (other.idEvent != null)
				return false;
		} else if (!idEvent.equals(other.idEvent))
			return false;
		if (idparent == null) {
			if (other.idparent != null)
				return false;
		} else if (!idparent.equals(other.idparent))
			return false;
		return true;
	}

	public Invitation_EventPk() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Long idEvent) {
		this.idEvent = idEvent;
	}

	public Long getIdparent() {
		return idparent;
	}

	public void setIdparent(Long idparent) {
		this.idparent = idparent;
	}
	
	
	
	

}
