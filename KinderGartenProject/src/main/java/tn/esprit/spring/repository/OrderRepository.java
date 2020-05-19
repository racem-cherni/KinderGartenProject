package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entities.Order;


public interface OrderRepository extends CrudRepository <Order,Integer>{
	
	@Query("SELECT o FROM Order o")
	public List<Order> getDispatchedOrders();

}
