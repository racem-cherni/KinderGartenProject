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

import tn.esprit.spring.Service.OfferService;
import tn.esprit.spring.Service.ProductService;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Product;
import tn.esprit.spring.entities.SessionFake;



@Scope(value = "session")
@Controller(value = "productController")
@ELBeanName(value = "productController")
@Join(path = "/product", to = "/pages/parent/marketplace/product.jsf")
public class ProductController {

	@Autowired
	private ProductService productservice;

	@Autowired
	private OfferService offerservice;

	private double price;

	private String ref ="0";

	private boolean rendered = false;

	private String id;

	private String fulllink = new String(" ");

	private List<Product> products;

	private List<Offer> offers;

	private Product product;

	private String username;

	private int offernumber;

	public void onload() {
		System.out.println(ref);
		this.product = productservice.retrieveProduct(Integer.parseInt(id));

	}

	public boolean isRendered() {
		return rendered;
	}

	public List<Offer> getOffers() {
		return offerservice.getOffersByProduct(product.getId());
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Product getProductbyId(String id) {
		this.product = productservice.retrieveProduct(Integer.parseInt(id));
		return product;
	}

	public double getPrice(int id) {
		return productservice.retrieveMinPrice(id);
	}

	public List<Product> getRandom() {

		return productservice.getRandomProducts();
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Product> getBestSellers(int limit) {
		return productservice.getBestSellers(limit);
	}

	public int getSoldNumber(int id) {
		return productservice.getSoldNumber(id);
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getOffernumber(int id) {
		return productservice.getOfferNumbers(id);
	}

	public void setOffernumber(int offernumber) {
		this.offernumber = offernumber;
	}

	public int getProductRating(int productid) {
		return productservice.getProductRating(productid);
	}

	public List<Integer> fillStars(int productid) {

		List<Integer> a = new ArrayList<Integer>();

		for (int i = 1; i <= productservice.getProductRating(productid); i++) {
			a.add(1);
		}

		return a;
	}

	public List<Integer> unfillStars(int productid) {

		List<Integer> a = new ArrayList<Integer>();

		for (int i = 1; i <= 5 - productservice.getProductRating(productid); i++) {
			a.add(1);
		}

		return a;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String goProductPage(Product product) {

		this.setId("" + product.getId());

		return "/product";

	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getFulllink() {
		return fulllink;
	}

	public void setFulllink() {

		if (SessionFake.getId() == null)
			this.fulllink = "localhost:8080/SpringMVC/product?id=" + this.id + "&ref=" + 0;
		else
			this.fulllink = "localhost:8080/SpringMVC/product?id=" + this.id + "&ref=" + SessionFake.getId();
		this.rendered = true;

	}

}
