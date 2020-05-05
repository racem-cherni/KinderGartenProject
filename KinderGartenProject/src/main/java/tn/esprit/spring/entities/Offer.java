package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "offers")
public class Offer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "price")
	private double price;

	@Column(name = "publication_date")
	private Date pub_date;

	@Column(name = "qty")
	private int qty;

	@ManyToOne
	Product product;

	@ManyToOne
	UserApp user;

	@JsonIgnore
	@OneToMany(mappedBy = "offer")
	private List<PanierProduct> paniers;

	public Offer() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getPub_date() {
		return pub_date;
	}

	public void setPub_date(Date pub_date) {
		this.pub_date = pub_date;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public List<PanierProduct> getPaniers() {
		return paniers;
	}

	public void setPaniers(List<PanierProduct> paniers) {
		this.paniers = paniers;
	}

	public UserApp getUser() {
		return user;
	}

	public void setUser(UserApp user) {
		this.user = user;
	}

	public Offer(int id, double price, Date pub_date, int qty, Product product, UserApp user,
			List<PanierProduct> paniers) {
		super();
		this.id = id;
		this.price = price;
		this.pub_date = pub_date;
		this.qty = qty;
		this.product = product;
		this.user = user;
		this.paniers = paniers;
	}




	
	

}
