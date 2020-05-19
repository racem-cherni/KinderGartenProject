package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Teacher;

public interface ClasseRepository extends JpaRepository<Classe, Long>{
@Query("select c from Classe c where c.teacher= :teacher")
public Classe findByTeacher(@Param("teacher") Teacher teacher);



@Query("select c.teacher from Classe c where c.id= :id")
public Teacher findTeacher(@Param("id") Long id);


@Query("select c from Classe c where c.id= :id")
public Classe findByid(@Param("id") Long id);



@Query("select c from Classe c where c.kinderGarten= :kinder and c.age= :age ")
public List<Classe> findclasseForkid(@Param("kinder") KinderGarten kinder,@Param("age") int age);



@Query("select c from Classe c where c.kinderGarten= :kinder")
public List<Classe> findclasseByKinder(@Param("kinder") KinderGarten kinder);

}
