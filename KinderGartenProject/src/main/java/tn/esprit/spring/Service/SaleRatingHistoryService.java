package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.SaleRatingHistory;


public interface SaleRatingHistoryService {
	
	//////////////CRUD////////////////
	List<SaleRatingHistory> retrieveAllSaleRatingHistorys();
	SaleRatingHistory addSaleRatingHistory(SaleRatingHistory p);
	void deleteSaleRatingHistory(int id);
	SaleRatingHistory updateSaleRatingHistory(SaleRatingHistory u);
	SaleRatingHistory retrieveSaleRatingHistory(int id);
	///////////////////////////////
	

	//void deleteSaleRatingHistoryByRef(int id);
	
}