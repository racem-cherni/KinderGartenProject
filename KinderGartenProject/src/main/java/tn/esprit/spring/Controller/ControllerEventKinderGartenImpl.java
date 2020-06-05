package tn.esprit.spring.Controller;

import java.util.Calendar;
import java.util.Date; 
import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.IEventService;
import tn.esprit.spring.Service.IInvitation_EventService;
//import tn.esprit.spring.Service.ISalle_eventService;
import tn.esprit.spring.Service.IStockeventService;
import tn.esprit.spring.entities.Category_event;
import tn.esprit.spring.entities.Discussion_Event;
import tn.esprit.spring.entities.Etat_event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Locationevent;
//import tn.esprit.spring.entities.Salle_event;
import tn.esprit.spring.entities.Stock_event;
import tn.esprit.spring.entities.Type_Event;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.Part;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;



@Scope(value = "session")
@Controller(value = "eventkinderController")
@ELBeanName(value = "eventkinderController")
@Join(path = "/jardinevent", to = "/pages/Jardin/Event/WelcomeEvent.jsf")
@MultipartConfig
public class ControllerEventKinderGartenImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IStockeventService istockservice ;

	@Autowired
    IEventService ieventservice ;
	
	@Autowired
    IInvitation_EventService iinvitationservice ;
	
//	@Autowired
//    ISalle_eventService isalleservice ;
//	
	private Long idevent ;
	
	private int number5;
	
	
	private String namestocksearch ;
	
	private List<Event> listevents ;
	
	private List<Stock_event> liststockeventsearch ;
	
	private List<Discussion_Event> listdiscussions ;

	private int nbrdusscussions;

	private Long ideventreserve ;
	
	private List<Event> upcomingevents;

	private Locationevent location_event ;
	
	public Locationevent[] getLocationevents(){
		return Locationevent.values();
	}
	
	private Type_Event typesearch  ;
	public Type_Event[] getTypesearchs(){
		return Type_Event.values();
	}
	
	private Category_event categorysearch ;
	public Category_event[] getCategoriesearchs(){
		return Category_event.values();
	}
    
	private Etat_event etatsearch ;
	public Etat_event[] getEtatsearchs(){
		return Etat_event.values();
	}
	
	private String heurestartstr ;
	
	private String heurefinstr ;
	private Event todayevents;

    private Date dateeventsearch ;
	
    private String title;
	
	private String description ;
	
	private Date dateevent ;
	
	private java.sql.Time heurestart ;
	
	private java.sql.Time heurefin;
	
	private Date datefinreservation;
	
	private int nbr_places ;
	
	private String photo ;
	
	private Double entry_price ;
	
	private Event searchevent ;


	private Category_event category ;
	
	public Category_event[] getCategories(){
		return Category_event.values();
	}
	
	private Type_Event type_event;
	public Type_Event[] getTypes(){
		return Type_Event.values();
	}
	
	
	//private Salle_event salle_event;
	
	private Part  cinf;
	
	
	

	public Long getIdeventreserve() {
		return ideventreserve;
	}

	public void setIdeventreserve(Long ideventreserve) {
		this.ideventreserve = ideventreserve;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateevent() {
		return dateevent;
	}

	public void setDateevent(Date dateevent) {
		this.dateevent = dateevent;
	}

	public java.sql.Time getHeurestart() {
		return heurestart;
	}

	public void setHeurestart(java.sql.Time heurestart) {
		this.heurestart = heurestart;
	}

	public java.sql.Time getHeurefin() {
		return heurefin;
	}

	public void setHeurefin(java.sql.Time heurefin) {
		this.heurefin = heurefin;
	}

	public Date getDatefinreservation() {
		return datefinreservation;
	}

	public void setDatefinreservation(Date datefinreservation) {
		this.datefinreservation = datefinreservation;
	}

	public int getNbr_places() {
		return nbr_places;
	}

	public void setNbr_places(int nbr_places) {
		this.nbr_places = nbr_places;
	}

	public Category_event getCategory() {
		return category;
	}

	public void setCategory(Category_event category) {
		this.category = category;
	}

	public Type_Event getType_event() {
		return type_event;
	}

	public void setType_event(Type_Event type_event) {
		this.type_event = type_event;
	}

//	public Salle_event getSalle_event() {
//		return salle_event;
//	}
//
//	public void setSalle_event(Salle_event salle_event) {
//		this.salle_event = salle_event;
//	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Double getEntry_price() {
		return entry_price;
	}

	public void setEntry_price(Double entry_price) {
		this.entry_price = entry_price;
	}

	
	public Part getCinf() {
		return cinf;
	}

	public void setCinf(Part cinf) {
		this.cinf = cinf;
	}
	
	
 
	public Type_Event getTypesearch() {
		return typesearch;
	}

	public void setTypesearch(Type_Event typesearch) {
		this.typesearch = typesearch;
	}

	public Category_event getCategorysearch() {
		return categorysearch;
	}

	public void setCategorysearch(Category_event categorysearch) {
		this.categorysearch = categorysearch;
	}

	public Etat_event getEtatsearch() {
		return etatsearch;
	}

	
	
	public Long getIdevent() {
		return idevent;
	}

	public void setIdevent(Long idevent) {
		this.idevent = idevent;
	}

	public String getHeurestartstr() {
		return heurestartstr;
	}

	public void setHeurestartstr(String heurestartstr) {
		this.heurestartstr = heurestartstr;
	}

	public String getHeurefinstr() {
		return heurefinstr;
	}

	public void setHeurefinstr(String heurefinstr) {
		this.heurefinstr = heurefinstr;
	}

	public void setEtatsearch(Etat_event etatsearch) {
		this.etatsearch = etatsearch;
	}

	public Date getDateeventsearch() {
		return dateeventsearch;
	}

	public void setDateeventsearch(Date dateeventsearch) {
		this.dateeventsearch = dateeventsearch;
	}
	
	
	
	

	public String getNamestocksearch() {
		return namestocksearch;
	}

	public void setNamestocksearch(String namestocksearch) {
		this.namestocksearch = namestocksearch;
	}

	public Locationevent getLocation_event() {
		return location_event;
	}

	public void setLocation_event(Locationevent location_event) {
		this.location_event = location_event;
	}
	
	
	
	
	

	public List<Discussion_Event> getListdiscussions() {
		listdiscussions = ieventservice.listdiscussion(idevent);
		return listdiscussions ;	
		}

	public void setListdiscussions(List<Discussion_Event> listdiscussions) {
		this.listdiscussions = listdiscussions;
	}
	
	


	

	public String gotoeventpage() {
		String navigateTo ="null";
		navigateTo = "/pages/Jardin/Event/Eventdash.xhtml?faces-redirect=false";
	    return navigateTo;
	}
	
	
	
	
	
	public Event getSearchevent() {
		return searchevent;
	}

	public void setSearchevent(Event searchevent) {
		this.searchevent = searchevent;
	}

	public String addEvent() throws IOException, ParseException  {
		String navigateTo ="null";
		cinf.write("C:\\Users\\lenovo\\git\\KinderGartenProject\\KinderGartenProject\\src\\main\\webapp\\resources\\eventdocs\\"+cinf.getSubmittedFileName());        
	    File oldFile=new File("C:\\Users\\lenovo\\git\\KinderGartenProject\\KinderGartenProject\\src\\main\\webapp\\resources\\eventdocs\\"+cinf.getSubmittedFileName());
	    String img= ieventservice.getAlphaNumericString(7)+cinf.getSubmittedFileName();
	    File newfile =new File("C:\\Users\\lenovo\\git\\KinderGartenProject\\KinderGartenProject\\src\\main\\webapp\\resources\\eventdocs\\"+img);
	    oldFile.renameTo(newfile);
		ieventservice.addevent(new Event(title, description, dateevent,datefinreservation,changestringtotime(heurestartstr),changestringtotime(heurefinstr),
				nbr_places,img,entry_price,category,type_event,location_event));
	    navigateTo = "/pages/Jardin/Event/WelcomeEvent.xhtml?faces-redirect=false";

		return navigateTo;
	}
	
	public Event searcheventbydate() {
	 searchevent = ieventservice.geteventbydate(dateeventsearch);
	 return searchevent;
	}

	public Event getTodayevents() {
		todayevents = ieventservice.eventtodayjsf();
		return todayevents;
		}

	public void setTodayevents(Event todayevents) {
		this.todayevents = todayevents;
	}
	
	

	public List<Event> getListevents() {
		listevents = ieventservice.filterevents(categorysearch, typesearch, etatsearch);
        return listevents;
	}

	public void setListevents(List<Event> listevents) {
		this.listevents = listevents;
	}
	
	@SuppressWarnings("deprecation")
	public Time changestringtotime(String time) throws ParseException {
     
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		long ms = sdf.parse(time).getTime();
		Time t = new Time(ms);
		t.setHours(t.getHours()+1);
		return t;
	}
	
	@SuppressWarnings("deprecation")
	public String changetimetostirng (Time time) {
		
		time.setHours(time.getHours()-1);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		String strtime = sdf.format(time);
		return strtime;
         
		
		
	}
	
	public Event geteventbyid (){
		return  ieventservice.geteventbyid(idevent);
		
	}
	
	 public String gottodetailevent(Event event) {
			String navigateTo ="null";
            this.setIdevent(event.getId());
            this.setCategory(event.getCategory());
            this.setTitle(event.getTitle());
            this.setType_event(event.getType_event());
            this.setDateevent(event.getDate_event());
            this.setDatefinreservation(event.getDate_final_reservation());
            this.setDescription(event.getDescription());
            this.setEntry_price(event.getEntry_price());
            this.setHeurestartstr(changetimetostirng(event.getEvent_start_heure()));
            this.setHeurefinstr(changetimetostirng(event.getEvent_fin_heure()));
            this.setNbr_places(event.getNbr_places());
            this.setPhoto((event.getPhoto()));
            this.setLocation_event(event.getLocation_event());
		    navigateTo = "/pages/Jardin/Event/detaileventjardin.xhtml?faces-redirect=false";
			return navigateTo;
	 
	 }
	 
	 public String gottodetailtodayevent() {
			String navigateTo ="null";
        this.setIdevent(todayevents.getId());
        this.setCategory(todayevents.getCategory());
         this.setTitle(todayevents.getTitle());
         this.setType_event(todayevents.getType_event());
         this.setDateevent(todayevents.getDate_event());
         this.setDatefinreservation(todayevents.getDate_final_reservation());
         this.setDescription(todayevents.getDescription());
         this.setEntry_price(todayevents.getEntry_price());
         this.setHeurestartstr(changetimetostirng(todayevents.getEvent_start_heure()));
         this.setHeurefinstr(changetimetostirng(todayevents.getEvent_fin_heure()));
         this.setNbr_places(todayevents.getNbr_places());
         this.setPhoto((todayevents.getPhoto()));
         this.setLocation_event(todayevents.getLocation_event());
		    navigateTo = "/pages/Jardin/Event/detaileventjardin.xhtml?faces-redirect=false";
			return navigateTo;
	 
	 }
	 public String gotohomeevents() {
			String navigateTo ="null";
			navigateTo = "/pages/Jardin/Event/WelcomeEvent.xhtml?faces-redirect=true";
		    return navigateTo;
		}


	

    

	public List<Event> getUpcomingevents() {
		upcomingevents = ieventservice.upcomingeventsjsf();
		return upcomingevents;
	}

	public void setUpcomingevents(List<Event> upcomingevents) {
		this.upcomingevents = upcomingevents;
	}
		
	public void removeEvent(Long eventid){
		ieventservice.delete_eventjsf(eventid);
	}

	public int getNbrdusscussions() {
		 nbrdusscussions = ieventservice.nbrdisscussion(idevent) ;
		return nbrdusscussions;
	}

	public void setNbrdusscussions(int nbrdusscussions) {
		this.nbrdusscussions = nbrdusscussions;
	}
	
	public String gottoreserveevent(Long id) {
		String navigateTo ="null";
       this.ideventreserve = id ;
	    navigateTo = "/pages/Jardin/Event/reserverevent.xhtml?faces-redirect=false";
		return navigateTo;
 
 }
	
  public String returntodetailevent(){
	  
	    String navigateTo = "/pages/Jardin/Event/detaileventjardin.xhtml?faces-redirect=false";
		return navigateTo;

  }

public List<Stock_event> getListstockeventsearch() {
	liststockeventsearch = istockservice.liststockrecherche(namestocksearch)	; 
	
	return liststockeventsearch;
}

public void setListstockeventsearch(List<Stock_event> liststockeventsearch) {
	this.liststockeventsearch = liststockeventsearch;
}

public int getNumber5() {
	return number5;
}

public void setNumber5(int number5) {
	this.number5 = number5;
}
	
	
	
	
    

	
	
}
	
	
	
	

