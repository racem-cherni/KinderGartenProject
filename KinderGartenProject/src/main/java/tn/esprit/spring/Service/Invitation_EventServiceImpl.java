package tn.esprit.spring.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Etat_Invitation_Event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Invitation_Event;
import tn.esprit.spring.entities.Invitation_EventPk;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Type_Event;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.Facture_EventRepository;
import tn.esprit.spring.repository.Invitation_EventRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class Invitation_EventServiceImpl implements IInvitation_EventService {
	
	@Autowired
	UserRepository userrepository ;
	
    @Autowired
	
	EventRepository eventrepository ;
	
    @Autowired
	
	private Session sessionservice ;
    
	@Autowired
	UserServices userservices ;
    
    @Autowired
    
    Invitation_EventRepository invitationrepository ;
    
    @Autowired
    
    ParentRepository parentrepository ;
    
@Autowired
    
    ChildRepository childrepository ;

@Override
public Invitation_Event getinvitationevent (Long idevent)
{
	UserApp user = userrepository.findById(1L).get();
	Parent parent = user.getParent() ;
	Event event = eventrepository.findById(idevent).get();
	return invitationrepository.getinvitation(parent, event);
}

    @Override
	public void inviter_parent_event(Long id_event, Long id_parent, HttpServletRequest request) {
		
		Date date = new Date();
		
        UserApp user = sessionservice.session(request); 
		KinderGarten kindergarten = user.getKindergarten() ;
		
		Event e = eventrepository.findById(id_event).get();
		Parent p = parentrepository.findById(id_parent).get();
		if (e.getKindereventmaker().equals(kindergarten)){
			for (int i =0 ;i<childrepository.findParentschilds(kindergarten).size();i++){
			if (childrepository.findParentschilds(kindergarten).get(i).equals(p)) {
				
				Invitation_EventPk invitationpk = new Invitation_EventPk();
				
				invitationpk.setIdEvent(id_event);
				invitationpk.setIdparent(id_parent);
				
				Invitation_Event invitation = new Invitation_Event();
				invitation.setInvitationpk(invitationpk);
				invitation.setReponse("en_attente");
				invitation.setDate_invitation(date);
				invitationrepository.save(invitation);
				e.setNbr_invites(e.getNbr_invites()+1);
				eventrepository.save(e);
				
			}
			
			
		}

		}
		
		
	}
	
	@Override
	public List<Parent> listparentskindergarten (HttpServletRequest request){
		UserApp user = sessionservice.session(request); 
		KinderGarten kindergarten = user.getKindergarten() ;
		
		System.out.print(childrepository.findParentschilds(kindergarten));
		return childrepository.findParentschilds(kindergarten);
		
	}
	
	
	
	
	
	@Override
	public void inviter_tousparent_event(Long id_event, HttpServletRequest request) {
Date date = new Date();
		
        UserApp user = sessionservice.session(request); 
		KinderGarten kindergarten = user.getKindergarten() ;
		Event e = eventrepository.findById(id_event).get();
		
		if (e.getKindereventmaker().equals(kindergarten)){
			for (int i =0 ;i<childrepository.findParentschilds(kindergarten).size();i++){
                
				Invitation_EventPk invitationpk = new Invitation_EventPk();
				
				invitationpk.setIdEvent(id_event);
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
		
		
		
	}

	@Override
	public void participer_parent_event(Long id_event, Etat_Invitation_Event etat, HttpServletRequest request)  {
	/*	Date date = new Date();
		UserApp user = sessionservice.session(request); 
		Parent parent = user.getParent() ;
		Event event = eventrepository.findById(id_event).get();
		Invitation_Event invitation = invitationrepository.getinvitation(parent, event);
        
		
		if (parent.getInvitations().contains(invitation)){
			
			if (invitation.getEtat_invitation().equals(Etat_Invitation_Event.en_attente)){
				event.setNbr_invites(event.getNbr_invites()-1);
			}
			
			
			if (etat.equals(Etat_Invitation_Event.interesse))
			{
				if (invitation.getEtat_invitation().equals(etat)){
					System.out.println("cet evenement  avec l'id " + id_event + " est deja "+etat)	;
				}
				else
				{
					invitation.setDate_reponse(date);
					invitation.setEtat_invitation(etat);
					event.setNbr_interssants(event.getNbr_interssants()+1);
					eventrepository.save(event);
				}
			}
			
			if (etat.equals(Etat_Invitation_Event.participe))
			{
				if (invitation.getEtat_invitation().equals(etat)){
					System.out.println("cet evenement  avec l'id " + id_event + " est deja "+etat)	;
				}
				else {
					if (event.getNbr_participants()<event.getNbr_places() && event.getDate_final_reservation().after(date))
					{
						
						if (invitation.getEtat_invitation().equals(Etat_Invitation_Event.interesse))
						{
							
							invitation.setDate_reponse(date);
							invitation.setEtat_invitation(etat);
							if(event.getType_event().equals(Type_Event.Public)){
							event.setNbr_participants(event.getNbr_participants() +1+ parent.getChilds().size());
							}
							if(event.getType_event().equals(Type_Event.Kids)){
								event.setNbr_participants(event.getNbr_participants() +parent.getChilds().size());
								}
							event.setNbr_interssants(event.getNbr_interssants()-1);
							eventrepository.save(event);
							
					    }
						else
						{ 
						    invitation.setDate_reponse(date);
						invitation.setEtat_invitation(etat);
						if(event.getType_event().equals(Type_Event.Public)){
							event.setNbr_participants(event.getNbr_participants() +1+ parent.getChilds().size());
							}
							if(event.getType_event().equals(Type_Event.Kids)){
								event.setNbr_participants(event.getNbr_participants() +parent.getChilds().size());
								}							eventrepository.save(event);

						}
						
					}
					
				    }
				
			}
			if (etat.equals(Etat_Invitation_Event.Ignorer))
			{
				if (invitation.getEtat_invitation().equals(etat)){
					System.out.println("cet evenement  avec l'id " + id_event + " est deja "+etat)	;
				}
				else{
				invitation.setDate_reponse(date);
				invitation.setEtat_invitation(etat);
				event.setNbr_ignorer(event.getNbr_ignorer()+1);
				eventrepository.save(event);
			}
				}
		invitationrepository.save(invitation);
		}*/
		}

	
	
	@Override
	public void annuler_participation_event(Long id_event,HttpServletRequest request) {
	/*	Date date = new Date();
		UserApp user = sessionservice.session(request); 
		Parent parent = user.getParent() ;
		Event event = eventrepository.findById(id_event).get();
		Invitation_Event invitation = invitationrepository.getinvitation(parent, event);
		
		if (parent.getInvitations().contains(invitation)){
			if (invitation.getEtat_invitation().equals(Etat_Invitation_Event.participe)){
				if(event.getDate_event().after(date)){
					if(event.getType_event().equals(Type_Event.Public)){
						event.setNbr_participants(event.getNbr_participants() -1 - parent.getChilds().size());

					}
					if(event.getType_event().equals(Type_Event.Kids)){
						event.setNbr_participants(event.getNbr_participants() - parent.getChilds().size());
						}
					invitation.setEtat_invitation(Etat_Invitation_Event.Annuleé);
					invitation.setDate_reponse(date);
					invitationrepository.save(invitation);
					eventrepository.save(event);
					

					
				}
				else System.out.println("cet evenement avec l'id "+id_event+"est deja en cours");

				
			}
            else System.out.println ("vous etes pas participer a cette event avec l'id "+id_event);

				
			
		}
        else System.out.println ("vous etes pas inscrit  cette event avec l'id "+id_event);
	*/
	}
/////////////////////////////////////////////jsf////////////////////////////////////////////////////////////
	@Override
	@Transactional
	public void participer_parentjsf(Long id_event) {
		Date date = new Date();
		UserApp user = userrepository.findById(1L).get();
		Parent parent = user.getParent() ;
		Event event = eventrepository.findById(id_event).get();
		Invitation_Event invitation = invitationrepository.getinvitation(parent, event);
		
if (parent.getInvitations().contains(invitation)){
			
			if (invitation.getReponse().equals("en_attente")){
				event.setNbr_invites(event.getNbr_invites()-1);
				
			}
			
			if (invitation.getReponse().equals("participe"))
			{
				
					System.out.println("cet evenement  avec l'id " + id_event + " est deja participe ")	;

			}
				else {
					if (event.getNbr_places_occupes()<event.getNbr_places() && event.getDate_final_reservation().after(date))
					{
						
						if (invitation.getReponse().equals("intéressé"))
						{
							
							invitation.setDate_reponse(date);
							invitation.setReponse("participe");
							if(event.getType_event().equals(Type_Event.Public)){
						    event.setNbr_places_occupes(event.getNbr_places_occupes() +1 + parent.getChilds().size());
							event.setNbr_participants(event.getNbr_participants() +1 );
							}
							if(event.getType_event().equals(Type_Event.Kids)){
								event.setNbr_places_occupes(event.getNbr_places_occupes() +parent.getChilds().size());
								event.setNbr_participants(event.getNbr_participants() +1 );

								}
							event.setNbr_interssants(event.getNbr_interssants()-1);
							eventrepository.save(event);
							
					    }
						else
						{ 
						    invitation.setDate_reponse(date);
						invitation.setReponse("participe");
						if(event.getType_event().equals(Type_Event.Public)){
							event.setNbr_places_occupes(event.getNbr_places_occupes() +1+ parent.getChilds().size());
							event.setNbr_participants(event.getNbr_participants() +1 );

							}
							if(event.getType_event().equals(Type_Event.Kids)){
								event.setNbr_places_occupes(event.getNbr_places_occupes() +parent.getChilds().size());
								event.setNbr_participants(event.getNbr_participants() +1 );

								}
							eventrepository.save(event);


						}
						
					}
					else 
						System.out.println("la date finale de reservation est atteint");
					
				    }
			invitationrepository.save(invitation);

			}
			
		}
	@Override
	@Transactional
	public void annuler_participation_eventjsf(Long id_event) {
		Date date = new Date();
		UserApp user = userrepository.findById(1L).get();
		Parent parent = user.getParent() ;
		Event event = eventrepository.findById(id_event).get();
		Invitation_Event invitation = invitationrepository.getinvitation(parent, event);
		if(event.getDate_event().after(date)){
			if(event.getType_event().equals(Type_Event.Public)){
		event.setNbr_places_occupes(event.getNbr_places_occupes() -1 - parent.getChilds().size());
		event.setNbr_participants(event.getNbr_participants() -1 );
                                                                }
			if(event.getType_event().equals(Type_Event.Kids)){
				event.setNbr_places_occupes(event.getNbr_places_occupes() -parent.getChilds().size());
				event.setNbr_participants(event.getNbr_participants() -1 );
                                                            }
			eventrepository.save(event);
			invitation.setDate_reponse(date);
			invitation.setReponse("annulé");
			invitationrepository.save(invitation);
           }
		else System.out.println("le event est deja en cours ");
		
		
	}
		
		
	@Override
	@Transactional
	public void interesser_parentjsf(Long id_event) {
		Date date = new Date();
		UserApp user = userrepository.findById(1L).get();
		Parent parent = user.getParent() ;
		Event event = eventrepository.findById(id_event).get();
		Invitation_Event invitation = invitationrepository.getinvitation(parent, event);
		if (parent.getInvitations().contains(invitation)){
			if (invitation.getReponse().equals("en_attente")){
				event.setNbr_invites(event.getNbr_invites()-1);
			}
			
	     if (invitation.getReponse().equals("intéressé"))
		{
				System.out.println("cet evenement  avec l'id " + id_event + " est deja interesse ");
		}
			else
			{
				invitation.setDate_reponse(date);
				invitation.setReponse("intéressé");
				event.setNbr_interssants(event.getNbr_interssants()+1);
				eventrepository.save(event);

			}
			invitationrepository.save(invitation);

	}
	}
	@Override
	public void annuler_interesser_eventjsf(Long id_event) {
		Date date = new Date();
		UserApp user = userrepository.findById(1L).get();
		Parent parent = user.getParent() ;
		Event event = eventrepository.findById(id_event).get();
		Invitation_Event invitation = invitationrepository.getinvitation(parent, event);
		invitation.setDate_reponse(date);
		invitation.setReponse("annulé");
		event.setNbr_interssants(event.getNbr_interssants()-1);
		eventrepository.save(event);
		invitationrepository.save(invitation);

}
	
	@Override
	public void inviter_tousparent_eventjsf(Event e) {

Date date = new Date();
		
       UserApp user = userrepository.findById(2L).get();
		KinderGarten kindergarten = user.getKindergarten() ;
		
		if (e.getKindereventmaker().equals(kindergarten)){
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
		
		
	}

	
	@Override
	public List<Event> listinteressteevents() {
		UserApp user = userrepository.findById(1L).get();
		Parent parent = user.getParent() ;
		return invitationrepository.listeventsinteresses(parent);
	}

	@Override
	public List<Event> listparticipatedevents() {
	
		UserApp user = userrepository.findById(1L).get();
		Parent parent = user.getParent() ;
		return invitationrepository.listeventsparticipated(parent);
	}
	
	@Override
	public Invitation_Event invitationparent(Long id){
		UserApp user = userservices.currentUserJsf();

		Parent parent = user.getParent() ;
		Event event = eventrepository.findById(id).get();

		
		return invitationrepository.getinvitation(parent, event);

	}

	@Override
	public List<Parent> listparentparticipes(Long idevent) {
		Event event = eventrepository.findById(idevent).get();
		return invitationrepository.listparentparticipes(event);
	}

	@Override
	public List<Parent> listparentinteressess(Long idevent) {
		Event event = eventrepository.findById(idevent).get();
		return invitationrepository.listparentinteresses(event);
	}
	
	
/////////////////////////////////////////////////////////jsf///////////////////////////////////////////////////////

	


}
