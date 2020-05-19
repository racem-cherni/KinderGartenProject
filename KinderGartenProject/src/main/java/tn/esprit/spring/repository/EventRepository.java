package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
