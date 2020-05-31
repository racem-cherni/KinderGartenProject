package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
@Query("select sum(v.note) from Vote v where v.sourceUser= :kinder")
public Integer getSumNote(@Param("kinder") KinderGarten kinder);



@Query("select count(v) from Vote v where v.sourceUser= :kinder")
public Integer getNum(@Param("kinder") KinderGarten kinder);
}
