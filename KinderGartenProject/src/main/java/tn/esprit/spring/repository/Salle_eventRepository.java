package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Disponibilité_salle;
import tn.esprit.spring.entities.Salle_event;


@Repository
public interface Salle_eventRepository extends CrudRepository<Salle_event, Long> {
	
	@Query("Select s.numero_salle from Salle_event s where s.disponibilité='Disponible'" )
	public List<Integer> listsalledispo() ;

}
