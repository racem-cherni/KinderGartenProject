package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PanierSession")
public class PanierSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique = true)
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	private UserApp user;
	
	@OneToOne
	private Panier panier;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserApp getUser() {
		return user;
	}

	public void setUser(UserApp user) {
		this.user = user;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public PanierSession(UserApp user, Panier panier) {
		super();
		this.user = user;
		this.panier = panier;
	}

	public PanierSession() {
		super();
	}
	
	

	
}
