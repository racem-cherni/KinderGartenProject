package tn.esprit.spring.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import tn.esprit.spring.Service.OfferService;
import tn.esprit.spring.Service.ProductService;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Product;
import tn.esprit.spring.entities.SessionFake;


@Scope(value="session")
@Controller(value = "marketController")
@ELBeanName(value = "marketController")
@Join(path = "/market", to = "/pages/parent/marketplace/market.jsf")
public class MarketProfileController {

	@Autowired
	private ProductService productservice;

	@Autowired
	private OfferService offerservice;
	
	private int id;
	
	private boolean rendered = false;
	
	private Product product;
	
	private List<Offer> offers;
	
	private List<Product> products;
	
	private Offer offer;
	
	private String price;
	
	private String qty;
	
	private String count;
	
	private String search="";
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getCount(){
		
		return ""+offerservice.getOfferCount(SessionFake.getId());
	}
	
	public String getPrice() {
		return ""+offer.getPrice();
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQty() {
		return ""+offer.getQty();
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered() {
		rendered = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Offer> getOffers() {
		if(SessionFake.getId() != null)
			this.offers = offerservice.getOffersByKindergarten(SessionFake.getId());
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public List<Product> getProducts() {

	    Long id = SessionFake.getId();
		
		List<Offer> off = this.getOffers();
		List<Product> pro = new ArrayList<Product>();

		for (Offer o : off)
			if (o.getProduct().getName().toLowerCase().contains(this.search.toLowerCase()))
				pro.add(o.getProduct());

		return pro;
	}

	public void setProducts(List<Product> products) {
		this.products = products;

	}

	public double getPrice(int id) {
		return productservice.retrieveMinPrice(id);
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {

		this.product = product;
		offer = offerservice.getSelectedOffer(SessionFake.getId(), product.getId());
		rendered = true;
	}
	
	public void debug(){
		
		System.out.println(this.id);
	}
	
	public void updateOffer(){
		
		System.out.println(price);
		
		if (offer != null) {
			
			
			offer.setPrice(Double.valueOf(price));
			offer.setQty(Integer.parseInt(qty));

		}
			
		
		rendered = false;
		offer = null;
		
	}
	
	public void remove(){
		
		offerservice.deleteOffer(offer.getId());
		
		rendered = false;
		offer = null;
		
	}
	
	
}
