package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "order_date")
	private Date order_date;

	@Column(name = "total_price")
	private double total_price;

	private double reducedprice;

	private double pointspent;

	@Enumerated(EnumType.STRING)
	private OrderState state = OrderState.WAITING;

	@OneToOne
	private Panier panier;

	@ManyToOne
	UserApp User;

	public UserApp getUser() {
		return User;
	}

	public void setUser(UserApp user) {
		User = user;
	}

	public Order() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public double getReducedprice() {
		return reducedprice;
	}

	public void setReducedprice(double reducedprice) {
		this.reducedprice = reducedprice;
	}

	public double getPointspent() {
		return pointspent;
	}

	public void setPointspent(double pointspent) {
		this.pointspent = pointspent;
	}

	public Order(int id, Date order_date, double total_price, double reducedprice, double pointspent, OrderState state,
			Panier panier, UserApp user) {
		super();
		this.id = id;
		this.order_date = order_date;
		this.total_price = total_price;
		this.reducedprice = reducedprice;
		this.pointspent = pointspent;
		this.state = state;
		this.panier = panier;
		User = user;
	}
	
	

}
