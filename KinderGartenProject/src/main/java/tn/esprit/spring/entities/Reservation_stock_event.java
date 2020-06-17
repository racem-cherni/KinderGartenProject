package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="RESERVATION_STOCK_EVENT")
public class Reservation_stock_event implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private Reservation_stock_eventPk reservationstockeventpk ;
	
	private Date date_reservation;
	
	private  int quantite ;
	
	private  Double price ;
	
	
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "idevent", referencedColumnName = "Event_id", insertable=false, updatable=false)
	private Event event;
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "idstock", referencedColumnName = "Stock_id", insertable=false, updatable=false)
	private Stock_event stockevent ;

	public Reservation_stock_event() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reservation_stock_eventPk getReservationstockeventpk() {
		return reservationstockeventpk;
	}

	public void setReservationstockeventpk(Reservation_stock_eventPk reservationstockeventpk) {
		this.reservationstockeventpk = reservationstockeventpk;
	}

	public Date getDate_reservation() {
		return date_reservation;
	}

	public void setDate_reservation(Date date_reservation) {
		this.date_reservation = date_reservation;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Stock_event getStockevent() {
		return stockevent;
	}

	public void setStockevent(Stock_event stockevent) {
		this.stockevent = stockevent;
	}
	
	
	
	
	
	

	
	
	
	
	
	


}
