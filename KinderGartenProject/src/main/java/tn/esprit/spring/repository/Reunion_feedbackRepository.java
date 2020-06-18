package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.Reunion_feedback;

public interface Reunion_feedbackRepository extends CrudRepository<Reunion_feedback, Integer> {

	@Query("Select "
			+ "rec from Reunion_feedback rec  "
			+ "join rec.parent u "
			+ "where u=:userd")
    public List<Reunion_feedback> getAllfbByParentt(@Param("userd") Parent user);
    
	@Query("Select "
			+ "rec from Reunion_feedback rec  "
			+ "join rec.reunion r "
			+ "join r.jardin u "
			+ "where u=:userd")
    public List<Reunion_feedback> getAllfbByJardinn(@Param("userd") KinderGarten user);
    
    
}
