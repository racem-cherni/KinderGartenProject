package tn.esprit.spring.entities;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity 
@Table(name = "T_COMMENT")
public class Comment {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMMENT_ID")
	private Long id; 
	@Column(name = "COMMENT_LIKE")
	private int like;
	@Column(name = "COMMENT_DESLIKE")
	private int desLike;
	@Column(name = "COMMENT_LOVE")
	private int love;
	@Column(name = "COMMENT_RIRE")
	private int rire;
	@Column(name = "COMMENT_CONTENU")
	private String contenuComment;
	@Override
	public String toString() {
		return "Comment [id=" + id + ", like=" + like + ", desLike=" + desLike + ", love=" + love + ", rire=" + rire
				+ ", contenuComment=" + contenuComment + ", dateComment=" + dateComment + ", Useres=" + Useres
				+ ", Posts=" + Posts + "]";
	}
	@Temporal(TemporalType.DATE)
	private Date dateComment;
	
	
	@ManyToOne
	UserApp Useres;
	@ManyToOne
	Post Posts;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public int getDesLike() {
		return desLike;
	}
	public void setDesLike(int desLike) {
		this.desLike = desLike;
	}
	public int getLove() {
		return love;
	}
	public void setLove(int love) {
		this.love = love;
	}
	public int getRire() {
		return rire;
	}
	public void setRire(int rire) {
		this.rire = rire;
	}
	public String getContenuComment() {
		return contenuComment;
	}
	public void setContenuComment(String contenuComment) {
		this.contenuComment = contenuComment;
	}
	public Date getDateComment() {
		return dateComment;
	}
	public void setDateComment(Date dateComment) {
		this.dateComment = dateComment;
	}
	public UserApp getUseres() {
		return Useres;
	}
	public void setUseres(UserApp useres) {
		Useres = useres;
	}
	public Post getPosts() {
		return Posts;
	}
	public void setPosts(Post posts) {
		Posts = posts;
	}
	public Comment(Long id, int like, int desLike, int love, int rire, String contenuComment, Date dateComment,
			UserApp useres, Post posts) {
		super();
		this.id = id;
		this.like = like;
		this.desLike = desLike;
		this.love = love;
		this.rire = rire;
		this.contenuComment = contenuComment;
		this.dateComment = dateComment;
		Useres = useres;
		Posts = posts;
	}
	public Comment() {
		super();
	}
	public Comment(Long id, String contenuComment) {
		super();
		this.id = id;
		this.contenuComment = contenuComment;
	}
	public Comment(int like, int desLike, int love, int rire, String contenuComment, Date dateComment) {
		super();
		this.like = like;
		this.desLike = desLike;
		this.love = love;
		this.rire = rire;
		this.contenuComment = contenuComment;
		this.dateComment = dateComment;
	}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="Comments")
	private Set<History> comHist;
	public Set<History> getComHist() {
		return comHist;
	}
	public void setComHist(Set<History> comHist) {
		this.comHist = comHist;
	}
}
