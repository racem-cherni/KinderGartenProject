package tn.esprit.spring.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Named;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.OfferService;
import tn.esprit.spring.Service.OrderService;
import tn.esprit.spring.Service.PanierProductService;
import tn.esprit.spring.Service.PanierService;
import tn.esprit.spring.Service.PointsHistoryService;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Order;
import tn.esprit.spring.entities.PanierProduct;
import tn.esprit.spring.entities.SessionFake;
import tn.esprit.spring.repository.PanierSessionRepository;

@Scope(value = "session")
@Controller(value = "cartController")
@ELBeanName(value = "cartController")
@Join(path = "/cart", to = "/pages/parent/marketplace/cart.jsf")
public class CartController {

	@Autowired
	PanierProductService PanierProductService;

	@Autowired
	PointsHistoryService PointsHistoryService;

	@Autowired
	private OfferService offerservice;

	@Autowired
	private OrderService orderservice;

	@Autowired
	private PanierService panierservice;

	@Autowired
	private PanierSessionRepository PanierSessionRepository;

	private List<Offer> offers;

	private Double total_price;

	private Map<Integer, Integer> offer_qty = new HashMap<Integer, Integer>();

	private Map<Integer, Double> offer_price = new HashMap<Integer, Double>();

	private String qty;

	private int total_points;

	private String points;

	private String points2;

	private String reduced_price;

	public String getReduced_price() {
		return reduced_price;
	}

	public void setReduced_price(String reduced_price) {
		this.reduced_price = reduced_price;
	}

	public String getPoints2() {
		return points2;
	}

	public void setPoints2(String points2) {
		this.points2 = points2;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public int getTotal_points() {
		return total_points;
	}

	public void setTotal_points(int total_points) {
		this.total_points = total_points;
	}

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

	public void calculatePrice(int offer_id) {

		int qty = Integer.parseInt(this.qty);
		int old_qty = this.offer_qty.get(offer_id);

		Double price = this.offer_price.get(offer_id);

		if (qty > old_qty)
			this.total_price += (qty - old_qty) * price;
		else
			this.total_price -= (old_qty - qty) * price;

		this.offer_qty.replace(offer_id, qty);

		this.reduced_price = "" + (this.total_price - Double.parseDouble(this.points));

	}

	public void onload() {

		this.offers = Collections.emptyList();
		this.offer_qty = new HashMap<Integer, Integer>();
		this.offer_price = new HashMap<Integer, Double>();
		List<Offer> offers_temp = PanierProductService.retrieveAlOffdersOfPanier();

		if (offers_temp != null) {
			for (Offer offer : offers_temp) {
				this.offer_qty.put(offer.getId(), 1);
				this.offer_price.put(offer.getId(), offer.getPrice());
			}
			this.offers = offers_temp;

		}

		double total_price = 0;

		for (Map.Entry<Integer, Integer> offer : this.offer_qty.entrySet()) {
			total_price += offer.getValue() * (this.getOffer_price().get(offer.getKey()));
		}

		this.total_price = total_price;

		this.qty = "1";

		this.points = "0.0";
		this.points2 = "0";

		this.reduced_price = "" + this.total_price;

	}

	private String ref = "0";

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public void addToCart(Offer offer) {
		PanierProductService.addProductToPanier(offer, 1, (long) Integer.parseInt(ref));
	}

	public List<Offer> getOffers() {
		return PanierProductService.retrieveAlOffdersOfPanier();
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public void deleteOffer(int id) {

		PanierProductService.removeProductFromPanier(id);
		this.offers = PanierProductService.retrieveAlOffdersOfPanier();

		this.offer_price.remove(id);
		this.offer_qty.remove(id);

		double total_price = 0;

		for (Map.Entry<Integer, Integer> offer : this.offer_qty.entrySet()) {
			total_price += offer.getValue() * (this.getOffer_price().get(offer.getKey()));
		}

		this.total_price = total_price;

		this.points = "0.0";
		this.points2 = "0.0";

		this.updatePrice();

	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public int getQuantity(int offer_id) {
		return this.offer_qty.get(offer_id);
	}

	public void placeOrder() {

		Order order = new Order();
		int panier_id;

		if (PanierSessionRepository.getPanierSessionByUser(SessionFake.getId()) != null)
			panier_id = PanierSessionRepository.getPanierSessionByUser(SessionFake.getId()).getPanier().getId();

		for (Map.Entry<Integer, Integer> offer : this.offer_qty.entrySet()) {
			PanierProduct panier_product = PanierProductService.getProductPanierByOfferAndPanier(offer.getKey(),
					panier_id);
			panier_product.setQty(offer.getValue());
			PanierProductService.updateProduct(panier_product);
		}

		order.setPointspent(Double.parseDouble(this.points)*50);
		
		if (Double.parseDouble(this.points)>0)
			order.setReducedprice(Double.parseDouble(this.reduced_price));
		else
			order.setReducedprice(this.total_price);

		orderservice.addOrder(order, panier_id, SessionFake.getId());

	}

	public String getTotalPoints() {
		this.total_points = PointsHistoryService.getPointsUser(SessionFake.getId());
		return "" + this.total_points;
	}

	public String getEquivalentPrice() {
		return "" + ((double) this.total_points / 50);
	}

	public void manipulatePoints() {

		if (((double) this.total_points / 50) > this.total_price) {
			if (Double.parseDouble(this.points) > this.total_price)
				this.points = "" + this.total_price;

		} else {
			if (Double.parseDouble(this.points) > ((double) this.total_points / 50)) {
				this.points = "" + (double) this.total_points / 50;
			}
		}

		this.points2 = "" + (int) Math.ceil(Double.parseDouble(this.points) * 0.05 * 1000);

	}

	public void updatePrice() {
		this.reduced_price = "" + (this.total_price - Double.parseDouble(this.points));
	}
}