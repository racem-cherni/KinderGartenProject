package tn.esprit.spring.Controller;

import java.io.IOException;  
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import tn.esprit.spring.Service.IEventService;
import tn.esprit.spring.Service.IInvitation_EventService;
import tn.esprit.spring.entities.Category_event;
import tn.esprit.spring.entities.Discussion_Event;
import tn.esprit.spring.entities.Etat_Invitation_Event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Invitation_Event;
import tn.esprit.spring.entities.Locationevent;
import tn.esprit.spring.entities.Salle_event;
import tn.esprit.spring.entities.Type_Event;



@Scope(value = "session")
@Controller(value = "eventkindergartenController")
@ELBeanName(value = "eventkindergartenController")
@Join(path = "/event", to = "/pages/Parent/Event/WelcomeEvent.jsf")
public class ControllerEventImpl {
	
	@Autowired
    IEventService ieventservice ;
	
	@Autowired
    IInvitation_EventService iinvitationservice ;
	
	private Locationevent location_event ;

	private int nbrdusscussions;
	
	private List<Discussion_Event> listdiscussions ;
	
	private List<Event> events ;
	
	private Double entry_price ;

	private List<Event> eventsassocié;

	
	private List<Event> todayevents;
	
	private List<Event> upcomingevents;
   
	private Event showevent ;
	
	private String commentaire ;
	
	private String  month ;
	
	private String title;
	
	private String description ;
	
	private Date dateevent ;
	
	private java.sql.Time heurestart ;
	
	private java.sql.Time heurefin;
	
	private Date datefinreservation;
	
	private int nb_participants;

	private int nb_interssants;
	
	private int nbr_places_occupe;
	
	private int nbr_places ;


	private Category_event category ;
	
	private Type_Event type_event;
	
	private Salle_event salle_event;
	
	private Long idevent ;
	
	private Etat_Invitation_Event etatinvitevent ;

	public Etat_Invitation_Event[] getEtat_Invitation_Events(){
		return Etat_Invitation_Event.values();
	}
	
	
	public Etat_Invitation_Event getEtatinvitevent() {
		return etatinvitevent;
	}


	public void setEtatinvitevent(Etat_Invitation_Event etatinvitevent) {
		this.etatinvitevent = etatinvitevent;
	}


	public int getNb_participants() {
		return nb_participants;
	}


	public void setNb_participants(int nb_participants) {
		this.nb_participants = nb_participants;
	}


	
	
	


	public Locationevent getLocation_event() {
		return location_event;
	}


	public void setLocation_event(Locationevent location_event) {
		this.location_event = location_event;
	}


	public int getNbr_places() {
		return nbr_places;
	}


	public void setNbr_places(int nbr_places) {
		this.nbr_places = nbr_places;
	}


	
	
	public String getCommentaire() {
		return commentaire;
	}


	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}


	public int getNbr_places_occupe() {
		return nbr_places_occupe;
	}


	public void setNbr_places_occupe(int nbr_places_occupe) {
		this.nbr_places_occupe = nbr_places_occupe;
	}


	public int getNb_interssants() {
		return nb_interssants;
	}


	public void setNb_interssants(int nb_interssants) {
		this.nb_interssants = nb_interssants;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getDateevent() {
		return dateevent;
	}


	public void setDateevent(Date dateevent) {
		this.dateevent = dateevent;
	}


	public java.sql.Time getHeurestart() {
		return heurestart;
	}


	public void setHeurestart(java.sql.Time heurestart) {
		this.heurestart = heurestart;
	}


	public java.sql.Time getHeurefin() {
		return heurefin;
	}


	public void setHeurefin(java.sql.Time heurefin) {
		this.heurefin = heurefin;
	}


	public Date getDatefinreservation() {
		return datefinreservation;
	}


	public void setDatefinreservation(Date datefinreservation) {
		this.datefinreservation = datefinreservation;
	}

	

	


	


	public int getNbrdusscussions() {
		 nbrdusscussions = ieventservice.nbrdisscussion(idevent) ;
		 return nbrdusscussions ;
	}


	public void setNbrdusscussions(int nbrdusscussions) {
		this.nbrdusscussions = nbrdusscussions;
	}


	public Category_event getCategory() {
		return category;
	}


	public void setCategory(Category_event category) {
		this.category = category;
	}


	public Type_Event getType_event() {
		return type_event;
	}


	public void setType_event(Type_Event type_event) {
		this.type_event = type_event;
	}


	public Salle_event getSalle_event() {
		return salle_event;
	}


	public void setSalle_event(Salle_event salle_event) {
		this.salle_event = salle_event;
	}


	public Event Eventtpop(int eventid) {
		System.out.println("event"+eventid);
		this.showevent =  ieventservice.geteventbyid(eventid);
		return showevent;
	}
	
	
    public Event getShowevent() {
		System.out.println("evenn");

       return showevent;
	}

    public void setShowevent(Event showevent) {
		this.showevent = showevent;
	}

    public List<Event> getEvents() {
		events = ieventservice.getallevents();
		return events ;
	}



	public void setEvents(List<Event> events) {
		this.events = events;
	}


	public List<Event> getTodayevents() {
		todayevents = ieventservice.eventstodayjsf();
		return todayevents;
	}


	public void setTodayevents(List<Event> todayevents) {
		this.todayevents = todayevents;
	}


	public List<Event> getUpcomingevents() {
		upcomingevents = ieventservice.upcomingeventsjsf();
		// month = new SimpleDateFormat("MMMM").format(upcomingevents.get(i).getDate_event()) ;
		
		return upcomingevents;
	}


	public void setUpcomingevents(List<Event> upcomingevents) {
		this.upcomingevents = upcomingevents;
	}


	public String getMonth(Date d) {
		month = new SimpleDateFormat("MMMM").format(d);	
		return month;
		}
	


	public void setMonth(String month) {
		this.month = month;
	}
	
	/*public void gotoupcomingevents() {
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpServletRequest origRequest = (HttpServletRequest)context.getExternalContext().getRequest();
	    String contextPath = origRequest.getContextPath();
	try {
	        FacesContext.getCurrentInstance().getExternalContext()
	                .redirect(contextPath  + "/pages/Parent/Event/Upcomingevent.jsf");
		
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
	

	}*/
	
	public String gotoupcomingevents() {
		String navigateTo ="null";
		navigateTo = "/pages/Parent/Event/Upcomingevent.xhtml?faces-redirect=false";
	    return navigateTo;
	

	}
	    public String gottodetailevent(Event event) {
		String navigateTo ="null";
		this.setIdevent(event.getId());
		this.setTitle(event.getTitle());
		this.setDescription(event.getDescription());
		this.setDateevent(event.getDate_event());
		this.setHeurestart(event.getEvent_start_heure());
		this.setHeurefin(event.getEvent_fin_heure());
		this.setSalle_event(event.getSalle_event());
		this.setCategory(event.getCategory());
		this.setNb_interssants(event.getNbr_interssants());
		this.setNb_participants(event.getNbr_participants());
		this.setNbr_places_occupe(event.getNbr_places_occupes());
		this.setNbr_places(event.getNbr_places());
		this.setDatefinreservation(event.getDate_final_reservation());
		this.setType_event(event.getType_event());
		this.setLocation_event(event.getLocation_event());
		navigateTo = "/pages/Parent/Event/detailevent.xhtml?faces-redirect=false";
		
	    return navigateTo;
	    
	}
	    
		public String gotohomeevents() {
			String navigateTo ="null";
			navigateTo = "/pages/Parent/Event/WelcomeEvent.xhtml?faces-redirect=true";
		    return navigateTo;
		}
		@Transactional
		public void participateevent() {
			//String navigateTo ="/pages/Parent/Event/WelcomeEvent.xhtml?faces-redirect=true";
			iinvitationservice.participer_parentjsf(idevent);
			
			
		//	return navigateTo;

			
		}
		@Transactional
		public void interesserevent() {
		//	String navigateTo ="/pages/Parent/Event/WelcomeEvent.xhtml?faces-redirect=true";
			iinvitationservice.interesser_parentjsf(idevent);
		//	return navigateTo;

			
		}
		
		public Event geteventbyid (){
			return  ieventservice.geteventbyid(idevent);
			
		}
		
		public boolean verifieretat (Long id){
			if (iinvitationservice.getinvitationevent(id).getReponse().equals("participe"))
			return true ;
			else
			return false;
		}

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public void afficher(String titlee)
	{
		this.title = titlee;
	}


	public Long getIdevent() {
		return idevent;
	}


	public void setIdevent(Long idevent) {
		this.idevent = idevent;
	}

	
	public String addcommentaire(){
		String navigateTo ="null";
		ieventservice.add_commentaire(idevent, commentaire);
		return navigateTo;
	}


	public List<Discussion_Event> getListdiscussions() {
		listdiscussions = ieventservice.listdiscussion(idevent);
		return listdiscussions ; 
	}


	public void setListdiscussions(List<Discussion_Event> listdiscussions) {
		this.listdiscussions = listdiscussions;
	}


	public Double getEntry_price() {
		return entry_price;
	}


	public void setEntry_price(Double entry_price) {
		this.entry_price = entry_price;
	}


	public List<Event> getEventsassocié() {
	 eventsassocié = ieventservice.eventassocies(idevent);
	 return eventsassocié; 
	}


	public void setEventsassocié(List<Event> eventsassocié) {
		this.eventsassocié = eventsassocié;
	}

   
	



	

}
