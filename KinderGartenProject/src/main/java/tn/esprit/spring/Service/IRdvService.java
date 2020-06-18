package tn.esprit.spring.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Rdv_dispo;
import tn.esprit.spring.entities.Rdv_reponse;

public interface IRdvService {
	public int ajouterRdv1(Rdv rdv,long idparent,HttpServletRequest request) throws Exception;
	public int planifierRdv(Rdv rdv,long idparent,HttpServletRequest request) throws Exception;
	public int prendreRdv(Rdv rdv, long idjardinn,HttpServletRequest request) throws Exception;
	public void updateInfoRdvParparent(int rdvId, Rdv rdv,HttpServletRequest request) throws Exception;
	public void updateInfoRdvParjardin(int rdvId, Rdv rdv,HttpServletRequest request) throws Exception;
	public void annulerRdvById(int rdvId,HttpServletRequest request) throws Exception;
	public void ReportRdvFromDateUntilDatee(String date, String date2,HttpServletRequest request) throws Exception;
	
	/////// Les GETS
public List<Rdv> getAllRdvByJardin(HttpServletRequest request);
public List<Rdv> getAllRdvBytitleJ(HttpServletRequest request,String title);
public List<Rdv> getAllRdvByNomparentJ(HttpServletRequest request,String nom);

public List<Rdv> getAllRdvByParent(HttpServletRequest request);
	
	public List<Rdv> getAllRdvTrierDateByJardin(HttpServletRequest request);
	public List<Rdv> getConfirmedRdvByJardin(HttpServletRequest request);	
	public List<Rdv> getRejectedRdvByJardin(HttpServletRequest request);
	public List<Rdv> getoldRdvByJardin(HttpServletRequest request);
	public List<Rdv> getTodayRdvByJardin(HttpServletRequest request);
	public List<Rdv> getweekRdvByJardin(HttpServletRequest request);
	public List<Rdv> getUpcomingRdvByJardin(HttpServletRequest request);
	///////
	//////////////////////JSF////
	public List<Rdv> getAllRdvByJardin();
	public Parent getParentByRdvID(int idrdv);
	public void planifierUnRdv(Rdv r) throws Exception;
	public List<Parent> getAllParent();
	public void annulerRdvById(int rdvId) throws Exception;
	public String getEtatByRdv(int rdvId);
	public void updateInfoRdvParjardinn(int rdvId_attach, Rdv r) throws Exception;
	public void updateInfoRdvParparentt(int rdvId_attach, Rdv r) throws Exception;

	public void reportRdvFromDateUntilDate(Date datefromDate, Date datefromDate2)throws ParseException, Exception;
	public List<Rdv> getAllRdvByParent();
	public int prendreUnRdv(Rdv r) throws Exception;
	public KinderGarten getJardinByRdvID(int idrdv);
	public List<KinderGarten> getAllJardin();
	public List<String> getDisponibiltyforP();
	public List<Rdv_dispo> getDisponibiltyforrP();
	public List<Rdv> getoldRdvByParent();
	public List<Rdv> getTodayRdvByParent();
	public List<Rdv> getweekRdvByParent();
	public int repondreAuRdv(Rdv_reponse r, int rdvId_attach) throws Exception;
	public List<Rdv> getRdvByParentToConfirm();
	public List<Rdv> getweekRdvByJardin();
	public List<Rdv> getoldRdvByJardin();
	public List<Rdv> getUpcomingRdvByJardin();
	public void reportRdvToDate(Date datefromDate, int rdvId_attach) throws ParseException, Exception;
	public List<Rdv> getUpcommingRdvByParent();
	public List<Rdv> getTakenRdvByParent();
	public void updatereponseAuRdv(Rdv_reponse r, int rdvr_id) throws Exception;
	public List<Parent> getParentOfkindergarten(KinderGarten jardin);
	public List<KinderGarten> getKinderGartenofParent();
	public List<Rdv_dispo> getDisponibiltyforJ();
	public List<Rdv> findRdvByTitle(String searchString, KinderGarten jardin);
	public List<Rdv> findRdvByParentName(String searchString, KinderGarten jardin);
	public List<Rdv_dispo> getDisponibiltyforP(KinderGarten k);


}
