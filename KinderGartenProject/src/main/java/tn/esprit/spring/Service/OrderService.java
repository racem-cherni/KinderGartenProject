package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Order;


public interface OrderService {
	
	//////////////CRUD////////////////
	List<Order> retrieveAllOrders();
	Order addOrder(Order p, int panierid, Long userid);
	void deleteOrder(int id);
	Order updateOrder(Order u);
	Order retrieveOrder(int id);
	
	Order cancelOrder(int id);
	Order refuseOrder(int id);
	
	Order confirmOrder(int id);
	Order dispatchOrder(int id);
	///////////////////////////////
	
	int getTotalOrders();
	//void deleteOrderByRef(int id);
	
}