package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "T_HISTORY")
public class History implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HISTORY_ID")
	private Long id; // Identifiant formation (Clé primaire)
	@Column(name = "HISTORY_Post")
	private String postHistory; // Thème formation
	@Column(name = "HISTORY_React")
	private String reactHistory;
	@Temporal(TemporalType.DATE)
	private Date dateHistory;
	public History() {
		super();
	}
	public History(Long id, String postHistory, String reactHistory, Date dateHistory, UserApp users) {
		super();
		this.id = id;
		this.postHistory = postHistory;
		this.reactHistory = reactHistory;
		this.dateHistory = dateHistory;
		Users = users;
	}
	public History(String postHistory, String reactHistory) {
		super();
		this.postHistory = postHistory;
		this.reactHistory = reactHistory;
	}
	public History(String postHistory) {
		super();
		this.postHistory = postHistory;
	}
	@ManyToOne
	UserApp Users;
	@ManyToOne
	Comment Comments;
	
	public Comment getComments() {
		return Comments;
	}
	public void setComments(Comment comments) {
		Comments = comments;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPostHistory() {
		return postHistory;
	}
	public void setPostHistory(String postHistory) {
		this.postHistory = postHistory;
	}
	public String getReactHistory() {
		return reactHistory;
	}
	public void setReactHistory(String reactHistory) {
		this.reactHistory = reactHistory;
	}
	public Date getDateHistory() {
		return dateHistory;
	}
	public void setDateHistory(Date dateHistory) {
		this.dateHistory = dateHistory;
	}
	public UserApp getUsers() {
		return Users;
	}
	public void setUsers(UserApp users) {
		Users = users;
	}
	
}
