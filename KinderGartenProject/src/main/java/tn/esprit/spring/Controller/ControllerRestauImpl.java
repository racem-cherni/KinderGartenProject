package tn.esprit.spring.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.RestaurationService;
import tn.esprit.spring.entities.MedicalRec;
import tn.esprit.spring.entities.foodmedrecwithgramage;
import tn.esprit.spring.entities.foodsandtheircallories;
import tn.esprit.spring.repository.FoodmedrecwithgramageRepository;
import tn.esprit.spring.repository.foodsandtheircalloriesRepository;

@Controller(value = "restaucontroller")
@ELBeanName(value = "restaucontroller")
@Join(path = "/restau1", to = "/pages/parent/Chilinfos.jsf")

public class ControllerRestauImpl {
	@Autowired
	RestaurationService ser;
	private String foodgroupe;
	private String callories;
private List<foodsandtheircallories> fr;
 FoodmedrecwithgramageRepository fdd;

	public List<foodsandtheircallories> ajoutermeu() {
		
		return ser.fd(1);
	}
	
	public void scrapfoods() throws IOException{
		ser.ajouterMenuinfo();
		ser.affecterfoodtokindergarten(1);
	
	}
	
	public void affectfoodbyidmedrec(long food) throws ParseException, IOException{
		/*String url = "?faces-redirect=true";*/
		ser.affectmedicalrectofood(1,food);       
        FacesContext.getCurrentInstance().addMessage
		(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
		"Food have been successfully affected to childs", null));
	}
	
public List<foodmedrecwithgramage> listfoodbyidchild(long idchild){
	return ser.byidchild(idchild);
}
	public List<foodmedrecwithgramage> listallfoodsbyidkinder(long idkinder){
		return ser.listfoodsgrmmagebykinder(idkinder);
	
	}

	public RestaurationService getSer() {
		return ser;
	}

	public FoodmedrecwithgramageRepository getFdd() {
		return fdd;
	}

	public void setFdd(FoodmedrecwithgramageRepository fdd) {
		this.fdd = fdd;
	}

	public List<foodsandtheircallories> getFr() {
		return fr;
	}

	public void setFr(List<foodsandtheircallories> fr) {
		this.fr = fr;
	}

	public void setSer(RestaurationService ser) {
		this.ser = ser;
	}

	public String getFoodgroupe() {
		return foodgroupe;
	}

	public void setFoodgroupe(String foodgroupe) {
		this.foodgroupe = foodgroupe;
	}

	public String getCallories() {
		return callories;
	}

	public void setCallories(String callories) {
		this.callories = callories;
	}

	public List<foodsandtheircallories> getFd() {
		return fr;
	}

	public void setFd(List<foodsandtheircallories> fr) {
		this.fr = fr;
	}
}
