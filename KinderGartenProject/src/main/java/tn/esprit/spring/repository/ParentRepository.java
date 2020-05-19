package tn.esprit.spring.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.entities.Parent;


public interface ParentRepository extends JpaRepository<Parent,Long> {

}
