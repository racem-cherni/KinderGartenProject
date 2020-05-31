package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Stock_event")
public class Stock_event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Stock_id")
	private Long id;
	
	@Column(name="Stock_Name")
	private String name;
	
	@Column(name="Stock_Description")
	private String description;
	
	@Column(name="fournisseur_name")
	private String fournisseur;
	
	@Enumerated(EnumType.STRING)
	private StockCategory stockcategory;
	
	@Column(name="Prix_stock")
	private Double prix_stock;
	
	
	

	public Stock_event() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

	public StockCategory getStockcategory() {
		return stockcategory;
	}

	public void setStockcategory(StockCategory stockcategory) {
		this.stockcategory = stockcategory;
	}

	public Double getPrix_stock() {
		return prix_stock;
	}

	public void setPrix_stock(Double prix_stock) {
		this.prix_stock = prix_stock;
	}
	
	
	

}
