package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Product;

public interface ProductRepository extends CrudRepository <Product,Integer>{
	
	@Query("SELECT count(*) FROM Product")
	public int gettotalproducts();
	
	@Query(nativeQuery = true, value = "SELECT a2.pid FROM ( SELECT of.product_id as pid, count(*) FROM orders o JOIN offers of ON of.id = o.offer_id GROUP BY(of.product_id) ORDER BY count(*) LIMIT 1) a2")
	public int getBestSellerById();
	
	@Query(nativeQuery = true, value = "SELECT MIN(price) FROM offers where product_id = :id")
	public double getMinPrice(@Param("id") int id);
	
	@Query("SELECT o.product FROM Offer o GROUP BY o.product")
	public List<Product> getProductsOnSale();
	
	@Query(nativeQuery = true, value = "SELECT a.id "
			+ "FROM ( SELECT of.product_id id, count(*) "
					+ "from orders o "
					+ "JOIN paniers p "
					+ "on p.id = o.panier_id "
					+ "JOIN panierproduct pp "
					+ "on pp.id_panier = p.id "
					+ "JOIN offers of "
					+ "ON of.id = pp.id_offer "
					+ "where o.state = 'DISPATCHED' and YEAR(o.order_date) > 2010 "
							+ "group by of.product_id "
							+ "order by count(*) DESC "
							+ "limit :limit) a")
	public List<Integer> getBestSeller(@Param("limit") int limit); 
	
	@Query(nativeQuery = true, value ="SELECT count(*) from orders o JOIN paniers p  on p.id = o.panier_id JOIN panierproduct pp on pp.id_panier = p.id JOIN offers of ON of.id = pp.id_offer  where o.state = 'DISPATCHED' or o.state = 'CONFIRMED' and of.product_id = :id")
	public int getSoldNumber(@Param("id") int id);
	
	@Query(nativeQuery = true, value = "SELECT count(*) from offers	WHERE product_id = :id")
	public int getOffersNumber(@Param("id") int id); // number of offers / product
	
	@Query(nativeQuery = true, value = "SELECT AVG(`rating`) FROM `sale_ratings_history` s JOIN offers o ON o.id = s.offerid WHERE o.product_id = :id GROUP BY o.product_id ")
	public double getProductRating (@Param("id") int id); ///average rating
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%',:string,'%')")
	public List<Product> searchProducts(@Param("string") String msg);
	
		//############RECOMMANDATION###############//
	
	@Query(nativeQuery = true, value = "SELECT a.userid from ( SELECT s.userid, count(o.product_id) FROM `sale_ratings_history` s JOIN offers o ON o.id = s.offerid WHERE s.userid <> :userid group by s.userid order by count(o.product_id) desc LIMIT :limit ) a ORDER BY a.userid")
    public List<Integer> getTopUsers(@Param("userid") Long id, @Param("limit") int limit);
	
	@Query(nativeQuery = true, value = "SELECT s.userid, s.rating FROM sale_ratings_history s JOIN offers o ON o.id = s.offerid WHERE o.product_id =:id")
	public List<Object[]> getProductVector(@Param ("id") int id);
	
	@Query(nativeQuery = true, value = "SELECT o.product_id from sale_ratings_history s JOIN offers o ON o.id = s.offerid  where o.product_id <> 20 GROUP BY 1 LIMIT :limit")
	public List<Integer> getAllRatedProducts(@Param ("limit") int limit);
	
	@Query(nativeQuery = true, value = "SELECT o.product_id, s.rating FROM sale_ratings_history s JOIN offers o ON o.id = s.offerid WHERE s.userid = :id")
	public List<Integer> getRatedProductsByUser(@Param("id") Long id);
	
	@Query(nativeQuery = true, value = "SELECT o.product_id, s.rating from sale_ratings_history s JOIN offers o ON o.id = s.offerid WHERE s.userid = :id")
	public List<Object[]> getUserVector(@Param ("id") Long id);
	
}
