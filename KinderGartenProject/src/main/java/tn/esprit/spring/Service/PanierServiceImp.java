package tn.esprit.spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Panier;
import tn.esprit.spring.entities.PanierSession;
import tn.esprit.spring.entities.SessionFake;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.PanierRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.PanierSessionRepository;

@Service
public class PanierServiceImp implements PanierService{
	
	@Autowired
	private PanierRepository PanierRepository;
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private PanierSessionRepository PanierSessionRepository;
	
	@Autowired
	private UserServices userServices;

	@Override
	public Panier addPanier(Panier p) {
		UserApp user = new UserApp();
		user = UserRepository.findById(userServices.currentUserJsf().getId()).orElse(null);
		PanierSession panier_session = new PanierSession(user, p);
		Panier panier = PanierRepository.save(p);
		PanierSessionRepository.save(panier_session);
		return panier;
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
