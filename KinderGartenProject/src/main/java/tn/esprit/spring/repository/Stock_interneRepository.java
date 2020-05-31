package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Stock_interne;

@Repository
public interface Stock_interneRepository extends CrudRepository<Stock_interne,Long> {

}
