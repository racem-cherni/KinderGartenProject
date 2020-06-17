package tn.esprit.spring.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Invitation_Event;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;

@Repository
public interface Invitation_EventRepository extends CrudRepository<Invitation_Event,Long> {
	
	@Query("Select i from Invitation_Event i where i.parent_invitation=:parent and i.event_invitation=:event")
	
	public Invitation_Event getinvitation (@Param("parent")Parent parent,@Param("event")Event event);
	
	@Query("Select i.event_invitation from Invitation_Event i where i.reponse='intéressé' and i.parent_invitation=:parent and i.event_invitation.etat_event='a_venir' order by i.date_reponse ")
	public List<Event> listeventsinteresses(@Param("parent")Parent parent);
	
	@Query("Select i.event_invitation from Invitation_Event i where i.reponse='participe' and i.parent_invitation=:parent and i.event_invitation.etat_event='a_venir'")
	public List<Event> listeventsparticipated(@Param("parent")Parent parent);
	
	@Query("Select i.parent_invitation from Invitation_Event i where i.reponse='participe' and i.event_invitation=:event")
	public List<Parent> listparentparticipes(@Param("event")Event event) ;
	
	@Query("Select i.parent_invitation from Invitation_Event i where i.reponse='intéressé' and i.event_invitation=:event")
	public List<Parent> listparentinteresses(@Param("event")Event event) ;

}
