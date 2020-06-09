package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import tn.esprit.spring.entities.foodsandtheircallories;
@Repository
public interface foodsandtheircalloriesRepository extends JpaRepository<foodsandtheircallories, Long> {

	@Query("select e from foodsandtheircallories e "
			+ "join e.KinderGarten t "
			+ "where t.id=:idkinder")
	public List <foodsandtheircallories> foodbyidkinder(@Param("idkinder")long idkinder);
	
	
	


  }
