package tn.esprit.spring.Service;

import tn.esprit.spring.entities.PointsHistory;

public interface PointsHistoryService {
	
	PointsHistory addActivity(PointsHistory p);
	int getPointsUser(int id);
	

}
