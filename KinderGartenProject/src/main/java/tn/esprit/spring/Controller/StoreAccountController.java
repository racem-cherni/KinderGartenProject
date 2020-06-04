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
import tn.esprit.spring.entities.SessionFake;

@Scope(value = "session")
@Controller(value = "storeaccountController")
@ELBeanName(value = "storeaccountController")
@Join(path = "/storeaccount", to = "/pages/parent/marketplace/store_account.jsf")
public class StoreAccountController {
	
	@Autowired
	PointsHistoryService PointsHistoryService;

	private int total_points;
	
	public String getTotal_points() {
		this.total_points = PointsHistoryService.getPointsUser(SessionFake.getId());
		return "" + this.total_points;
	}

	public void setTotal_points(int total_points) {
		this.total_points = total_points;
	}
   
	

}
