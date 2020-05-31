package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Evaluation_Event;
import tn.esprit.spring.entities.KinderGarten;

@Repository
public interface Evaluation_EventRepository extends CrudRepository<Evaluation_Event,Long>
{
	@Query(value="Select  e.id_event,(SUM(e.Evaluation_value)) as somme from EVALUATION_EVENT e where e.Event_id.kindereventmaker=:kindergarten "
			+ "GROUP BY(e.id_event) ORDER BY somme desc limit 1 " ,nativeQuery=true)
	
	public  Long top_evaluation_event(@Param("kindergarten")KinderGarten kindergarten);

}
