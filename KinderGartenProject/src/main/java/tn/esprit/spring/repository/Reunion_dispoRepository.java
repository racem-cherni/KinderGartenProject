package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entities.Rdv_dispo;
import tn.esprit.spring.entities.Reunion_dispo;

public interface Reunion_dispoRepository extends CrudRepository<Reunion_dispo, Integer> {

}
