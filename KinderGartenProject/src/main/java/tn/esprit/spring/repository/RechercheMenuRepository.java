package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.RechercheMenu;
import tn.esprit.spring.entities.UserApp;

public interface RechercheMenuRepository extends JpaRepository<RechercheMenu, Long>{
	@Query("select r from RechercheMenu r where r.userapp= :user")
	public List<RechercheMenu> findByUser(@Param("user") UserApp user);

}
