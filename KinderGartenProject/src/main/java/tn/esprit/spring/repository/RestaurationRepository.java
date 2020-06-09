package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.foodsandtheircallories;


@Repository
public interface RestaurationRepository extends JpaRepository<foodsandtheircallories,Long> {
	

}
