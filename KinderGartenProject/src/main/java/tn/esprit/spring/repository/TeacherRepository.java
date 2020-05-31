package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
@Query("select t from Teacher t where t.kinderGarten= :kinder")
public List<Teacher> findByKinder(@Param("kinder") KinderGarten kinder);




}
