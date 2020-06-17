package tn.esprit.spring.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Category_event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Facture_Event;
import tn.esprit.spring.entities.Reservation_stock_event;
import tn.esprit.spring.entities.Reservation_stock_eventPk;
import tn.esprit.spring.entities.StockCategory;
import tn.esprit.spring.entities.Stock_event;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.Facture_EventRepository;
import tn.esprit.spring.repository.Reservation_StockEventRepository;
import tn.esprit.spring.repository.StockEventRepository;

@Service
public class StockEventServiceImpl implements IStockeventService {
	
	@Autowired
	StockEventRepository stockrepository ;
	
	@Autowired
	Facture_EventRepository facturerepository ;
@Autowired
	
	EventRepository eventrepository ;

@Autowired
Reservation_StockEventRepository reservationstockrepository ;

	@Override
	public List<Stock_event> liststockrecherche(String stockname) {
		List<Stock_event> liststock = stockrepository.liststockrecherche(stockname);
	/*  List<Stock_event> l = null;
	  List<Stock_event> liststocks =stockrepository.liststockrecherche(stockname);
		for (int i=0;i<liststocks.size();i++){
			 if (liststocks.get(i).getName().contains(stockname))
				 l.add(liststocks.get(i));
		 }
		return liststocks;*/
		return liststock;
	}
	


@Override
public List<Stock_event> liststock() {
	List<Stock_event> liststock = (List<Stock_event>) stockrepository.findAll();
	return liststock ;
}



@Override
public void Reserver_stock_event(Long idevent,Long idstock,int quantite) {
	
	Date date = new Date();
	//Event event = eventrepository.findById(idevent).get();
	Stock_event stock = stockrepository.findById(idstock).get();
	
	Reservation_stock_eventPk reservationpk = new Reservation_stock_eventPk();
	reservationpk.setIdevent(idevent);
	reservationpk.setIdstock(idstock);
	Reservation_stock_event reservation = new Reservation_stock_event();
	reservation.setReservationstockeventpk(reservationpk);
	reservation.setDate_reservation(date);
	reservation.setQuantite(quantite);
	reservation.setPrice(stock.getPrix_stock()*quantite);
	reservationstockrepository.save(reservation);
	
}



@Override
public List<Reservation_stock_event> listreservationbyevent(Long idevent) {
	Event event = eventrepository.findById(idevent).get();

	List<Reservation_stock_event> listreservations = reservationstockrepository.listreservationsevent(event);
	return listreservations;
	
	
}





@Override
public String namesstockauto() {
	//List<Stock_event> list = (List<Stock_event>) stockrepository.findAll();
	List<String> list = stockrepository.liststockcategory();
	String listnames ="";
	 for (int i=0;i<list.size();i++){
		 String k = list.get(i);
		 listnames = listnames+","+k;
	 }
	 listnames = listnames.substring(1);
	 return listnames ;
	
}



@Override
public List<Stock_event> liststockrecherchecat(StockCategory catgorie) {
	List<Stock_event> liststock = stockrepository.liststockrecherchecat(catgorie);
	return liststock;
}

@Override
public int getnbrreservationevent(Long idevent) {
	Event event = eventrepository.findById(idevent).get();
	int nbr = reservationstockrepository.nbrreservationevent(event);
	return nbr ;
}



@Override
public void annuler_reservation_stock_event(Long EventId, Long stockId) {
      reservationstockrepository.deleteReservation(EventId, stockId);	
}

@Override
public Double totalpricereservation(Long idevent) {
	// TODO Auto-generated method stub
	return reservationstockrepository.prixtotalereservation(idevent) ;
}
@Override
public void facturercartevent(Long idevent){
	 Date datee = new Date();
    Facture_Event f = new Facture_Event();
   f.setDate_facture(datee);
   f.setMontant_totale(totalpricereservation(idevent));
   facturerepository.save(f);
	Event event = eventrepository.findById(idevent).get();
    event.setFacture_event(f);
    eventrepository.save(event);
   
}

@Override
 public Facture_Event factureevent(Long id){
		Event event = eventrepository.findById(id).get();
        	Facture_Event f = event.getFacture_event();
        	return f ;
   }

}