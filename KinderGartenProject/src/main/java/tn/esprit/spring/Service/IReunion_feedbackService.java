package tn.esprit.spring.Service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.Reunion_feedback;

public interface IReunion_feedbackService {
	/*public int ajouterfb(Reunion_feedback rec) throws ParseException ;*/
	public int ajouterfb1(Reunion_feedback rec,int idReunion,HttpServletRequest request) throws ParseException, Exception ;

	
	public List<Reunion_feedback> getAllfbByParent(HttpServletRequest request);
	public List<Reunion_feedback> getAllfbByJardin(HttpServletRequest request);
	public void deleteRdvById(int idfb);



}
