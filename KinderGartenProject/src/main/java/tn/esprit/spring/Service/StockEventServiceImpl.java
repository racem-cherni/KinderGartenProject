package tn.esprit.spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Stock_event;
import tn.esprit.spring.repository.StockEventRepository;

@Service
public class StockEventServiceImpl implements IStockeventService {
	
	@Autowired
	StockEventRepository stockrepository ;

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
}