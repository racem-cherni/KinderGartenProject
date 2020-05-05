package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Panier;

public interface PanierService {
	
	Panier addPanier(Panier p);
	void deletePanier(int id);
	List<Panier> retrieveAllPaniers();

}
