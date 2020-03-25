package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApp implements Serializable {
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column(name="id")
	private long id;
	@Column(name="username",unique=true)
	private String username;
	@Column(name="password")
	
	private String password;
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<RoleApp> roles= new ArrayList<>();
	private boolean actived;
	private int Score;

	@OneToOne(mappedBy="userApp")
	private Parent parent;
	
	@OneToOne(mappedBy="userapp")
	private KinderGarten kindergarten;
	
}
