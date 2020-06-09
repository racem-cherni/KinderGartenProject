package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.entities.callories;

public interface CalloriesRepository extends JpaRepository<callories, Long> {

}
