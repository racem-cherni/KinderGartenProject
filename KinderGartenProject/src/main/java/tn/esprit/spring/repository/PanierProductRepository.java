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
	PanierProduct  getProductPanierByOfferAndPanier(@Param("offer") int offer, @Param("panier") int panier);
	
	@Query("SELECT o FROM PanierProduct o WHERE panier.id =:panier")
	List<PanierProduct> getAllProductsByPanier(@Param("panier") int panier_id);
}
