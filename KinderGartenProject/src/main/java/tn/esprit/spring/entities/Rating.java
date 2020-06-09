package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Rating implements Serializable {

	
	@Override
	public String toString() {
		return "Rating [User=" + User + ", Post=" + Post + ", rate=" + rate + "]";
	}
	private static final long serialVersionUID = 1L;
	public Rating(tn.esprit.spring.entities.UserApp user, tn.esprit.spring.entities.Post post, Rate rate) {
		super();
		User = user;
		Post = post;
		this.rate = rate;
	}
	private RatingPk RatingPk;
	private UserApp User;
	private Post Post;
	@Enumerated(EnumType.STRING)
	Rate rate; 
	
	
	
	
	public Rating() {
		super();
	}
	public Rating(tn.esprit.spring.entities.RatingPk ratingPk, tn.esprit.spring.entities.UserApp user,
			tn.esprit.spring.entities.Post post, Long points, Rate rate) {
		super();
		RatingPk = ratingPk;
		User = user;
		Post = post;
		
		this.rate = rate;
	}
	public Rate getRate() {
		return rate;
	}
	public void setRate(Rate rate) {
		this.rate = rate;
	}
	
	@EmbeddedId
	public RatingPk getRatingPk() {
		return RatingPk;
	}
	public void setRatingPk(RatingPk ratingPk) {
		RatingPk = ratingPk;
	}
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, name ="idUser", referencedColumnName = "USER_ID")
	public UserApp getUser() {
		return User;
	}
	public void setUser(UserApp user) {
		User = user;
	}
	@ManyToOne
	@JoinColumn(insertable = false, updatable = false, name ="idPost", referencedColumnName = "POST_ID")
	public Post getPost() {
		return Post;
	}
	public void setPost(Post post) {
		Post = post;
	}
	

}
