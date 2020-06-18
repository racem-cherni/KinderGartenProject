package tn.esprit.spring.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Reunion;
import tn.esprit.spring.entities.Reunion_dispo;
import tn.esprit.spring.entities.Reunion_feedback;

public interface IReunionService {
	public int creerReunion1(Reunion re,HttpServletRequest request) throws ParseException, Exception;
	public void deleteReunionById(int idRe) throws Exception;
	

	void affecterParentAReunion(long parentId, int reId) throws Exception;
	void desaffecterParentduReunion(long parentId, int reId) throws Exception ;
	//public void updateReunion(int RdvId,Reunion re) throws Exception ;
	public List<Reunion> getAllReunionByJardin(HttpServletRequest request);
	public List<Reunion> getAllReunionByParent(HttpServletRequest request);

	
	public void RenouvellementReunion(int reId) throws ParseException, Exception;
	
	public void annulerReunionById(int idRe) throws Exception;
	public List<Reunion> getOldReunionByJardin(HttpServletRequest request);
	public List<Reunion> getTodayReunionByJardin(HttpServletRequest request);
	public List<Reunion> getUpcomingReunionByJardin(HttpServletRequest request);
	public List<Reunion> getTodayReunionByParent(HttpServletRequest request);
	public List<Reunion> getUpcomingReunionByParent(HttpServletRequest request);
	public List<Reunion> getOldReunionByParent(HttpServletRequest request);
	public void annulerMultiReunionById(int idRe, int nbr) throws ParseException, Exception;
	public void ReportReunionFromDateUntilDate(String date, String date2,HttpServletRequest request) throws ParseException, Exception;


     //////////////////////////////////
	///////// JSF //////// JSF ///////////////
	///////////////////////////////
	public List<Reunion> getAllReunionByJardin();
	public List<Reunion> getAllReunionByParent();
	public List<Reunion> getOldReunionByJardin();
	public List<Reunion> getTodayReunionByJardin();
	public List<Reunion> getWeekReunionByJardin();
	public List<Reunion> getMonthReunionByJardin();
	
	
	public List<Reunion> getUpcomingReunionByJardinn();
	public List<Reunion> getParentbyReunion(int reun_idattach);
	public Reunion getReunionByReunID(int reun_idattach);

	public List<Parent> getParentByReunID(int reun_idattach);
	public void desaffecterParentduReunion(String parent_email, int reun_idattach) throws Exception;
	public List<Parent> getAllParent(int reun_idattach);
	public void ajoutreunion(Reunion r);
	public int creerReunion(Reunion r) throws Exception;
	public void updateReunion(int reun_idattach, Reunion r) throws Exception;
	public void reportReunionFromDateUntilDatenew(Date reportDateBegin, Date reportDateEnd) throws Exception;
	public void annulerReunionByIdnew(int idRe) throws Exception;
	public List<Reunion_feedback> getReunfbByReun(int reun_idattach);
	public Parent getParentByFbID(int idfb);
	public int getCountfbByRdv(int reun_idattach);
	public List<Reunion_feedback> getReunfbByReunETParent(int reun_idattach, Parent p);
	public void ajouterfb(Reunion_feedback r) throws ParseException, Exception;
	public String getAlphaNumericString(int i);
	public String getDescriptionByReun_id(int reun_idattach);
	public List<Reunion_feedback> getFbByRdv(int reun_idattach);
	public List<Parent> getParentOfkindergarten(int reun_idattach);
	public List<Reunion_dispo> getDisponibiltyforrP();
	public List<Reunion> findReunionByTitle(String searchString,Parent p);
	public List<Reunion> findReunionByKinderGarten(String searchString,Parent p);
	public List<Reunion> findReunionByTitle(String searchString, KinderGarten jardin);
	public List<Reunion> findReunionByKinderGarten(String searchString, KinderGarten jardin);
	public List<Reunion_dispo> getDisponibiltyforJ();
	public List<Reunion> findReunionByParentName(String searchString, KinderGarten jardin);
	public List<Reunion> getWeekReunionByParent();
	public List<Reunion> getMonthReunionByParent();
	public List<Reunion> getOldReunionByParent();
	



}
