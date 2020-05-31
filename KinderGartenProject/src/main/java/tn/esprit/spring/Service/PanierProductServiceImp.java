package tn.esprit.spring.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Panier;
import tn.esprit.spring.entities.PanierProduct;
import tn.esprit.spring.entities.PanierProductPK;
import tn.esprit.spring.entities.PanierProductState;
import tn.esprit.spring.entities.PanierSession;
import tn.esprit.spring.entities.SessionFake;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.PanierProductRepository;
import tn.esprit.spring.repository.PanierRepository;
import tn.esprit.spring.repository.OrderRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.PanierSessionRepository;


@Service
public class PanierProductServiceImp implements PanierProductService {

	@Autowired
	private PanierProductRepository PanierProductRepository;

	@Autowired
	private PanierRepository PanierRepository;

	@Autowired
	private OrderRepository OfferRepository;

	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private PanierSessionRepository PanierSessionRepository;

	@Autowired
	private PanierService PanierService;

	@Override
	public PanierProduct addProductToPanier(Offer offer, int qty, Long refuser) {

		UserApp user = new UserApp();
		PanierSession session = new PanierSession();
		Panier panier;

		user = UserRepository.findById(refuser).orElse(null);
		session = PanierSessionRepository.getPanierSessionByUser(SessionFake.getId());

		if (session == null)
			panier = PanierService.addPanier(new Panier());
		else
			panier = session.getPanier();

		PanierProductPK prk = new PanierProductPK(panier.getId(), offer.getId());
		PanierProduct pr = new PanierProduct(prk, qty, offer, panier, user);
		pr.setState(PanierProductState.WAITING);
		return PanierProductRepository.save(pr);
	}

	@Override
	public void removeProductFromPanier(int id) {

		PanierSession session = new PanierSession();
		Panier panier;

		session = PanierSessionRepository.getPanierSessionByUser(SessionFake.getId());

		if (session == null)
			panier = PanierService.addPanier(new Panier());
		else
			panier = session.getPanier();

		PanierProductRepository.deleteOfferFromPanier(panier.getId(), id);
	}

	@Override
	public List<Offer> retrieveAlOffdersOfPanier() {

		long user_id = SessionFake.getId();
		PanierSession panier_session = PanierSessionRepository.getPanierSessionByUser(user_id);

		if (panier_session != null)
			return PanierProductRepository.getOffersByPanier(panier_session.getPanier().getId());

		return null;
	}

}
