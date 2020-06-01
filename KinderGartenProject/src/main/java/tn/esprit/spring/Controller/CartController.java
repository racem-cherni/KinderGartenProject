package tn.esprit.spring.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.OfferService;
import tn.esprit.spring.Service.PanierProductService;
import tn.esprit.spring.entities.Offer;


@Scope(value = "session")
@Controller(value = "cartController")
@ELBeanName(value = "cartController")
@Join(path = "/cart", to = "/pages/parent/marketplace/cart.jsf")
public class CartController {
	
	@Autowired
	PanierProductService PanierProductService;
	
	@Autowired
	private OfferService offerservice;
	
	private List<Offer> offers;
	
	private Double total_price;
	
	private Map<Integer, Integer> offer_qty = new HashMap<Integer, Integer>();
	
	private Map<Integer, Double> offer_price = new HashMap<Integer, Double>();
	
	private String qty ;
	
	
	
	public Map<Integer, Double> getOffer_price() {
		return offer_price;
	}

	public void setOffer_price(Map<Integer, Double> offer_price) {
		this.offer_price = offer_price;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}
	
	public String getQty() {
		return qty;
	}

	
	public Map<Integer, Integer> getOffer_qty() {
		return offer_qty;
	}

	public void setOffer_qty(Map<Integer, Integer> offer_qty) {
		this.offer_qty = offer_qty;
	}

	public void calculatePrice(){
		
total_price++;
		System.out.println(total_price);

	}
	
	public void onload(){
		
		this.offers = Collections.emptyList();
		List<Offer> offers_temp = PanierProductService.retrieveAlOffdersOfPanier();
		for(Offer offer : offers_temp){
			this.offer_qty.put(offer.getId(), 1);
			this.offer_price.put(offer.getId(), offer.getPrice());
		}
		
		if (offers_temp != null)
			this.offers = offers_temp ;
		
		double total_price = 0;
		
		for(Map.Entry<Integer, Integer> offer : this.offer_qty.entrySet()){
			total_price += offer.getValue() * (this.getOffer_price().get(offer.getKey()));
		}
		
		this.total_price = total_price;

	}
	
	private String ref= "0";
	
	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void addToCart(Offer offer){
		PanierProductService.addProductToPanier(offer, 1, (long) Integer.parseInt(ref));
	}

	public List<Offer> getOffers() {
		return PanierProductService.retrieveAlOffdersOfPanier();
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
	
	public void deleteOffer(int id){
		
		PanierProductService.removeProductFromPanier(id);
		this.offers = PanierProductService.retrieveAlOffdersOfPanier();
		System.out.println(this.offers);
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}
	
	public int getQuantity(int offer_id){
		return this.offer_qty.get(offer_id);
	}
	
	
		
}
