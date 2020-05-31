package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="Facture_Event")
public class Facture_Event  implements Serializable{
private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Facture_id")
	private Long id;
	
	@Column(name="charge_stocks")
	private double montant_stocks;
	
	
	@Column(name="charge_totale")
	private double montant_totale;
	
	@Temporal (TemporalType.DATE)
	@Column(name="Date_Dacture")
	private Date date_facture;

	public Facture_Event() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public double getMontant_stocks() {
		return montant_stocks;
	}

	public void setMontant_stocks(double montant_stocks) {
		this.montant_stocks = montant_stocks;
	}

	public double getMontant_totale() {
		return montant_totale;
	}

	public void setMontant_totale(double montant_totale) {
		this.montant_totale = montant_totale;
	}

	public Date getDate_facture() {
		return date_facture;
	}

	public void setDate_facture(Date date_facture) {
		this.date_facture = date_facture;
	}

	

	
	
}
