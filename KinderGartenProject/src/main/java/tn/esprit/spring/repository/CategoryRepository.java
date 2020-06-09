package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.entities.categories;

public interface CategoryRepository extends JpaRepository<categories, Long> {

}
