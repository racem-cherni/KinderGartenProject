package tn.esprit.spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Panier;
import tn.esprit.spring.repository.PanierRepository;

@Service
public class PanierServiceImp implements PanierService{
	
	@Autowired
	private PanierRepository PanierRepository;

	@Override
	public Panier addPanier(Panier p) {
		return PanierRepository.save(p);
	}

	@Override
	public void deletePanier(int id) {
		PanierRepository.deleteById(id);
		
	}

	@Override
	public List<Panier> retrieveAllPaniers() {

		return (List<Panier>) PanierRepository.findAll();
	}
	

}
