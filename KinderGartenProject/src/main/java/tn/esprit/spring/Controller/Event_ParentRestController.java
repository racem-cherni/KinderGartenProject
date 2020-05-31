package tn.esprit.spring.Controller;

import javax.servlet.http.HttpServletRequest; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.Service.IEventService;
import tn.esprit.spring.Service.IInvitation_EventService;
import tn.esprit.spring.Service.ISalle_eventService;
import tn.esprit.spring.entities.Etat_Invitation_Event;


@RestController
public class Event_ParentRestController {

	@Autowired
    IEventService ieventservice ;
	
	
	@Autowired
	IInvitation_EventService iinvitationservice ;
	
////////////////////Participation_event/////////////////////////////////////////////////	
	 // http://localhost:8081/SpringMVC/servlet/participer_event/1/participer
	@PutMapping(value = "/participer_event/{idevent}/{etat}") 
	public void participeraevent(@PathVariable("idevent")Long eventId,@PathVariable("etat") Etat_Invitation_Event etat,HttpServletRequest request) {
		iinvitationservice.participer_parent_event(eventId,etat,request);
		
	}
	
	// http://localhost:8081/SpringMVC/servlet/annuler_participer_event/1
		@PutMapping(value = "/annuler_participer_event/{idevent}") 
		public void annulerparticiperaevent(@PathVariable("idevent")Long eventId,HttpServletRequest request) {
			iinvitationservice.annuler_participation_event(eventId, request);

}
//////////////////////////////////////////evaluation_event///////////////////////////////////////////		
		// http://localhost:8081/SpringMVC/servlet/evaluer_event
	    
		
		@PostMapping("/evaluer_event/{eventId}/{valeur}")
		@ResponseBody
		public void evaluer_event(@PathVariable("eventId") Long eventId, @PathVariable("valeur") int valeur,HttpServletRequest request) {
		ieventservice.evaluer_event(eventId, valeur, request);

				}

	/*			  //http://localhost:8081/SpringMVC/servlet/top_evaluation_event
		    	@GetMapping(value="/top_evaluation_event")
		    	@ResponseBody
		    	public Event gettop_evaluation_event() {
		    	 int l=  ieventservice.top_evaluation_event();
		     	Event event=ieventservice.getEventById(l);
		     	return  event ;

		    	 } */
		
		
}
