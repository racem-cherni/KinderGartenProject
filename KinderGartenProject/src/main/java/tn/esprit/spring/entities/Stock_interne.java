package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="Stock_interne")
public class Stock_interne implements Serializable{
private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Stock_id")
	private Long id;
	
	@Column(name="Stock_Name")
	private String Name;
	
	@Column(name="Stock_Description")
	private String description;
	
	@Column(name="Quantity_utilisé")
	private   int quantite_utilisé;
	
	
	@Column(name="Stock_quantité")
	 private  int firstquantite   ;
	
    
	@Enumerated(EnumType.STRING)
	private StockinterneCategory stockcategory;
	
	@Column(name="Stock_Photo")
    private String photo;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="stock")
	private  List<Reservation_Stock_interne> reservation_stock_interne;

	public Stock_interne() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public List<Reservation_Stock_interne> getReservation_stock_interne() {
		return reservation_stock_interne;
	}



	public void setReservation_stock_interne(List<Reservation_Stock_interne> reservation_stock_interne) {
		this.reservation_stock_interne = reservation_stock_interne;
	}



	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantite_utilisé() {
		return quantite_utilisé;
	}

	public void setQuantite_utilisé(int quantite_utilisé) {
		this.quantite_utilisé = quantite_utilisé;
	}

	public int getFirstquantite() {
		return firstquantite;
	}

	public void setFirstquantite(int firstquantite) {
		this.firstquantite = firstquantite;
	}

	public StockinterneCategory getStockcategory() {
		return stockcategory;
	}

	public void setStockcategory(StockinterneCategory stockcategory) {
		this.stockcategory = stockcategory;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	

	
	
}
