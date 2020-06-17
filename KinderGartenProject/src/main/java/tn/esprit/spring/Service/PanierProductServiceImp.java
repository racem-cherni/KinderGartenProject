package tn.esprit.spring.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Panier;
import tn.esprit.spring.entities.PanierProduct;
import tn.esprit.spring.entities.PanierProductPK;
import tn.esprit.spring.entities.PanierProductState;
import tn.esprit.spring.entities.PanierSession;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.*;

@Service
public class PanierProductServiceImp implements PanierProductService {

	@Autowired
	private PanierProductRepository PanierProductRepository;

	@Autowired
	private PanierRepository PanierRepository;

	@Autowired
	private OfferRepository OfferRepository;

	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private PanierSessionRepository PanierSessionRepository;

	@Autowired
	private PanierService PanierService;
	
	@Autowired
	private UserServices userServices;
	
	@Autowired
	private KinderGartenRepository KindergartenRepository;

	@Override
	public PanierProduct addProductToPanier(Offer offer, int qty, Long refuser) {

		UserApp user = new UserApp();
		PanierSession session = new PanierSession();
		Panier panier;
		
		if (offer.getKindergarten().getUserapp().getId() == userServices.currentUserJsf().getId())
			return new PanierProduct();

		user = UserRepository.findById(refuser).orElse(null);
		session = PanierSessionRepository.getPanierSessionByUser(userServices.currentUserJsf().getId());

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

		session = PanierSessionRepository.getPanierSessionByUser(userServices.currentUserJsf().getId());

		if (session == null)
			panier = PanierService.addPanier(new Panier());
		else
			panier = session.getPanier();

		PanierProductRepository.deleteOfferFromPanier(panier.getId(), id);
	}

	@Override
	public List<Offer> retrieveAlOffdersOfPanier() {

		long user_id = userServices.currentUserJsf().getId();
		PanierSession panier_session = PanierSessionRepository.getPanierSessionByUser(user_id);

		if (panier_session != null)
			return PanierProductRepository.getOffersByPanier(panier_session.getPanier().getId());

		return null;
	}

	@Override
	public PanierProduct getProductPanierByOfferAndPanier(int offer, int panier) {
		
		return PanierProductRepository.getProductPanierByOfferAndPanier(offer, panier);
	}

	@Override
	public PanierProduct updateProduct(PanierProduct panier_product) {
		
		return PanierProductRepository.save(panier_product);
	}

	@Override
	public void cancelAllProductsByPanier(int panier_id) {
		
		List<PanierProduct> panier_products = PanierProductRepository.getAllProductsByPanier(panier_id);
		
		for (PanierProduct panier_product : panier_products){
			panier_product.setState(PanierProductState.CANCELED);
			PanierProductRepository.save(panier_product);
		}
		
	}
	
	@Override
	public int getSalesCount(long id) {
		
		KinderGarten k = KindergartenRepository.getKinder(userServices.currentUserJsf().getId());
		
		try {
				return PanierProductRepository.getSalesCount(k.getId());
			}
			catch(Exception e) {
			 	return 0;
			}
		
	}

	@Override
	public double getSalesTotalPrice(long id) {
		
		
		KinderGarten k = KindergartenRepository.getKinder(userServices.currentUserJsf().getId());
		
		try {
			return PanierProductRepository.getSalesTotalPrice(k.getId());
			}
			catch(Exception e) {
			  return 0.0;
			}
		
		
	}
	
	@Override
	public List<PanierProduct> getAllBoughtProductsByUser(Long user) {
		
		List<PanierProduct> products = new ArrayList<PanierProduct>();
		
		List<Object[]> products_objs = PanierProductRepository.getAllBoughtProductsByUser(user);
		
		for (Object[] obj : products_objs)
			products.add(PanierProductRepository.getProductPanierByOfferAndPanier((int) obj[0], (int) obj[1]));
		
		return products;
	}


}
