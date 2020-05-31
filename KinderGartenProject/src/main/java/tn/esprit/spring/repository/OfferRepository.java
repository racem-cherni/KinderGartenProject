package tn.esprit.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Offer;

public interface OfferRepository extends CrudRepository <Offer,Integer>{
	
	@Query("SELECT count(*) FROM Offer o"
		 + " WHERE o.Kindergarten.user.id =:userid AND o.product.id=:productid")
    public int CheckProduct(@Param("userid") long userid, @Param("productid")int productid);
	
	@Query("SELECT count(*) FROM Offer o"
			 + " WHERE o.Kindergarten.user.id =:userid")
	public int getOfferCount(@Param("userid") long userid);
	
	@Query("SELECT o FROM Offer o WHERE o.Kindergarten.user.id =:userid AND o.product.id =:productid")
    public Offer getExistedOffer(@Param("userid") long userid, @Param("productid") int productid);
	
	@Query("SELECT o FROM Offer o WHERE o.Kindergarten.user.id =:id GROUP BY o.product")
	public List<Offer> getOffersByKindergarten(@Param ("id") Long id);
	
	@Query("SELECT o FROM Offer o WHERE o.product.id = :id GROUP BY o.id")
	public List<Offer> getOffersByProduct(@Param("id") int id);
	
}
