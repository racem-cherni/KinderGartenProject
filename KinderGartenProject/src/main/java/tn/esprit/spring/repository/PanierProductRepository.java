package tn.esprit.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.PanierProduct;



public interface PanierProductRepository extends CrudRepository <PanierProduct, Integer>{
	
	@Query("SELECT o.offer FROM PanierProduct o WHERE o.panier.id= :id")
	List<Offer> getOffersByPanier (@Param("id") int id);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM PanierProduct WHERE panier.id =:panier AND offer.id=:offer")
	void deleteOfferFromPanier(@Param("panier") int panier_id, @Param("offer") int offer_id);
	
	@Query("SELECT o FROM PanierProduct o WHERE panier.id =:panier AND offer.id=:offer")
	PanierProduct getProductPanierByOfferAndPanier(@Param("offer") int offer, @Param("panier") int panier);
	
	@Query("SELECT o FROM PanierProduct o WHERE panier.id =:panier")
	List<PanierProduct> getAllProductsByPanier(@Param("panier") int panier_id);
	
	@Query(nativeQuery = true, value="SELECT Sum(p.qty) FROM `panierproduct` p JOIN offers o ON o.id = p.id_offer WHERE  p.state = 'DISPATCHED' AND o.kindergarten_id = :id")
	Integer getSalesCount(@Param("id") long id);
	
	@Query(nativeQuery = true, value="SELECT SUM(o.price * p.qty) FROM `panierproduct` p JOIN offers o ON o.id = p.id_offer WHERE  p.state = 'DISPATCHED' AND o.kindergarten_id = :id")
	Double getSalesTotalPrice(@Param("id") long id);
	
	@Query(nativeQuery = true, value="SELECT p.id_offer, p.id_panier FROM orders o JOIN panierproduct p ON p.id_panier = o.panier_id LEFT JOIN panier_session ps ON ps.panier_id = p.id_panier WHERE o.user_id = :user AND ps.panier_id IS NULL ORDER BY o.order_date DESC")
	List<Object[]> getAllBoughtProductsByUser(@Param("user") Long user);
	
	@Query(nativeQuery = true, value="SELECT count(*) FROM panierproduct pp JOIN offers o ON o.id = pp.id_offer JOIN orders orr ON orr.panier_id = pp.id_panier WHERE o.product_id = :product and orr.user_id = :user AND pp.state = 'DISPATCHED'")
	Integer getCountBought(@Param("user") Long user, @Param("product") int product);
	
	@Query(nativeQuery = true, value="SELECT o.id FROM panierproduct pp JOIN offers o ON o.id = pp.id_offer JOIN orders orr ON orr.panier_id = pp.id_panier WHERE o.product_id = :product and orr.user_id = :user AND pp.state = 'DISPATCHED'")
	Integer getBoughtOffer(@Param("user") Long user, @Param("product") int product);
}
