package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Parent;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.esprit.spring.entities.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {
	@Query("Select "
			+ "p from Parent p  "
			+ "where  p.Email=:parent_email ")
	public Parent findByIdEmail(@Param("parent_email") String parent_email);
}
