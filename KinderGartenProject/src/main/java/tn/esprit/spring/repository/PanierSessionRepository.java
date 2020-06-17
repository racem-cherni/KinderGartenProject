package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.PanierSession;

public interface PanierSessionRepository extends CrudRepository <PanierSession, Integer>{
	
	@Query("SELECT o FROM PanierSession o WHERE o.user.id = :user")
	PanierSession getPanierSessionByUser (@Param("user") Long user);
	
	

}
