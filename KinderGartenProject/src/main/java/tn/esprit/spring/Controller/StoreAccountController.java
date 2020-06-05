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
import org.primefaces.event.SlideEndEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.PointsHistoryService;
import tn.esprit.spring.Service.PanierProductService;
import tn.esprit.spring.entities.PanierProduct;
import tn.esprit.spring.entities.SessionFake;


@Scope(value = "session")
@Controller(value = "storeaccountController")
@ELBeanName(value = "storeaccountController")
@Join(path = "/storeaccount", to = "/pages/parent/marketplace/store_account.jsf")
public class StoreAccountController {
	
	@Autowired
	PointsHistoryService PointsHistoryService;
	
	@Autowired
	PanierProductService PanierProductService;

	private int total_points;
	
	private int total_sales_count;
	
	private double total_sales;
	
	private List<PanierProduct> products;
	
	public String getTotal_points() {
		this.total_points = PointsHistoryService.getPointsUser(SessionFake.getId());
		return "" + this.total_points;
	}

	public void setTotal_points(int total_points) {
		this.total_points = total_points;
	}

	public int getTotal_sales_count() {
		this.total_sales_count = PanierProductService.getSalesCount(SessionFake.getId());
		return this.total_sales_count;
	}

	public void setTotal_sales_count(int total_sales_count) {
		this.total_sales_count = total_sales_count;
	}

	public double getTotal_sales() {
		this.total_sales = PanierProductService.getSalesTotalPrice(SessionFake.getId());
		return this.total_sales;
	}

	public void setTotal_sales(double total_sales) {
		this.total_sales = total_sales;
	}

	public List<PanierProduct> getProducts() {
		this.products = PanierProductService.getAllBoughtProductsByUser(SessionFake.getId());
		return products;
	}

	public void setProducts(List<PanierProduct> products) {
		this.products = products;
	}
	
	public String getColor(String state) {
		
		if (state.equals("WAITING"))
			return "text-info";
		else if (state.equals("CANCELED"))
			return "text-danger";
		else
			return "text-success";
		
		
	}
   
	

}

