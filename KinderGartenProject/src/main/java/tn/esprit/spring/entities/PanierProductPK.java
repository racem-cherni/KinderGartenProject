package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PanierProductPK implements Serializable {

	private static final long serialVersionUID = 5377539445871317492L;

	private int idPanier;
	
	private int idOffer;

	public PanierProductPK(int idPanier, int idOffer) {
		super();
		this.idPanier = idPanier;
		this.idOffer = idOffer;
	}

	public int getIdPanier() {
		return idPanier;
	}

	public void setIdPanier(int idPanier) {
		this.idPanier = idPanier;
	}

	public int getIdOffer() {
		return idOffer;
	}

	public void setIdOffer(int idOffer) {
		this.idOffer = idOffer;
	}

	public PanierProductPK() {
		super();
	}
	
	
	
	
}
