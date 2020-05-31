package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Stock_event;

public interface IStockeventService {
	
	public List<Stock_event> liststockrecherche (String stockname);
	public List<Stock_event> liststock ();


}
