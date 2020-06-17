package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Reservation_stock_eventPk implements Serializable {
	
	private static final long serialVersionUID = 5377539445871317492L;

	 private Long idevent;
		
	 private Long idstock;

	public Reservation_stock_eventPk() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdevent() {
		return idevent;
	}

	public void setIdevent(Long idevent) {
		this.idevent = idevent;
	}

	public Long getIdstock() {
		return idstock;
	}

	public void setIdstock(Long idstock) {
		this.idstock = idstock;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idevent == null) ? 0 : idevent.hashCode());
		result = prime * result + ((idstock == null) ? 0 : idstock.hashCode());
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
		Reservation_stock_eventPk other = (Reservation_stock_eventPk) obj;
		if (idevent == null) {
			if (other.idevent != null)
				return false;
		} else if (!idevent.equals(other.idevent))
			return false;
		if (idstock == null) {
			if (other.idstock != null)
				return false;
		} else if (!idstock.equals(other.idstock))
			return false;
		return true;
	}
	 
	 

}
