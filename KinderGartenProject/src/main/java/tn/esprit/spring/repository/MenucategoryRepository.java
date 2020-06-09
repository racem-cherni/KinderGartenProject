package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.entities.menucategory;

public interface MenucategoryRepository extends JpaRepository<menucategory, Long> {

}
