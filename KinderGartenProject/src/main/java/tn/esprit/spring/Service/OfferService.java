package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Offer;



public interface OfferService {
	
	//////////////CRUD////////////////
	List<Offer> retrieveAllOffers();
	Offer addOffer(Offer p);
	void deleteOffer(int id);
	Offer updateOffer(Offer u);
	Offer retrieveOffer(int id);
	List<Offer> getOffersByKindergarten(Long id);
	List<Offer> getOffersByProduct(int id);
	Offer getSelectedOffer(Long user, int id);
	int getOfferCount(Long user);
	///////////////////////////////
	
	Offer updateOfferMultiplicity(Offer u);

}
