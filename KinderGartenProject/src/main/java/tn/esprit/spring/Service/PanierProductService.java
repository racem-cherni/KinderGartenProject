package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Panier;
import tn.esprit.spring.entities.PanierProduct;

public interface PanierProductService {
	
	PanierProduct addProductToPanier(int panierid, int offerid, int qty, Long refuser);
	void removeProductFromPanier(int id);
	List<Offer> retrieveAlOffdersOfPanier(int id);

}
