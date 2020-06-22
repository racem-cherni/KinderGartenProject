package tn.esprit.spring.Controller;
 
import static org.assertj.core.api.Assertions.assertThatIllegalStateException; 

import java.util.Date; 
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.RateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.IEventService;
import tn.esprit.spring.Service.IInvitation_EventService;
import tn.esprit.spring.entities.Discussion_Event;
import tn.esprit.spring.entities.Etat_event;
import tn.esprit.spring.entities.Evaluation_Event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Invitation_Event;
import tn.esprit.spring.entities.Parent;

@Scope(value = "session")
@Controller(value = "detaileventController")
@ELBeanName(value = "detaileventController")
@Join(path = "/descripevent", to = "/pages/parent/Event/descripevent.jsf")
public class DetaileventController {
	
	@Autowired
    IEventService ieventservice ;
	
	@Autowired
    IInvitation_EventService iinvitationservice ;
	
	private Long id ;
	
	

	
	private String etat ;
	
	private int noteevaluation ;
	
	private int rating3 ;
	
	private double rat ;
	
	private int ratt ;
	
	private List<Discussion_Event> listdiscussions ;
	
	private List<Evaluation_Event> listevaluations ;
	
	private String commentaire ;
	
	private String evaluationdescription ;
	
	private int nbrdusscussions;
	
	private List<Event> eventsassocié;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Event geteventbyid (){
		return  ieventservice.geteventbyid(id);
		
	}
	
	public Invitation_Event getinvitationbyid(){
		return iinvitationservice.getinvitationevent(id);
	}

	public List<Discussion_Event> getListdiscussions() {
		listdiscussions = ieventservice.listdiscussion(id);

		return listdiscussions;
	}

	public void setListdiscussions(List<Discussion_Event> listdiscussions) {
		this.listdiscussions = listdiscussions;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public int getNbrdusscussions() {
		 nbrdusscussions = ieventservice.nbrdisscussion(id) ;

		return nbrdusscussions;
	}

	public void setNbrdusscussions(int nbrdusscussions) {
		this.nbrdusscussions = nbrdusscussions;
	}
	
	public void addcommentaire(){
		ieventservice.add_commentaire(id, commentaire);
	}

	public List<Event> getEventsassocié() {
		 eventsassocié = ieventservice.eventassocies(id);

		return eventsassocié;
	}

	public void setEventsassocié(List<Event> eventsassocié) {
		this.eventsassocié = eventsassocié;
	}
	
	@Transactional
	public void participateevent() {
		//String navigateTo ="/pages/Parent/Event/WelcomeEvent.xhtml?faces-redirect=true";
		iinvitationservice.participer_parentjsf(id) ;
		
		
	//	return navigateTo;

		
	}
	@Transactional
	public void interesserevent() {
	//	String navigateTo ="/pages/Parent/Event/WelcomeEvent.xhtml?faces-redirect=true";
		iinvitationservice.interesser_parentjsf(id);
	//	return navigateTo;

		
	}
	@Transactional
	public void getinvitationresponse() {
		if (iinvitationservice.getinvitationevent(id).getReponse().equals("participe")){
			iinvitationservice.annuler_participation_eventjsf(id);	
			etat = "annuler participation";
		}
		else {
			iinvitationservice.participer_parentjsf(id);
			etat = "participer" ;
		}
			
	}
	
	public boolean verifieretatparticipation (Long id){
		if (iinvitationservice.getinvitationevent(id).getReponse().equals("participe"))
		return true ;
		else
		return false;
	}
	public boolean verifieretatinteresser (Long id){
		if (iinvitationservice.getinvitationevent(id).getReponse().equals("intéressé"))
		return true ;
		else
		return false;
	}
	
	public boolean verifierdatereservation (Long id) {
		Date date = new Date();
		if (ieventservice.geteventbyid(id).getDate_final_reservation().before(date))
			return true ;
		else 
			return false ;
	}
	
	@Transactional
	public void getinvitationinteresser() {
		if (iinvitationservice.getinvitationevent(id).getReponse().equals("intéressé")){
			iinvitationservice.annuler_interesser_eventjsf(id);	
		}
		else {
			iinvitationservice.interesser_parentjsf(id);
		}
			
	}
	
	public boolean verifierlistdiscussion(){
		if( ieventservice.listdiscussion(id).size() == 0)
			return true ;
		else return false ;
	}
	
	public Parent parentdisscussion(){
		return ieventservice.getparent();
	}

	public int getRating3() {
		return rating3;
	}

	public void setRating3(int rating3) {
		this.rating3 = rating3;
	}
	
	public void onrate(RateEvent rateEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rate Event", "You rated:" + rateEvent.getRating());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
     
    public void oncancel() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public boolean veriftermineevent(){
		if(ieventservice.geteventbyid(id).getEtat_event().equals(Etat_event.terminé))
			return true ;
		else 
			return false ;
	}
	
	
	
	
	public String getEvaluationdescription() {
		return evaluationdescription;
	}

	public void setEvaluationdescription(String evaluationdescription) {
		this.evaluationdescription = evaluationdescription;
	}

	public void addevaluation(){
		ieventservice.evaluate_event(id, rating3, evaluationdescription);
		
	}

	public List<Evaluation_Event> getListevaluations() {
		listevaluations = ieventservice.listevaluationsevents(id);
		return listevaluations;
	}

	public void setListevaluations(List<Evaluation_Event> listevaluations) {
		this.listevaluations = listevaluations;
	}
	
	public boolean verifierlistevaluations(){
		if( ieventservice.listevaluationsevents(id).size() == 0)
			return true ;
		else return false ;
	}

	public double getNoteevaluation() {
		noteevaluation = ieventservice.noteevaluationevent(id);
	
		return noteevaluation;
	}

	public void setNoteevaluation(int noteevaluation) {
		this.noteevaluation = noteevaluation;
	}

	public int getRatt() {
		ratt = (int)ieventservice.noteevaluationevent(id);;
		return ratt;
	}

	public void setRatt(int ratt) {
		this.ratt = ratt;
	}
	
	public boolean verifierlistparentsevaluations(){
		if( ieventservice.listevaluationparent(id).size() == 0)
			return true ;
		else return false ;
	}
	
	public boolean verifierlisteventassocies(){
		if( ieventservice.eventassocies(id).size() == 0)
			return true ;
		else return false ;
	}

	public boolean verifieretatparentinvitation(){
		if ( (iinvitationservice.invitationparent(id).getReponse().equals("en_attente"))||
				(iinvitationservice.invitationparent(id).getReponse().equals("annulé"))){
			return true ;
		}
		else 
			return false;
		
	}
	
	public boolean verifiernombresplacesdisponibles(){
		
		if (ieventservice.geteventbyid(id).getNbr_places_occupes()>=ieventservice.geteventbyid(id).getNbr_places()){
			return true;
		}
		else 
			return false ;
	}

	
	
	

}
