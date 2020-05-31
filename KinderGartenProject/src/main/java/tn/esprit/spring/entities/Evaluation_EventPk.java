package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Evaluation_EventPk implements Serializable {
	
private static final long serialVersionUID = 5377539445871317492L;
	
    private Long idparentt;
	
	private Long idEvent;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idEvent == null) ? 0 : idEvent.hashCode());
		result = prime * result + ((idparentt == null) ? 0 : idparentt.hashCode());
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
		Evaluation_EventPk other = (Evaluation_EventPk) obj;
		if (idEvent == null) {
			if (other.idEvent != null)
				return false;
		} else if (!idEvent.equals(other.idEvent))
			return false;
		if (idparentt == null) {
			if (other.idparentt != null)
				return false;
		} else if (!idparentt.equals(other.idparentt))
			return false;
		return true;
	}

	public Long getIdparentt() {
		return idparentt;
	}

	public void setIdparentt(Long idparentt) {
		this.idparentt = idparentt;
	}

	public Long getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(Long idEvent) {
		this.idEvent = idEvent;
	}

		

}
