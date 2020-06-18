package tn.esprit.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Rdv_reponse;

public interface Rdv_reponseRepository extends CrudRepository<Rdv_reponse, Integer>{
	@Query("Select "
			+ "rdvr from Rdv_reponse rdvr  "
		
			+ "where rdvr.rdv=:rdvv")
    public Rdv_reponse findByRdv(@Param("rdvv") Rdv rdvv);

	@Query("Select "
			+ "rdvr from Rdv_reponse rdvr  "
		
			+ "where rdvr.rdv=:rdvv")
	public List<Rdv_reponse> getReponseByRdv(@Param("rdvv") Rdv rdvv);

}
