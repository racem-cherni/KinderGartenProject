package tn.esprit.spring.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Etat_Invitation_Event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Invitation_Event;
import tn.esprit.spring.entities.Parent;

public interface IInvitation_EventService {
	public Invitation_Event getinvitationevent (Long idevent);

	
	public void inviter_parent_event (Long id_event,Long id_parent,HttpServletRequest request);
	
	public void inviter_tousparent_event (Long id_event,HttpServletRequest request);
	
	public void participer_parent_event (Long id_event,Etat_Invitation_Event etat,HttpServletRequest request);
	
	public void annuler_participation_event (Long id_event,HttpServletRequest request);
	
	public List<Parent> listparentskindergarten (HttpServletRequest request);

	//////////////////////////jsf////////////////////////
	
	public void participer_parentjsf(Long id_event);
	
	public void interesser_parentjsf(Long id_event);
	public void inviter_tousparent_eventjsf(Event e);
	public void annuler_participation_eventjsf (Long id_event); 
	public void annuler_interesser_eventjsf (Long id_event);
	public List<Event> listinteressteevents () ;
	
	public List<Event> listparticipatedevents () ;
	
	public Invitation_Event invitationparent(Long id);

    public List<Parent> listparentparticipes(Long idevent);
    
    public List<Parent> listparentinteressess(Long idevent);
    

	
}
