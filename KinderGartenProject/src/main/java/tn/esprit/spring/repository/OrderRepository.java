package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entities.Order;

public interface OrderRepository extends CrudRepository <Order,Integer>{

}
