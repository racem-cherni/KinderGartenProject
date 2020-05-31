package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import tn.esprit.spring.entities.Stock_event;

@Repository
public interface StockEventRepository extends CrudRepository<Stock_event,Long> {
	@Query("Select s from Stock_event s where s.name=:stockname ORDER BY s.prix_stock ")
	List<Stock_event> liststockrecherche (@Param("stockname")String stockname);

}
