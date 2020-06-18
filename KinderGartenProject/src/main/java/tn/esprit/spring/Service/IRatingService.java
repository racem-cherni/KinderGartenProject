package tn.esprit.spring.Service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rating;
import tn.esprit.spring.entities.Rdv;

public interface IRatingService {
	
	public int ajouterRating1(Rating rat, long idjardin,HttpServletRequest request) throws ParseException, Exception;
	public List<Rating> getAllRatingByJardin(HttpServletRequest request);
	public List<KinderGarten> getAllJardinTrierParNote();

	public List<Rating> getAllRatingByParent(HttpServletRequest request);
	public void updateRating(int RdvId,Rating rdv) throws ParseException, Exception ;
	//public User getUserById(int id) ;
	public Double getnoteByJardin(HttpServletRequest request);
	public List<Object> getJardinTrierParNoteNourriture();
	public List<Object> getJardinTrierParNoteMaitre();
	public List<Object> getJardinTrierParNoteActivites();
	public List<Object> getJardinNoteTrierParNote();
/*	public int ajouterRating(Rating rat) throws ParseException;
	public List<Rating> getAllRatingByJardin(User Jardin);
	public List<User> getAllJardinTrierParNote();
	public List<Object> getJardinNoteTrierParNote();

	public List<Rating> getAllRatingByParent(User Jardin);
	public void updateRating(int RdvId,Rating rdv) throws ParseException, Exception ;
	public User getUserById(int id) ;
	public Double getnoteByJardin(User jardin);
	public int ajouterRating1(Rating rat, int idjardin) throws ParseException, Exception;
	public List<Object> getJardinTrierParNoteNourriture();
	public List<Object> getJardinTrierParNoteMaitre();
	public List<Object> getJardinTrierParNoteActivites();
	*/
	
	/////////////////
	/////////////////////JSF/////////////
	public Double getnoteByJardin(KinderGarten user);
	public Double getnoteNourritureByJardin(KinderGarten user);
	public Double getnoteActiviteByJardin(KinderGarten jardin);
	public Double getnoteMaitreByJardin(KinderGarten jardin);
	public Double getnoteActiviteByJardinParent(KinderGarten jardin, Parent parent);
	public Double getnoteMaitreByJardinParent(KinderGarten jardin, Parent parent);
	public Double getnoteNourritureByJardinParent(KinderGarten jardin, Parent parent);
	public void ajoutrates(Rating rat) throws ParseException, Exception;
	public String getratesdetailByJardinParent(KinderGarten jardin, Parent parent);
	public List<KinderGarten> getAllJardinTrierParNotelast();
	public List<Rating> getEvaluatioByKinder();
}
