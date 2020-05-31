package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.PanierProduct;
import tn.esprit.spring.entities.PanierProductPK;


public interface PanierProductService {
	
	PanierProduct addProductToPanier(Offer offer, int qty, Long refuser);
	void removeProductFromPanier(int id);
	List<Offer> retrieveAlOffdersOfPanier();

}
