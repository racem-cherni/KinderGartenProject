package tn.esprit.spring.Service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import javax.print.attribute.standard.PrinterMessageFromOperator;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import tn.esprit.spring.entities.Disponibilité_salle;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Salle_event;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.Salle_eventRepository;

@Service
public class Salle_eventServiceImpl implements ISalle_eventService {

@Autowired
	
	private Session sessionservice ;

@Autowired
Salle_eventRepository salleeventrepository ;

@Autowired

EventRepository eventrepository ;

	
	@Override
	public Salle_event addsalle_event(Salle_event s, HttpServletRequest request) {
		
		return salleeventrepository.save(s);
		
		
		
		
	}

	@Override
	public List<Salle_event> ListSalleEvent(HttpServletRequest request) {
		
		List<Salle_event> salles = (List<Salle_event>) salleeventrepository.findAll() ;
		return salles;
	}

	@Override
	public void affecter_Salle_Event(Long eventid, Long salleeventid,HttpServletRequest request,String exept)  {
		Event e = eventrepository.findById(eventid).get();
		Salle_event s = salleeventrepository.findById(salleeventid).get();
		 UserApp user = sessionservice.session(request); 
		  KinderGarten kindergarten = user.getKindergarten() ;
		  
		  if (e.getKindereventmaker().equals(kindergarten)){
			  if (s.getDisponibilité().equals(Disponibilité_salle.Disponible) && e.getNbr_participants() <= s.getCapacité_salle()) {
				  e.setSalle_event(s);
				  s.setDisponibilité(Disponibilité_salle.occuppée);
				  eventrepository.save(e);
				  salleeventrepository.save(s);
			  }
			  else 
		          exept="la salle avec lid "+salleeventid+"est occupé ou la capacité ne permet pas " ;
			  System.out.println(exept);
		
		
	}
		  
			  
		  }
	@Override
    public List<Integer> Listsalle (){
		  
		  
		return salleeventrepository.listsalledispo();

	}
}
