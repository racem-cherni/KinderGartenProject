package tn.esprit.spring.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Offer;

public interface OfferRepository extends CrudRepository <Offer,Integer>{
	
	@Query("SELECT count(*) FROM Offer o"
		 + " WHERE o.user.id =:userid AND o.product.id=:productid")
    public int CheckProduct(@Param("userid") long userid, @Param("productid")int productid);
	
	@Query("SELECT o FROM Offer o WHERE o.user.id =:userid AND o.product.id =:productid")
    public Offer getExistedOffer(@Param("userid") long userid, @Param("productid") int productid);
	
}
