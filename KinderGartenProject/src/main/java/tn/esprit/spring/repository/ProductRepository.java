package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entities.Product;

public interface ProductRepository extends CrudRepository <Product,Integer>{
	
	@Query("SELECT count(*) FROM Product")
	public int gettotalproducts();
	
	
	@Query(nativeQuery = true, value = "SELECT a2.pid FROM ( SELECT of.product_id as pid, count(*) FROM orders o JOIN offers of ON of.id = o.offer_id GROUP BY(of.product_id) ORDER BY count(*) LIMIT 1) a2")
	public int getBestSellerById();
	
}
