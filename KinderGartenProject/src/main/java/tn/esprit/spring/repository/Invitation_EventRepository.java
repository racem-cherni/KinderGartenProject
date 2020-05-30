package tn.esprit.spring.repository;

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

}
