package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.PanierProduct;


public interface PanierProductService {
	
	PanierProduct addProductToPanier(Offer offer, int qty, Long refuser);
	void removeProductFromPanier(int id);
	List<Offer> retrieveAlOffdersOfPanier();
	PanierProduct getProductPanierByOfferAndPanier(int offer, int panier);
	PanierProduct updateProduct(PanierProduct panier_product);
	void cancelAllProductsByPanier(int panier_id);
	int getSalesCount(long id);
	double getSalesTotalPrice(long id);
	List<PanierProduct> getAllBoughtProductsByUser(Long user);

}
