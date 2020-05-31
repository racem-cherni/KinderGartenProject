package tn.esprit.spring.Service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import tn.esprit.spring.entities.Category_event;
import tn.esprit.spring.entities.Discussion_Event;
import tn.esprit.spring.entities.Etat_event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Type_Event;
import tn.esprit.spring.entities.UserApp;


public interface IEventService {
	
	public List<String> mylist_event(HttpServletRequest request);

	public UserApp getsession(HttpServletRequest request);
	
	public void add_event(Event e,HttpServletRequest request);
	
	public void update_event(Event e,HttpServletRequest request);
	
	public void delete_event(Long idevent,HttpServletRequest request) ;
	
	public Event detail_event(Long idevent ,HttpServletRequest request);
	
	public List<String> getAllEventByCategorie (Category_event categorie,HttpServletRequest request);
	
	public Event getEventoftoday(HttpServletRequest request);
    
	public void verifier_etat_event();
	
	public void evaluer_event(Long eventId, int valeur,HttpServletRequest request);
	
	public Event event_most_evalue (HttpServletRequest request);
	
	///////////////////////////////////////////////jsf///////////////////////////
	public List<Event> getallevents();
	
	//public List<String> geteventstodayjjsf();
	public Event geteventbyid (long eventid);
	
	public List<Event> eventstodayjsf();
	
	public List<Event> upcomingeventsjsf();
	
	public void addevent(Event e);
	public String getAlphaNumericString(int n) ;
	
	public Event geteventbydate (Date date);
	
	public  Event eventtodayjsf();
	
	public List<Event> filterevents(Category_event categorie,Type_Event type, Etat_event etat);
	
	public void add_commentaire(Long id_event,String commentaire);
	
	public List<Discussion_Event> listdiscussion(Long id_event);

	public void update_eventjsf(Long id ,Event e);
	
	public void delete_eventjsf(Long idevent);
	
	public List<Event> eventassocies(Long idevent);
	
	public int nbrdisscussion(Long idevent);
	

	
}
