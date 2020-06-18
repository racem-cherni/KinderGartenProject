package tn.esprit.spring.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.SujetReclam;

public interface IReclamationService {
	
	public List<Reclamation> getAllReclamationByJardin(HttpServletRequest request);
	public List<Reclamation> getAllReclamationByParent(HttpServletRequest request);

	public int EnvoyerReclamation(Reclamation rec,long idjardin , HttpServletRequest request) throws ParseException ;
	public Double getPourcentageParTypeEtPeriode(String dated, String datef,SujetReclam Type) throws ParseException;
	public String getPourcentageParPeriode(String datedd, String dateff) throws ParseException;
	public List<Reclamation> getReclamationParType(SujetReclam type,HttpServletRequest request);
	public List<Reclamation> getReclamationParTypeEtPeriode(SujetReclam type, String dated, String datef,HttpServletRequest request) throws ParseException;
	public List<Reclamation> getReclamationByYear(int year,HttpServletRequest request) throws ParseException;
	public List<Reclamation> getReclamationByMonthOfYear(String month, int year,HttpServletRequest request) throws ParseException, Exception;
	//public List<Reclamation> getReclamationByYear(int year, SujetReclam type,HttpServletRequest request) throws ParseException;
	public List<Reclamation> getReclamationByMonthOfYear(String mois, int year, SujetReclam type,HttpServletRequest request) throws Exception;

	//public int ajouterReclmation(Reclamation rec) throws ParseException;
	
	
	///////////////////////
	///////JSF //////// JSF /////////
	////////////////////
	public Double getPourcentageParType(SujetReclam type, KinderGarten jardin) throws ParseException;
	public Integer getNombreParType(SujetReclam type, KinderGarten jardin) throws ParseException;
	public List<Reclamation> getListRParType(SujetReclam type, KinderGarten jardin);
	public void EnvoyerReclamation(Reclamation r) throws ParseException;
	public List<KinderGarten> getJardinByParent(Parent parent);
	public List<Reclamation> getReclamationparparent();
	public Reclamation getLastReclamAddedForJadin(KinderGarten jardin);
	public List<Reclamation> getListParType(SujetReclam sujetattach, KinderGarten jardin);


}
