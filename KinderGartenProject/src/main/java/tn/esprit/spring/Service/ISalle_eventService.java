package tn.esprit.spring.Service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import tn.esprit.spring.entities.Salle_event;


public interface ISalle_eventService {
	
	public Salle_event addsalle_event(Salle_event s,HttpServletRequest request);

    public List<Salle_event>  ListSalleEvent(HttpServletRequest request);
    
    public void affecter_Salle_Event  (Long eventid , Long salleeventid,HttpServletRequest request,String exept)  ;
    
    public List<Integer> Listsalle ();
    
}
