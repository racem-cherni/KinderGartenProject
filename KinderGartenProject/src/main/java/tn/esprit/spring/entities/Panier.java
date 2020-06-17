package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "paniers")
public class Panier implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	private Date date;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "panier", cascade = CascadeType.ALL)
	private List<PanierProduct> orderedoffers;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<PanierProduct> getOrderedoffers() {
		return orderedoffers;
	}

	public void setOrderedoffers(List<PanierProduct> orderedoffers) {
		this.orderedoffers = orderedoffers;
	}

	public Panier(int id, Date date, List<PanierProduct> orderedoffers) {
		super();
		this.id = id;
		this.date = date;
		this.orderedoffers = orderedoffers;
	}

	public Panier() {
		super();
		this.date = new Date();
	}
	
	
}
