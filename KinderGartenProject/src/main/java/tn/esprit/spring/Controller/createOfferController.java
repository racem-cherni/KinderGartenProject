package tn.esprit.spring.Controller;

import java.util.List;

import javax.faces.bean.ViewScoped;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.OfferService;
import tn.esprit.spring.Service.ProductService;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.Product;
import tn.esprit.spring.entities.SessionFake;
import tn.esprit.spring.entities.UserApp;

@ViewScoped
@Controller(value = "createofferController")
@ELBeanName(value = "createofferController")
@Join(path = "/createoffer", to = "/pages/parent/marketplace/createoffer.jsf")
public class createOfferController {
	
	@Autowired
	private ProductService productservice;
	
	@Autowired
	private OfferService offerservice;

	private boolean render1 = true;
	
	private boolean render2 = false;
	
	private boolean render_msg = true;
	
	private String product_name = "";
	
	private String product_ref;
	
	private int id = 0;

	private Product product;
	
	private String msg = "";
	
	private String price;
	
	private String qty;
	
	public void onload() {
		
		this.id = 0;
		this.price = "";
		this.qty = "";
		this.product_name = "";
		this.render1 = true;
		this.render2 = false;
		this.render_msg = true;
		this.msg ="";
		
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

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getProfuct_ref() {
		return product_ref;
	}
	public void setProfuct_ref(String profuct_ref) {
		this.product_ref = profuct_ref;
	}
	public boolean isRender_msg() {
		return render_msg;
	}
	public void setRender_msg(boolean render_msg) {
		this.render_msg = render_msg;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Product getProduct() {
		return productservice.retrieveProduct(id);
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public void select(int id) {
		this.id = id;
		this.product_name = productservice.retrieveProduct(id).getName();
		this.render_msg = false;
		this.product_ref = "("+productservice.retrieveProduct(id).getRef()+")";
	}
	
	public boolean isRender1() {
		return render1;
	}

	public void setRender1(boolean render1) {
		this.render1 = render1;
	}

	public boolean isRender2() {
		return render2;
	}

	public void setRender2(boolean render2) {
		this.render2 = render2;
	}

	public void render(){

		this.msg="";
		this.render1 = !render1;
		this.render2 = !render2;
	}
	
	public boolean isSelect(Product p){
		
		if (p.getId() != id)
			return true;
		
		return false;
	}
	

	public void deselect() {
		this.id = 0;
		this.render_msg = true;
	}
	
	public String addOffer(){
		
		System.out.println(price);
		System.out.println(qty);
		
		UserApp user = new UserApp();
		user.setId(SessionFake.getId());
		KinderGarten k = new KinderGarten();
		
		k.setUserapp(user);
		
		Offer o = new Offer(Double.valueOf(price), Integer.parseInt(qty), productservice.retrieveProduct(id), k);
		
		offerservice.addOffer(o);
		
		return "market";

	}
	
	public List<Product> getAllProducts(){
		
	
		if (this.msg.length() == 0)
			return productservice.getAllActivated();
		else
			return productservice.searchProducts(msg);
	}
	

}
