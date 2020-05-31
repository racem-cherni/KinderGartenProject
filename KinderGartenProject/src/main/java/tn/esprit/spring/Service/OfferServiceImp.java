package tn.esprit.spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Product;
import tn.esprit.spring.repository.OfferRepository;

@Service
public class OfferServiceImp implements OfferService {

	@Autowired
	private OfferRepository OfferRepository;

	@Override
	public Offer addOffer(Offer p) {
		
		if (OfferRepository.CheckProduct((int) p.getKindergarten().getUserapp().getId(), p.getProduct().getId()) == 0)
			return OfferRepository.save(p);
		
		else {
			Offer o = OfferRepository.getExistedOffer( p.getKindergarten().getUserapp().getId(), p.getProduct().getId());
			o.setQty(o.getQty() + p.getQty());
			
			if (o.getPrice()>p.getPrice())
				o.setPrice(p.getPrice());

			return OfferRepository.save(o);
		}
	}

	@Override
	public void deleteOffer(int id) {

		OfferRepository.deleteById(id);
	}

	@Override
	public List<Offer> retrieveAllOffers() {

		List<Offer> products = (List<Offer>) OfferRepository.findAll();
		return products;
	}

	@Override
	public Offer updateOffer(Offer u) {
		
		return OfferRepository.save(u);
	}

	@Override
	public Offer retrieveOffer(int id) {
		Offer p = OfferRepository.findById((id)).orElse(null);
		return p;
	}

	@Override
	public Offer updateOfferMultiplicity(Offer u) {
		// TODO Auto-generated method stub
		return null;
	}
	

}