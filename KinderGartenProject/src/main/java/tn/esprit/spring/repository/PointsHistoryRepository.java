package tn.esprit.spring.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.PointsHistory;


public interface PointsHistoryRepository extends CrudRepository <PointsHistory,Integer>{
	

	@Query("SELECT SUM(p.amount) FROM PointsHistory p"
		+ " WHERE p.user.id =:userid")
	public Integer getPointsUser(@Param("userid") long userid);
	
}
