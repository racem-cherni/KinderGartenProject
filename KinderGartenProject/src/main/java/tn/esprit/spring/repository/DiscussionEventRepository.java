package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Discussion_Event;
import tn.esprit.spring.entities.Event;


@Repository
public interface DiscussionEventRepository extends CrudRepository<Discussion_Event,Long> {
	
	@Query("Select d from Discussion_Event d where d.event_discussion=:event")
	public List<Discussion_Event> getlistduscussions (@Param("event")Event event);
	
	@Query("Select count (*) from Discussion_Event d where d.event_discussion=:event ")
	public int nbrdiscussion(@Param("event")Event event);


}


