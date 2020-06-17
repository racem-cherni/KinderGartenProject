package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Galerie_event;

public interface GalerieEventRepository extends CrudRepository<Galerie_event,Long> {
	
	@Query("Select g from Galerie_event g where g.events=:event")
	public List<Galerie_event> listgalerieevent(@Param("event")Event event);

}
