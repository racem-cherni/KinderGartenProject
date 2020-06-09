package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_POST")
public class Post implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POST_ID")
	private Long id; // Identifiant formation (Clé primaire)
	public Post(String title, String picture) {
		super();
		this.title = title;
		this.picture = picture;
	}
	public UserApp getUsers() {
		return users;
	}

	public void setUsers(UserApp users) {
		this.users = users;
	}
	@Column(name = "POST_TITLE")
	private String title;
	@Column(name = "POST_PICTURE", nullable = true)
	private String picture;
	@Temporal(TemporalType.DATE)
	private Date datePost;
	@Column()
	private float totalPoints;
	
	
	

	

	

	public float getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(float totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Date getDatePost() {
		return datePost;
	}

	public void setDatePost(Date datePost) {
		this.datePost = datePost;
	}

	public Long getId() {
		return id;
		
	}
	public Post(String title, String picture, Date datePost, UserApp users) {
		super();
		this.title = title;
		this.picture = picture;
		this.datePost = datePost;
		this.users = users;
	}
	private List<Rating> ratings;
	
	
	@Override
	public String toString() {
		return "title=" + title + ", picture=" + picture + ", datePost=" + datePost + ", users="
				+ users + "]";
	}

	public Set<Comment> getComments() {
		return Comments;
	}

	public void setComments(Set<Comment> comments) {
		Comments = comments;
	}

	public Post(Long id, String title, String picture, Date datePost) {
		super();
		this.id = id;
		this.title = title;
		this.picture = picture;
		this.datePost = datePost;
	}

	public Post(Long id, String title, String picture) {
		super();
		this.id = id;
		this.title = title;
		this.picture = picture;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public Post(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	@Access(AccessType.PROPERTY)
	@OneToMany(mappedBy="post")
	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
	public Post() {
		super();
	}

	public Post(String title, String picture, Date datePost) {
		
		this.title = title;
		this.picture = picture;
		this.datePost = datePost;
	
	}
	@ManyToOne
	UserApp users;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "Posts")
	private Set<Comment> Comments;
	

	public void assignRatingsToThisPost(List<Rating> ratings){
		this.setRatings(ratings);
		for(Rating r : ratings){
			r.setPost(this);
		}
		
	}

}
