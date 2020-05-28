package tn.esprit.spring.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString 
@Entity

public class Presence {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idPresence ;
	private String AP ;
	
	
	
	@OneToOne(mappedBy="presence")
	private Seance seance  ;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="presence",fetch=FetchType.LAZY)
	private Collection<Child> Childs  =new ArrayList();
	
	
}
