package tn.esprit.spring.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.IEventService;
import tn.esprit.spring.Service.IInvitation_EventService;
import tn.esprit.spring.Service.IStockeventService;
import tn.esprit.spring.entities.Discussion_Event;
import tn.esprit.spring.entities.Evaluation_Event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Galerie_event;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Reservation_stock_event;
import tn.esprit.spring.entities.StockCategory;
import tn.esprit.spring.entities.Stock_event;

@Scope(value = "session")
@Controller(value = "detaileventJardinController")
@ELBeanName(value = "detaileventJardinController")
@Join(path = "/detaileventjardin", to = "/pages/Jardin/Event/detaileventjardin.jsf")
@MultipartConfig
public class DetaileventJardinControllerImpl {
	
	@Autowired
	IStockeventService istockservice ;
	
	@Autowired
    IEventService ieventservice ;
	
	@Autowired
    IInvitation_EventService iinvitationservice ;
	
	private List<Discussion_Event> listdiscussions ;

	private int nbrdusscussions;
	
	private Long ideventreserve ;
	
	private List<Parent> listparentsparticipes ;
	
	private List<Evaluation_Event> listevaluations ;
	
	private int ratt ;

	
	private List<Parent> listparentsinteresses ;
    
	private List<Galerie_event> listimagesevent ;
	
	private Long id ;
	
	private Part  cinf;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Event geteventbyid (){
		return  ieventservice.geteventbyid(id);
		
	}
	
	public String gotohomeevents() {
		String navigateTo ="null";
		navigateTo = "/pages/Jardin/Event/WelcomeEvent.xhtml?faces-redirect=true";
	    return navigateTo;
	}
	
	public boolean verifierlistdiscussion(){
		if( ieventservice.listdiscussion(id).size() == 0)
			return true ;
		else return false ;
	}
	
	public List<Discussion_Event> getListdiscussions() {
		listdiscussions = ieventservice.listdiscussion(id);
		return listdiscussions ;	
		}

	public void setListdiscussions(List<Discussion_Event> listdiscussions) {
		this.listdiscussions = listdiscussions;
	}

	public int getNbrdusscussions() {
		 nbrdusscussions = ieventservice.nbrdisscussion(id) ;
		return nbrdusscussions;
	}

	public void setNbrdusscussions(int nbrdusscussions) {
		this.nbrdusscussions = nbrdusscussions;
	}
	
	public boolean verifieretatreservation(){
		Date date = new Date();
		if (ieventservice.geteventbyid(id).getDate_final_reservation().after(date))
			return true ;
		else return false ;
		
		
	}
	
	

	public List<Parent> getListparentsparticipes() {
		listparentsparticipes = iinvitationservice.listparentparticipes(id);
		return listparentsparticipes;
	}

	public void setListparentsparticipes(List<Parent> listparentsparticipes) {
		this.listparentsparticipes = listparentsparticipes;
	}

	public List<Parent> getListparentsinteresses() {
		listparentsinteresses = iinvitationservice.listparentinteressess(id);
		return listparentsinteresses;
	}

	public void setListparentsinteresses(List<Parent> listparentsinteresses) {
		this.listparentsinteresses = listparentsinteresses;
	}
	 
	public boolean verifierparentparticipe(){
		if (iinvitationservice.listparentparticipes(id).size()==0)
			return true ;
		else return false ;
	}
	public boolean verifierparentinteresse(){
		if (iinvitationservice.listparentinteressess(id).size()==0)
			return true ;
		else return false ;
	}

	public Part getCinf() {
		return cinf;
	}

	public void setCinf(Part cinf) {
		this.cinf = cinf;
	}
	
	public void addimagevent() throws IOException{
		Date date = new Date();
		cinf.write(		  "C:\\Users\\lenovo\\Desktop\\houssem akkari\\KinderGartenProject\\src\\main\\webapp\\resources\\eventdocs\\"
+cinf.getSubmittedFileName());        
	    File oldFile=new File("C:\\Users\\lenovo\\Desktop\\houssem akkari\\KinderGartenProject\\src\\main\\webapp\\resources\\eventdocs\\"
+cinf.getSubmittedFileName());
	    String img= ieventservice.getAlphaNumericString(7)+cinf.getSubmittedFileName();
	    File newfile =new File(		  "C:\\Users\\lenovo\\Desktop\\houssem akkari\\KinderGartenProject\\src\\main\\webapp\\resources\\eventdocs\\"
+img);
	    oldFile.renameTo(newfile);
	
	    ieventservice.addimageevent(id,img,date);
	
	}

	public List<Galerie_event> getListimagesevent() {
		listimagesevent = ieventservice.listimagesevent(id);
		return listimagesevent;
	}

	public void setListimagesevent(List<Galerie_event> listimagesevent) {
		this.listimagesevent = listimagesevent;
	}
	
	public boolean verifierlistevaluations(){
		if( ieventservice.listevaluationsevents(id).size() == 0)
			return true ;
		else return false ;
	}
	
	public List<Evaluation_Event> getListevaluations() {
		listevaluations = ieventservice.listevaluationsevents(id);
		return listevaluations;
	}

	public void setListevaluations(List<Evaluation_Event> listevaluations) {
		this.listevaluations = listevaluations;
	}

	public int getRatt() {
		ratt = (int)ieventservice.noteevaluationevent(id);
		return ratt;
	}

	public void setRatt(int ratt) {
		this.ratt = ratt;
	}
	
	public String gottoreserveevent() {
		String navigateTo ="null";
       this.setIdeventreserve(this.getId());
	    navigateTo = "/pages/Jardin/Event/reserverevent.xhtml?faces-redirect=false";
		return navigateTo;
 
 }
private int quantite ;
	
	private List<Stock_event> liststockeventsearch ;

	private List<Reservation_stock_event> listreservations ;

	private int nbrreservation ;
	
	private double prixtotalereservation ;


	
	private StockCategory categoriestocksearch ;
	
	
	
	
	
	public String returntodetailevent(){
		  
	    String navigateTo = "/pages/Jardin/Event/detaileventjardin.xhtml?faces-redirect=false";
		return navigateTo;

  }
	
	
public StockCategory getCategoriestocksearch() {
		
		return categoriestocksearch;
	}

	public void setCategoriestocksearch(StockCategory categoriestocksearch) {
		this.categoriestocksearch = categoriestocksearch;
	}

	public List<String> completeLanguage(String languagePrefix) {
		List<String> matches = new ArrayList<String>();
		for(String possibleLanguage: istockservice.namesstockauto().split(",")) {
			if(possibleLanguage.toUpperCase().startsWith(languagePrefix.toUpperCase())) {
				matches.add(possibleLanguage);
			}}
	        return matches ;	
	}
	public List<Stock_event> getListstockeventsearch() {
		liststockeventsearch = istockservice.liststockrecherchecat(categoriestocksearch) ; 
		
		return liststockeventsearch;
	}

	public void setListstockeventsearch(List<Stock_event> liststockeventsearch) {
		this.liststockeventsearch = liststockeventsearch;
	}
	
	public boolean verifierlistsearchreservation(){
		  if (istockservice.liststockrecherchecat(categoriestocksearch).size()== 0)
			  return true ;
			  else 
				  return false;
	  }
	
	
	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	
	public void reserverstockevent(Long idstock){
		istockservice.Reserver_stock_event(ideventreserve, idstock, quantite);
	}
	
	public List<Reservation_stock_event> getListreservations() {
		listreservations = istockservice.listreservationbyevent(ideventreserve);
		return listreservations;
	}

	public void setListreservations(List<Reservation_stock_event> listreservations) {
		this.listreservations = listreservations;
	}
	
	public void deletereservation (Long stockId){
		
		 istockservice.annuler_reservation_stock_event(ideventreserve, stockId);

			
		}
	
	public int getNbrreservation() {
		nbrreservation = istockservice.getnbrreservationevent(ideventreserve);
		return nbrreservation;
	}

	public void setNbrreservation(int nbrreservation) {
		this.nbrreservation = nbrreservation;
	}
	
	
	public double getPrixtotalereservation() {
		prixtotalereservation = istockservice.totalpricereservation(ideventreserve); 
		return prixtotalereservation;
	}

	public void setPrixtotalereservation(double prixtotalereservation) {
		this.prixtotalereservation = prixtotalereservation;
	}
	
	public boolean verfierlistimage (){
		if(ieventservice.listimagesevent(id).size()==0)
			return true ;
		else return false ;
	}

	public Long getIdeventreserve() {
		return ideventreserve;
	}

	public void setIdeventreserve(Long ideventreserve) {
		this.ideventreserve = ideventreserve;
	}
	
	public void facturereventcart() {
		istockservice.facturercartevent(ideventreserve);
	}
	
	
	
	public boolean verifiereventfacture(){
		if (geteventbyid ().getFacture_event() == null)
			return true ;
		else return false ;
	}
	
	public boolean verifierprix(){
		if(istockservice.totalpricereservation(ideventreserve) ==0)
			return true ;
			else return false ;
		
	}
	
	
}


