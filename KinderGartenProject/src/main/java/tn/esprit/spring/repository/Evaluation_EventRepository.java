package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Evaluation_Event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;

@Repository
public interface Evaluation_EventRepository extends CrudRepository<Evaluation_Event,Long>
{
	@Query(value="Select  e.id_event,(SUM(e.Evaluation_value)) as somme from EVALUATION_EVENT e where e.Event_id.kindereventmaker=:kindergarten "
			+ "GROUP BY(e.id_event) ORDER BY somme desc limit 1 " ,nativeQuery=true)
	
	public  Long top_evaluation_event(@Param("kindergarten")KinderGarten kindergarten);
	
	
	@Query("Select i.event_invitation from Invitation_Event i where i.reponse='participe' and i.parent_invitation=:parent and i.event_invitation.etat_event='terminé' ")
	public List<Event> listpassedevents (@Param("parent")Parent parent);
   

	@Query("Select i from  Evaluation_Event i where i.event_evaluation=:event ")
	public List<Evaluation_Event> listevaluations (@Param("event") Event event);
	
	
	@Query("Select i from Evaluation_Event i where i.event_evaluation=:event and i.parent_evaluation=:parent ")
	public List<Evaluation_Event> listparentevaluations (@Param("event") Event event,@Param("parent")Parent parent);
	
	
 }
