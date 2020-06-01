package tn.esprit.spring.Controller;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.OfferService;
import tn.esprit.spring.entities.Offer;


@Scope(value = "session")
@Controller(value = "offerController")
@ELBeanName(value = "offerController")
@Join(path = "/offer", to = "/pages/parent/marketplace/offer.jsf")
public class OfferController {

	@Autowired
	private OfferService offerservice;
	
	private String id;
	
	private Offer offer;
	
	private String price;
	
	private String qty;
	
	public void onload() {
		
		this.setOffer(offerservice.retrieveOffer(Integer.parseInt(id)));
		
		this.setPrice(""+this.getOffer().getPrice());
		this.setQty(""+this.getOffer().getQty());

	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	public void updateOffer(){
		
		System.out.println(price);
		System.out.println(qty);
		
		offer.setPrice(Double.valueOf(price));
		offer.setQty(Integer.parseInt(qty));
		
		offerservice.updateOffer(offer);
	}
	
	

		
	

}
