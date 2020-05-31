package tn.esprit.spring.Service;

import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.util.List; 

import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Category_event;
import tn.esprit.spring.entities.Discussion_Event;
import tn.esprit.spring.entities.Discussion_EventPk;
import tn.esprit.spring.entities.Etat_Invitation_Event;
import tn.esprit.spring.entities.Etat_event;
import tn.esprit.spring.entities.Evaluation_Event;
import tn.esprit.spring.entities.Evaluation_EventPk;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Facture_Event;
import tn.esprit.spring.entities.Invitation_Event;
import tn.esprit.spring.entities.Invitation_EventPk;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Type_Event;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.DiscussionEventRepository;
import tn.esprit.spring.repository.Evaluation_EventRepository;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.Facture_EventRepository;
import tn.esprit.spring.repository.Invitation_EventRepository;
import tn.esprit.spring.repository.UserRepository;


@Service

//@EnableScheduling
public class EventServiceImpl implements IEventService {
	
	@Autowired
	UserRepository userrepository ;
	@Autowired
	
	EventRepository eventrepository ;
@Autowired
    
    ChildRepository childrepository ;
	
    @Autowired
	
	Facture_EventRepository facturerepository ;

	@Autowired
	
	private Session sessionservice ;
    
	
	@Autowired
	DiscussionEventRepository discussionrepository ;
	
@Autowired
    
    Invitation_EventRepository invitationrepository ;

@Autowired 
Evaluation_EventRepository evaluationrepository ;

@Autowired
private IInvitation_EventService invitationservice ;
	@Override
	public List<String> mylist_event(HttpServletRequest request) {
		
		
		UserApp user = sessionservice.session(request); 
		
		KinderGarten kindergarten = user.getKindergarten() ;
		return eventrepository.findeventsbykindergarten(kindergarten);
		
	}

	@Override
	public UserApp getsession(HttpServletRequest request) {
		UserApp user = sessionservice.session(request); 
        return user ;
	}

	@Override
	public void add_event(Event e,HttpServletRequest request) {
          UserApp user = sessionservice.session(request); 
		  KinderGarten kindergarten = user.getKindergarten() ;
          e.setKindereventmaker(kindergarten);
          e.setNbr_interssants(0);
          e.setNbr_places_occupes(0);
          e.setNbr_participants(0);
          e.setNbr_ignorer(0);
          e.setNbr_invites(0);
     	  eventrepository.save(e);
     	  
   }

	@Override
	public void update_event(Event e,HttpServletRequest request) {
		 UserApp user = sessionservice.session(request); 
		  KinderGarten kindergarten = user.getKindergarten() ;
          e.setKindereventmaker(kindergarten);
     	  eventrepository.save(e);

		  
	}

	@Override
	public void delete_event(Long idevent,HttpServletRequest request) {
		Date date = new Date() ;
		UserApp user = sessionservice.session(request); 
		  KinderGarten kindergarten = user.getKindergarten() ;
		Event e = eventrepository.findById(idevent).get();
		if (e.getKindereventmaker().equals(kindergarten))
			if (e.getDate_final_reservation().after(date))
		eventrepository.deleteById(idevent);		
	        }

	@Override
	public Event detail_event(Long idevent, HttpServletRequest request) {
		UserApp user = sessionservice.session(request); 
		  KinderGarten kindergarten = user.getKindergarten() ;
			Event e = eventrepository.findById(idevent).get();
            return eventrepository.detaileventbykindergarten(kindergarten, idevent) 	;	
	}

	@Override
	public List<String> getAllEventByCategorie(Category_event categorie,HttpServletRequest request) {
		UserApp user = sessionservice.session(request); 
		  KinderGarten kindergarten = user.getKindergarten() ;
		return eventrepository.listeventbycategory(categorie, kindergarten);
	}

	@Override
	//@Scheduled(cron = "0 * * * * ?")
	public void verifier_etat_event() {
		Date date = new Date();
		  List<Event> events = eventrepository.listeventsbykindergarten();
		  for (int i=0;i<events.size();i++) {
			  if (events.get(i).getDate_event().after(date )) {
				  events.get(i).setEtat_event(Etat_event.a_venir);
			  }
			  
			  if (events.get(i).getDate_event().before(date)){
				  events.get(i).setEtat_event(Etat_event.terminé);
			  }
			  
			  if (events.get(i).getEvent_start_heure().getTime() <(date.getTime()) && events.get(i).getEvent_fin_heure().getTime() >(date.getTime())){
				  events.get(i).setEtat_event(Etat_event.en_cours);
			  }
			   eventrepository.save(events.get(i));
			   
		}
		
		
	}

	@Override
	public Event getEventoftoday(HttpServletRequest request) {

		UserApp user = sessionservice.session(request); 
		
		KinderGarten kindergarten = user.getKindergarten() ;
		return eventrepository.getEventPourToday(kindergarten);
	}
//////////////////////////////////////evaluation/////////////////////////////////////////////
	@Override
	public void evaluer_event(Long eventId, int valeur, HttpServletRequest request) {
		Date date = new Date();
		UserApp user = sessionservice.session(request); 
		Parent parent = user.getParent() ;
		Event event = eventrepository.findById(eventId).get();
		Invitation_Event invitation = invitationrepository.getinvitation(parent, event);
		
		if (parent.getInvitations().contains(invitation)){
		//	if (invitation.getEtat_invitation().equals(Etat_Invitation_Event.participe)){
				Evaluation_EventPk evaluationpk = new Evaluation_EventPk() ;
				evaluationpk.setIdEvent(eventId);
				evaluationpk.setIdparentt(parent.getId());
				Evaluation_Event evaluation = new Evaluation_Event() ;
				evaluation.setEvaluationpk(evaluationpk);
				evaluation.setEvaluation_value(valeur);
				evaluation.setEvaluation_date(date);
				evaluationrepository.save(evaluation);
			}
		//	else System.out.println ("vous etes pas participer a cet evenement");
		//	}
		else System.out.println("pas inscrit a cette evenement");
        }

	@Override
	public Event event_most_evalue(HttpServletRequest request) {
        UserApp user = sessionservice.session(request); 
		
		KinderGarten kindergarten = user.getKindergarten() ;
		
		Event event = eventrepository.findById(evaluationrepository.top_evaluation_event(kindergarten)).get();
		
		return event;
	}

	@Override
	public List<Event> getallevents() {
		
		return (List<Event>) eventrepository.findAll();

	}

//	@Override
	//public List<String> geteventstodayjjsf() {
	//	UserApp user = userrepository.findById(1L).get();
	//	KinderGarten kindergarten = user.getKindergarten();
	//	List<String> events = eventrepository.listeventstodayjsf(kindergarten);
	//	return events;
//	}
	
	public Event geteventbyid (long eventid) {
		return eventrepository.findById((long) eventid).get();
	}
///////////////////////////////////////////////////jsf/////////////////////
@Override
public List<Event> eventstodayjsf() {
	UserApp user = userrepository.findById(2L).get();
	KinderGarten kindergarten = user.getKindergarten();
	
	List<Event> eventstoday = eventrepository.eventstodayjsf(kindergarten);
	return eventstoday;
}

@Override
public List<Event> upcomingeventsjsf() {
	UserApp user = userrepository.findById(2L).get();
	KinderGarten kindergarten = user.getKindergarten();
	
	List<Event> upcomingevents = eventrepository.upcomingeventsjsf(kindergarten);
	return upcomingevents;
}

@Override
public void addevent(Event e) {
	Date date = new Date();

	  UserApp user = userrepository.findById(2L).get();
	  KinderGarten kindergarten = user.getKindergarten() ;
      e.setKindereventmaker(kindergarten);
      e.setNbr_interssants(0);
      e.setNbr_places_occupes(0);
      e.setNbr_participants(0);
      e.setNbr_ignorer(0);
      e.setNbr_invites(0);

 	  eventrepository.save(e);
 	 for (int i =0 ;i<childrepository.findParentschilds(kindergarten).size();i++){
         
			Invitation_EventPk invitationpk = new Invitation_EventPk();
			
			invitationpk.setIdEvent(e.getId());
			invitationpk.setIdparent(childrepository.findParentschilds(kindergarten).get(i).getId());
			
			Invitation_Event invitation = new Invitation_Event();
			invitation.setInvitationpk(invitationpk);
			invitation.setReponse("en_attente");
			invitation.setDate_invitation(date);
			invitationrepository.save(invitation);
			e.setNbr_invites(e.getNbr_invites()+1);
			eventrepository.save(e);
			
		}
 	 
 	  

}
@Override
public String getAlphaNumericString(int n) 
  { 

      // chose a Character random from this String 
      String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                  + "0123456789"
                                  + "abcdefghijklmnopqrstuvxyz"; 

      // create StringBuffer size of AlphaNumericString 
      StringBuilder sb = new StringBuilder(n); 

      for (int i = 0; i < n; i++) { 

          // generate a random number between 
          // 0 to AlphaNumericString variable length 
          int index 
              = (int)(AlphaNumericString.length() 
                      * Math.random()); 

          // add Character one by one in end of sb 
          sb.append(AlphaNumericString 
                        .charAt(index)); 
      } 

      return sb.toString(); 
  } 

@Override
@Transactional
public Event geteventbydate(Date date) {
	UserApp user = userrepository.findById(2L).get();
	  KinderGarten kindergarten = user.getKindergarten() ;
	
	return eventrepository.findeventsbydate(kindergarten, date);
}

@Override
public Event eventtodayjsf() {
	UserApp user = userrepository.findById(2L).get();
	KinderGarten kindergarten = user.getKindergarten();
	
	Event  eventstoday = eventrepository.eventtodayjsf(kindergarten);
	return eventstoday;
}
@Override
public List<Event> filterevents(Category_event categorie, Type_Event type, Etat_event etat) {
	UserApp user = userrepository.findById(2L).get();
	KinderGarten kindergarten = user.getKindergarten();
	List<Event> filtredevents = eventrepository.filtredeventsjsf(kindergarten, type, etat, categorie);
	return filtredevents ;
	}

@Override
public void add_commentaire(Long id_event,String commentaire ) {
	Date date = new Date();

	UserApp user = userrepository.findById(1L).get();
	Parent parent = user.getParent() ;
	Event event = eventrepository.findById(id_event).get();
	Discussion_EventPk discussionpk = new Discussion_EventPk();
	discussionpk.setIdEvent(id_event);
	discussionpk.setIdparent(parent.getId());
	discussionpk.setDate_commentaire(date);
	
	Discussion_Event discussion = new Discussion_Event();
	discussion.setDiscussioneventpk(discussionpk);
	discussion.setCommentaire(commentaire);
	
	discussionrepository.save(discussion) ;
	
	
}
@Override
public List<Discussion_Event> listdiscussion(Long id_event) {
	Event event = eventrepository.findById(id_event).get();
	List<Discussion_Event> listdiscussion = discussionrepository.getlistduscussions(event);
	return listdiscussion ;
	
}
@Override
public void update_eventjsf(Long id ,Event e) {
	 UserApp user = userrepository.findById(2L).get();
	  KinderGarten kindergarten = user.getKindergarten() ;
	  e = eventrepository.findById(id).get();
	  e.setKindereventmaker(kindergarten);
 	  eventrepository.save(e);
 	  

}
@Override
public void delete_eventjsf(Long idevent) {
	 UserApp user = userrepository.findById(2L).get();
	  KinderGarten kindergarten = user.getKindergarten() ;
	Event e = eventrepository.findById(idevent).get();
	eventrepository.deleteById(idevent);		

}

@Override
public List<Event> eventassocies(Long idevent) {
	Event e = eventrepository.findById(idevent).get();
    KinderGarten kindergarten = e.getKindereventmaker() ;
    Category_event categorie =e.getCategory() ;
	List<Event> listassocies = eventrepository.eventassociesjsf(kindergarten, categorie, idevent);
	return listassocies ;
}

@Override
public int nbrdisscussion(Long idevent) {
	Event e = eventrepository.findById(idevent).get();
    return discussionrepository.nbrdiscussion(e);
}



///////////////////////////////////////////////////jsf///////////////////////////////////////////////	











}
