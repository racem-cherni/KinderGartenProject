package tn.esprit.spring.Controller;

import java.util.List; 

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.Service.IEventService;
import tn.esprit.spring.Service.IInvitation_EventService;
//import tn.esprit.spring.Service.ISalle_eventService;
import tn.esprit.spring.entities.Category_event;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
//import tn.esprit.spring.entities.Salle_event;
import tn.esprit.spring.entities.UserApp;


@RestController
public class Event_KinderGartenRestController {
	
	@Autowired
    IEventService ieventservice ;
	
//	@Autowired
//	ISalle_eventService isalleeventservice ;
//	
	@Autowired
	IInvitation_EventService iinvitationservice ;
	
/////////////////////////////////event////////////////////////////////////////////////////////////

	//http://localhost:8081/all-event-bykindergarten
		@GetMapping(path="/mylist_event")
		@ResponseBody
		public List<String> retrieveEvent(HttpServletRequest request) {
			
		return ieventservice.mylist_event(request);
		 }
	
		//getsession
		@GetMapping(path="/getsession")
		@ResponseBody
		public UserApp getsession(HttpServletRequest request) {
			
		return ieventservice.getsession(request);
		 }
		
		//Ajouter Event : http://localhost:8081/add-event
		@PostMapping("/add-event")
		@ResponseBody
		
		public void addEvent(@RequestBody Event e ,HttpServletRequest request) {
		 ieventservice.add_event(e,request);
	     }
		
		//Modifier Event : http://localhost:8081/update-event
		@PutMapping("/update-event")
		@ResponseBody
		public void ModifierEvent(@RequestBody Event e ,HttpServletRequest request) {
		ieventservice.update_event(e,request);
	    }   
		
		//http://localhost:8081/SpringMVC/servlet/remove-event/{event-id}
		@DeleteMapping("/remove-event/{event-id}")
		@ResponseBody
		public void removeUser(@PathVariable(name="event-id") Long eventId,HttpServletRequest request) {
		ieventservice.delete_event(eventId,request);
		 }
		 
		//http://localhost:8081/SpringMVC/servlet/detail_event/{idevent}
				@GetMapping(path="/detail_event/{idevent}")
				@ResponseBody
				public Event detailEvent(@PathVariable("idevent") long eventId,HttpServletRequest request) {
					
				return ieventservice.detail_event(eventId,request);
				 }
				
				//http://localhost:8081/SpringMVC/servlet/detail_event/{idevent}
				@GetMapping(path="/event_categorie/{categorie}")
				@ResponseBody
				public List<String> Event_categorie(@PathVariable("categorie") Category_event categorie ,HttpServletRequest request) {
					
				return ieventservice.getAllEventByCategorie(categorie,request);
				 }
			
		//http://localhost:8081/SpringMVC/servlet/detail_event/{idevent}
		@GetMapping(path="/event_today")
		@ResponseBody
		public Event Event_oftoday(HttpServletRequest request) {
					
		return ieventservice.getEventoftoday(request);
		}
		
////////////////////////////////////////////salleevent/////////////////////////////////////////////////
		        //Ajouter Salle : http://localhost:8081/add-salleevent
//				@PostMapping("/add-salleevent")
//				@ResponseBody
//				
//				public void addsalleEvent(@RequestBody Salle_event s ,HttpServletRequest request) {
//				 isalleeventservice.addsalle_event(s, request);
//			     }
//				
				//http://localhost:8081/SpringMVC/servlet/listsallesevent
//				@GetMapping(path="/list-salleevent")
//				@ResponseBody
//				public List<Salle_event> Allsallesevent(HttpServletRequest request) {
//					
//				return isalleeventservice.ListSalleEvent(request);
//				 }
//				
//				// http://localhost:8081/SpringMVC/servlet/affectersalleAEvent/1/1
//			    @PutMapping(value = "/affectersalleAEvent/{EventId}/{salleeventId}") 
//				public void affectersalleAEvent(@PathVariable("EventId")Long EventId, @PathVariable("salleeventId")Long salleId,HttpServletRequest request,String exept) throws Exception {
//			    	isalleeventservice.affecter_Salle_Event(EventId, salleId,request,exept);
//				}
/////////////////////////////////////////////Invitation///////////////////////////////////////////////
			    
			  //inviter parent : http://localhost:8081/SpringMVC/servlet/inviter_parent/
				@PostMapping("/inviter_parent/{idevent}/{idparent}")
				@ResponseBody
				
				public void inviterparent(@PathVariable("idevent")Long EventId,@PathVariable("idparent")Long parentId,HttpServletRequest request) {
				 iinvitationservice.inviter_parent_event(EventId, parentId, request);
			     }
				
				//http://localhost:8081/SpringMVC/servlet/listsallesevent
				@GetMapping(path="/list-parent")
				@ResponseBody
				public List<Parent> listchild(HttpServletRequest request) {
					
				return iinvitationservice.listparentskindergarten(request);
				 }
				
				  //inviter tousparent : http://localhost:8081/SpringMVC/servlet/inviter_tousparent/
					@PostMapping("/inviter_tousparent/{idevent}")
					@ResponseBody
					
					public void invitertousparent(@PathVariable("idevent")Long EventId,HttpServletRequest request) {
					 iinvitationservice.inviter_tousparent_event(EventId,request);
				     }
					
					//http://localhost:8081/SpringMVC/servlet/eventmostevalue
					@GetMapping(path="/event_most_evalue")
					@ResponseBody
					public Event eventmostevalue(HttpServletRequest request) {
						
					return ieventservice.event_most_evalue(request);
					 }
				
				
			    
			    
			    
}
