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
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.PanierRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.PanierProductRepository;
import tn.esprit.spring.repository.OfferRepository;

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

	@Override
	public PanierProduct addProductToPanier(int panierid, int offerid, int qty, Long refuser) {

		Offer o = OfferRepository.findById(offerid).orElse(null);
		Panier p = PanierRepository.findById(panierid).orElse(null);
		
		UserApp u = new UserApp();
		
		u = UserRepository.findById(refuser).orElse(null);
		
		if (o == null)
			throw new RuntimeException("The offer dosen't exist!");

		if (o.getQty() >= qty) {

			if (p == null) {

				p = new Panier();

				p.setDate(new Date());
				p.setId(panierid);
				PanierRepository.save(p);

			}

			p = PanierRepository.findById(panierid).orElse(null);
			PanierProductPK prk = new PanierProductPK(panierid, offerid);
			PanierProduct pr = new PanierProduct(prk, qty, o, p, u);
			return PanierProductRepository.save(pr);
		}

		throw new RuntimeException("The product's quantity is inferior to the one you are trying to order!");
	}

	@Override
	public void removeProductFromPanier(int id) {

	}

	@Override
	public List<Offer> retrieveAlOffdersOfPanier(int id) {

		return null;
	}

}
