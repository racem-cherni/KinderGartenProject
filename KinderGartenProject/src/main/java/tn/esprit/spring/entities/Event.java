package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="EVENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event implements Serializable{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Event_id")
    private int id;
	
	@Column(name="Event_title")
	private String title;

	@Column(name="Event_location")
	private String location;
	

	@Column(name="Event_Photo")
    private String photo;

	@Enumerated(EnumType.STRING)
	private Category category;
	
	
	
	@Column(name="Event_Description")
	private String description;
	
	@Temporal (TemporalType.DATE)
	@Column(name="Event_start_Date")
	private Date start_date;
	
	
	@Column(name="Event_start_heure")
	private java.sql.Time event_start_heure ;

	@Temporal (TemporalType.DATE)
	@Column(name="Event_End_Date")
	private Date end_date;
	
	
	
	@Temporal (TemporalType.DATE)
    @Column(name="Event_Final_reservation")
	private Date date_final_reservation;
	
	
	@Column(name="Event_Nbr_Place")
	private int Nbr_places;
	
	@Column(name="Event_Nbr_Participants")
	private int Nbr_participants;
	
	@Column(name="Event_Nbr_interessants")
	private int Nbr_interssants;
	
	@Column(name="Event_budget")
	private int event_budget;
	
	@JsonIgnore
	@ManyToOne
	private KinderGarten  kinderGarten;
	
	
}



