package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Competence;

public interface CompetenceRepository extends JpaRepository<Competence, Long>{
@Query("select count(c) from Competence c where c.id= :id")
public int NbrCompParid(@Param("id") Long id);
@Query("select c from Competence c where c.competenceName= :competenceName")
public Competence findByNom(@Param("competenceName")String competenceName);


@Query("select c from Competence c where c.competenceName= :competenceName")
public Competence findByNo(@Param("competenceName")String competenceName);




}
