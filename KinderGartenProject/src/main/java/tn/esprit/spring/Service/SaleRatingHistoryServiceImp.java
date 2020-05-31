package tn.esprit.spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.SaleRatingHistory;
import tn.esprit.spring.repository.SaleRatingHistoryRepository;


@Service
public class SaleRatingHistoryServiceImp implements SaleRatingHistoryService {

	@Autowired
	private SaleRatingHistoryRepository SaleRatingHistoryRepository;

	@Override
	public SaleRatingHistory addSaleRatingHistory(SaleRatingHistory p) {

		
		
		return SaleRatingHistoryRepository.save(p);
	}

	@Override
	public void deleteSaleRatingHistory(int id) {

		SaleRatingHistoryRepository.deleteById(id);
	}

	@Override
	public List<SaleRatingHistory> retrieveAllSaleRatingHistorys() {

		List<SaleRatingHistory> products = (List<SaleRatingHistory>) SaleRatingHistoryRepository.findAll();
		return products;
	}

	@Override
	public SaleRatingHistory updateSaleRatingHistory(SaleRatingHistory u) {
		
		return SaleRatingHistoryRepository.save(u);
	}

	@Override
	public SaleRatingHistory retrieveSaleRatingHistory(int id) {
		SaleRatingHistory p = SaleRatingHistoryRepository.findById((id)).orElse(null);
		return p;
	}
	
}