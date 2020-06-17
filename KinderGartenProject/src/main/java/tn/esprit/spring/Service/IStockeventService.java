package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Category_event;
import tn.esprit.spring.entities.Facture_Event;
import tn.esprit.spring.entities.Reservation_stock_event;
import tn.esprit.spring.entities.StockCategory;
import tn.esprit.spring.entities.Stock_event;

public interface IStockeventService {
	
	public List<Stock_event> liststockrecherche (String stockname);
	
	public List<Stock_event> liststockrecherchecat (StockCategory catgorie);

	public List<Stock_event> liststock ();
	
	public void Reserver_stock_event (Long idevent,Long idstock,int quantite);
	
	public List<Reservation_stock_event> listreservationbyevent(Long idevent);
	
	public void annuler_reservation_stock_event(Long EventId, Long stockId);
	
	public String namesstockauto();
	
	public int getnbrreservationevent(Long idevent);
	
	public Double totalpricereservation (Long idevent);
	
	public void facturercartevent(Long idevent);
	
	public Facture_Event factureevent(Long id);

}
