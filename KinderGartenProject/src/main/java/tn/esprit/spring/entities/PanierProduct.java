package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "panierproduct")
public class PanierProduct implements Serializable {

	private static final long serialVersionUID = 3876346912862238239L;

	@EmbeddedId
	private PanierProductPK panierPK;

	private int qty;

	@ManyToOne
	@JoinColumn(name = "idOffer", referencedColumnName = "id", insertable = false, updatable = false)
	private Offer offer;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "idPanier", referencedColumnName = "id", insertable = false, updatable = false)
	private Panier panier;

	@OneToOne
	@JoinColumn(nullable = true)
	private UserApp refuser;

	public PanierProductPK getPanierPK() {
		return panierPK;
	}

	public void setPanierPK(PanierProductPK panierPK) {
		this.panierPK = panierPK;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public PanierProduct() {
		super();
	}

	public UserApp getRefuser() {
		return refuser;
	}

	public void setRefuser(UserApp refuser) {
		this.refuser = refuser;
	}

	public PanierProduct(PanierProductPK panierPK, int qty, Offer offer, Panier panier, UserApp refuser) {
		super();
		this.panierPK = panierPK;
		this.qty = qty;
		this.offer = offer;
		this.panier = panier;
		this.refuser = refuser;
	}

	
}
