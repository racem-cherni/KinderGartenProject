package tn.esprit.spring.entities;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.TransactionScoped;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table(name="EVENT")
public class Event implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Event_id")
	private Long id;	
	
	@Column(name="Event_title")
	private String title;

	
	

	@Column(name="Event_Photo")
    private String photo;
	
	@Column(name="Event_Description")
	private String description;
	
	@Temporal (TemporalType.DATE)
	@Column(name="Event_Date")
	private Date date_event;
	
	
	@Column(name="Event_start_heure")
	private java.sql.Time event_start_heure ;


	
	@Column(name="Event_fin_heure")
	private java.sql.Time event_fin_heure ;

	
	@Temporal (TemporalType.DATE)
    @Column(name="Event_Final_reservation")
	private Date date_final_reservation;
	
	
	@Column(name="Event_Nbr_Place")
    private int nbr_places;
	
	@Column(name="Event_Nbr_Participants")
	private int nbr_participants;
	
	@Column(name="Event_Nbr_interessants")
	private int nbr_interssants;
	
	@Column(name="Event_Nbr_places_occupes")
	private int nbr_places_occupes;
	
	@Column(name="Event_Nbr_ignorer")
	private int Nbr_ignorer;
	
	@Column(name="Event_invites")
	private int nbr_invites;
	
	@Column(name="Event_budget")
	private Double event_budget;
	
	@Column(name="Entry_price")
	private Double entry_price;
	
	@Enumerated(EnumType.STRING)
	private Category_event category;
	
	@Enumerated(EnumType.STRING)
	private Etat_event etat_event;
	
	@Enumerated(EnumType.STRING)
	private Type_Event type_event;
	
	@Enumerated(EnumType.STRING)
	private Locationevent location_event ;
	
	//dddd////
	@JsonIgnore
    @ManyToOne
	KinderGarten kindereventmaker;

	
    @JsonIgnore
	@OneToOne
	private Salle_event salle_event ;
	
	@JsonIgnore
	@OneToOne
	private Facture_Event facture_event ;
	
    @JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="event")
	private  List<Reservation_Stock_interne> reservation_stock_interne;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy="event_invitation")
	private  List<Invitation_Event> invitations;
	
    @JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL, mappedBy="event_evaluation")
	private  List<Evaluation_Event> evaluations; 
	
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, mappedBy="event_discussion")
	private  List<Discussion_Event> Discussions;

	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public int getNbr_places_occupes() {
		return nbr_places_occupes;
	}



	public void setNbr_places_occupes(int nbr_places_occupes) {
		this.nbr_places_occupes = nbr_places_occupes;
	}



	public Date getDate_event() {
		return date_event;
	}



	public void setDate_event(Date date_event) {
		this.date_event = date_event;
	}



	public java.sql.Time getEvent_start_heure() {
		return event_start_heure;
	}

	public void setEvent_start_heure(java.sql.Time event_start_heure) {
		this.event_start_heure = event_start_heure;
	}

	

	public java.sql.Time getEvent_fin_heure() {
		return event_fin_heure;
	}

	public void setEvent_fin_heure(java.sql.Time event_fin_heure) {
		this.event_fin_heure = event_fin_heure;
	}

	public Date getDate_final_reservation() {
		return date_final_reservation;
	}

	public void setDate_final_reservation(Date date_final_reservation) {
		this.date_final_reservation = date_final_reservation;
	}

	
	
	public int getNbr_places() {
		return nbr_places;
	}



	public void setNbr_places(int nbr_places) {
		this.nbr_places = nbr_places;
	}



	public int getNbr_participants() {
		return nbr_participants;
	}



	public void setNbr_participants(int nbr_participants) {
		this.nbr_participants = nbr_participants;
	}



	public int getNbr_interssants() {
		return nbr_interssants;
	}



	public void setNbr_interssants(int nbr_interssants) {
		this.nbr_interssants = nbr_interssants;
	}



	public Double getEvent_budget() {
		return event_budget;
	}



	public void setEvent_budget(Double event_budget) {
		this.event_budget = event_budget;
	}



	



	public KinderGarten getKindereventmaker() {
		return kindereventmaker;
	}



	public void setKindereventmaker(KinderGarten kindereventmaker) {
		this.kindereventmaker = kindereventmaker;
	}



	public Category_event getCategory() {
		return category;
	}

	public void setCategory(Category_event category) {
		this.category = category;
	}

	public Etat_event getEtat_event() {
		return etat_event;
	}

	public void setEtat_event(Etat_event etat_event) {
		this.etat_event = etat_event;
	}

	

   public Salle_event getSalle_event() {
		return salle_event;
	}

	public void setSalle_event(Salle_event salle_event) {
		this.salle_event = salle_event;
	}

	public Facture_Event getFacture_event() {
		return facture_event;
	}

	public void setFacture_event(Facture_Event facture_event) {
		this.facture_event = facture_event;
	}

	public List<Reservation_Stock_interne> getReservation_stock_interne() {
		return reservation_stock_interne;
	}

	public void setReservation_stock_interne(List<Reservation_Stock_interne> reservation_stock_interne) {
		this.reservation_stock_interne = reservation_stock_interne;
	}

	

	public List<Invitation_Event> getInvitations() {
		return invitations;
	}



	public void setInvitations(List<Invitation_Event> invitations) {
		this.invitations = invitations;
	}



	public List<Evaluation_Event> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation_Event> evaluations) {
		this.evaluations = evaluations;
	}


	public Type_Event getType_event() {
		return type_event;
	}



	public void setType_event(Type_Event type_event) {
		this.type_event = type_event;
	}



	public int getNbr_ignorer() {
		return Nbr_ignorer;
	}



	public void setNbr_ignorer(int nbr_ignorer) {
		Nbr_ignorer = nbr_ignorer;
	}



	public int getNbr_invites() {
		return nbr_invites;
	}



	public void setNbr_invites(int nbr_invites) {
		nbr_invites = nbr_invites;
	}

	


	public Double getEntry_price() {
		return entry_price;
	}



	public void setEntry_price(Double entry_price) {
		this.entry_price = entry_price;
	}

    public List<Discussion_Event> getDiscussions() {
		return Discussions;
	}

    public void setDiscussions(List<Discussion_Event> discussions) {
		Discussions = discussions;
	}
    
    
     
    public Locationevent getLocation_event() {
		return location_event;
	}



	public void setLocation_event(Locationevent location_event) {
		this.location_event = location_event;
	}



	@Override
	public String toString() {
		return "Event [id=" + id + ", title=" + title + ", photo=" + photo + ", description=" + description
				+ ", date_event=" + date_event + ", event_start_heure=" + event_start_heure + ", event_fin_heure="
				+ event_fin_heure + ", date_final_reservation=" + date_final_reservation + ", nbr_places=" + nbr_places
				+ ", nbr_participants=" + nbr_participants + ", nbr_interssants=" + nbr_interssants
				+ ", nbr_places_occupes=" + nbr_places_occupes + ", Nbr_ignorer=" + Nbr_ignorer + ", nbr_invites="
				+ nbr_invites + ", event_budget=" + event_budget + ", entry_price=" + entry_price + ", category="
				+ category + ", etat_event=" + etat_event + ", type_event=" + type_event + ", kindereventmaker="
				+ kindereventmaker +  "]";
	}



	public Event(String title,String description, Date date_event, Date date_final_reservation,java.sql.Time event_start_heure,java.sql.Time event_fin_heure, int nbr_places,String photo, Double entry_price,
			Category_event category, Type_Event type_event,Locationevent location_event) {
		super();
		this.title = title;
		this.photo = photo;
		this.description = description;
		this.date_event = date_event;

		this.date_final_reservation = date_final_reservation;
		this.nbr_places = nbr_places;
		this.entry_price = entry_price;
		this.category = category;
		this.type_event = type_event;
		this.event_start_heure=event_start_heure;
		this.event_fin_heure = event_fin_heure ;
		this.location_event = location_event;

	}



	
	
	
	
}
	


	


	


	



	
     
	
	
	
	
	




	



