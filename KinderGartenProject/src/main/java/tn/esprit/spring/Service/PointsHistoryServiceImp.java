package tn.esprit.spring.Service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.PointsHistory;
import tn.esprit.spring.repository.PointsHistoryRepository;


@Service
public class PointsHistoryServiceImp implements PointsHistoryService {

	@Autowired
	private PointsHistoryRepository PointsHistoryRepository;

	@Override
	public PointsHistory addActivity(PointsHistory p) {

		p.setDate(new Date());

		return PointsHistoryRepository.save(p);
	}

	@Override
	public int getPointsUser(Long id) {

		int points;
		
		try {
			points = PointsHistoryRepository.getPointsUser(id);
		} catch (Exception e) {
			points = 0 ;			
		}
		
		return points;
	}

}
