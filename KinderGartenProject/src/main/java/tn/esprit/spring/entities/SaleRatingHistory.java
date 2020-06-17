package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "SaleRatings_History")
public class SaleRatingHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RateUsersPK rateuser;

	@ManyToOne
	@JoinColumn(name = "userid", referencedColumnName = "id", insertable = false, updatable = false)
	private UserApp user;

	@ManyToOne
	@JoinColumn(name = "offerid", referencedColumnName = "id", insertable = false, updatable = false)
	private Offer offer;

	private Date date;

	private int rating;

	private String review;

	public SaleRatingHistory(RateUsersPK rateuser, UserApp user, Offer offer, Date date, String review) {
		super();
		this.rateuser = rateuser;
		this.user = user;
		this.offer = offer;
		this.date = date;
		this.review = review;
	}

	public SaleRatingHistory() {
		super();
	}

	public RateUsersPK getRateuser() {
		return rateuser;
	}

	public void setRateuser(RateUsersPK rateuser) {
		this.rateuser = rateuser;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserApp getUser() {
		return user;
	}

	public void setUser(UserApp user) {
		this.user = user;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public SaleRatingHistory(RateUsersPK rateuser, int rating, String review) {
		super();
		this.rateuser = rateuser;
		this.date = new Date();
		this.rating = rating;
		this.review = review;
	}
	
	

}
