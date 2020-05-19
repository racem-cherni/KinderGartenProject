package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;

public interface ChildRepository extends JpaRepository<Child, Long>{
@Query("select c.parents from Child c where c.kindergarten= :kinder")
public List<Parent> findParentKidsKindergarten(@Param("kinder") KinderGarten Kinder );
@Query("select c.parents from Child c where c.id= :id")
public Parent findParentKidKindergarten(@Param("id") Long id );

@Query("select c from Child c where c.parents= :parent")
public List<Child> findchildByparent(@Param("parent") Parent parent );


@Query("select c from Child c where c.classe= :classe")
public List<Child> findchildByClasse(@Param("classe") Classe classe );

@Query("select c from Child c where c.kindergarten= :kinder")
public List<Child> findchildByKinder(@Param("kinder") KinderGarten kinder );

@Query("select c.classe from Child c where c.parents= :parent")
public List<Classe> findchildClasseByparent(@Param("parent") Parent parent );

@Query("select c.parents from Child c where c.classe= :classe ")
public List<Parent> findchildParentByClasse(@Param("classe") Classe classe);
}
