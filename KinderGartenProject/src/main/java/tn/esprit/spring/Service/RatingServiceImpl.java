package tn.esprit.spring.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Child;

import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rating;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.RatingRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class RatingServiceImpl implements IRatingService {
	
	@Autowired
	RatingRepository ratingrepository;
	
	@Autowired
	UserRepository userrepository;
	
	@Autowired
	private Session sessionservice;
	@Autowired
	ParentRepository parentrepository;
	@Autowired
	UserServices userservices;
	
	public int ajouterRating1(Rating rat, long idjardin,HttpServletRequest request) throws Exception {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();		
		
		UserApp jardinuser = userrepository.findById(idjardin).get();
		KinderGarten jardin = jardinuser.getKindergarten();
		
		
		Date ajoutDate= getTodaydate();
		
		VerifierNote(rat);
		rat.setRating_note((rat.getNote_activites()+rat.getNote_nourriture()+rat.getNote_maitres())/3);
		
		List<Rating> l = ratingrepository.verifierexistance(jardin,parent);
		if (l.isEmpty())
		{
		rat.setRating_date(ajoutDate);
		rat.setJardin(jardin);
		rat.setParent(parent);
		ratingrepository.save(rat);
		}
		else { 
			int id =l.get(0).getRating_id();
			updateRating(id,rat);
			}
		return rat.getRating_id();
	}
	/////////
	////////
	/////////updateRating
	public void updateRating(int ratId,Rating ratn) throws Exception {
		Rating rat = ratingrepository.findById(ratId).get();
		   
		Date modifDate=getTodaydate();
		VerifierNote(ratn);

		rat.setRating_description(ratn.getRating_description());
		rat.setNote_activites(ratn.getNote_activites());
		rat.setNote_maitres(ratn.getNote_maitres());
		rat.setNote_nourriture(ratn.getNote_nourriture());
		rat.setRating_date(modifDate);
		rat.setRating_note((ratn.getNote_activites()+ratn.getNote_nourriture()+ratn.getNote_maitres())/3);

		ratingrepository.save(rat);
	}
	//////
	/////
	/////VerifierNote
	public void VerifierNote(Rating rat) throws Exception{
		if ( rat.getNote_nourriture() > 5 || rat.getNote_nourriture() < 1  )
		{ throw new Exception("veuillez choisir une note nouriture de 1 a 5 ");
		}
		else if( rat.getNote_activites() > 5 || rat.getNote_activites() < 1  )
		{ throw new Exception("veuillez choisir une note activité de 1 a 5 ");
		}
		else if( rat.getNote_maitres() > 5 || rat.getNote_maitres() < 1  )
		{ throw new Exception("veuillez choisir une note maitres de 1 a 5 ");
		}
	}
	////
	////
	////
	@Override
	public List<Object> getJardinNoteTrierParNote() {
		return ratingrepository.getJardinNoteTrierParNote();
	}
	
	@Override
	public List<Object> getJardinTrierParNoteNourriture() {
		return ratingrepository.getJardinTrierParNoteNourriture();
	}
	
	@Override
	public List<Object> getJardinTrierParNoteMaitre() {
		return ratingrepository.getJardinTrierParNoteMaitre();
	}
	
	@Override
	public List<Object> getJardinTrierParNoteActivites() {
		return ratingrepository.getJardinTrierParNoteActivites();
	}
	
	public Double getnoteByJardin(HttpServletRequest request) {
		/*double som = ratingrepository.getsommeRatingByJardinn(jardin);
		double count = ratingrepository.getcountRatingByJardinn(jardin);
		return (double) (som/count);*/
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return ratingrepository.getnoteByJardin(jardin);
	}
	
	public List<Rating> getAllRatingByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return ratingrepository.getAllRatingByJardinn(jardin);

}
	public List<Rating> getAllRatingByParent(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();
		return ratingrepository.getAllRatingByParentt(parent);

}
	
	

/*public User getUserById(int id) {
	long userid=new Long(id);
	return userrepository.findById(userid).get();			
}*/





public List<KinderGarten> getAllJardinTrierParNote() {
	return ratingrepository.getAllJardinTrierParNotee();

}



public Date getTodaydate() throws ParseException{
	Calendar cal = Calendar.getInstance();
	   cal.add(Calendar.DATE, +1);
	String date1= new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	Date todaydate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
	return todaydate;
}



////////////////////jsf
@Override
public Double getnoteByJardin(KinderGarten user) {
	Double note=(double) 0;
	if (!(ratingrepository.getnoteByJardin(user) ==null)){
	note = round(ratingrepository.getnoteByJardin(user), 2);
	}
	return note;
}
@Override
public Double getnoteNourritureByJardin(KinderGarten user) {
	Double note=(double) 0;
	if (!(ratingrepository.getnoteByJardin(user) ==null)){
		note =ratingrepository.getnoteNourritureByJardin(user);
	}
	return note;
}
@Override
public Double getnoteActiviteByJardin(KinderGarten jardin) {
	Double note=(double) 0;
	if (!(ratingrepository.getnoteByJardin(jardin) ==null)){
	note =ratingrepository.getnoteActiviteByJardin(jardin);
	}
	return note;

}
@Override
public Double getnoteMaitreByJardin(KinderGarten jardin) {
	Double note=(double) 0;
	if (!(ratingrepository.getnoteByJardin(jardin) ==null)){
		note = ratingrepository.getnoteMaitreByJardin(jardin);
	}
	return note;
	 

}
@Override
public Double getnoteActiviteByJardinParent(KinderGarten jardin, Parent parent) {
	Double note=(double) 0;
	if (!(ratingrepository.getnoteByJardin(jardin) ==null)){
		note =ratingrepository.getnoteActiviteByJardinParent(jardin,parent);
}
	return note;

}
@Override
public Double getnoteMaitreByJardinParent(KinderGarten jardin, Parent parent) {
	Double note=(double) 0;
	if (!(ratingrepository.getnoteByJardin(jardin) ==null)){
		note =ratingrepository.getnoteMaitreByJardinParent(jardin,parent);
}
	return note;

}
@Override
public Double getnoteNourritureByJardinParent(KinderGarten jardin, Parent parent) {
	Double note=(double) 0;
	if (!(ratingrepository.getnoteByJardin(jardin) ==null)){
		note = ratingrepository.getnoteNourritureByJardinParent(jardin,parent);
}
	return note;

}
@Override
public void ajoutrates(Rating rat) throws Exception {
		
	UserApp user = userservices.currentUserJsf();
	Parent parent = user.getParent();
	System.out.println("Sorry ");
	KinderGarten jardin = rat.getJardin();
	Child c = ratingrepository.parentOfJardin(parent,jardin);
	System.out.println("Sorry you can't rate this Garden"+c);
	if (c!=null){
	Date ajoutDate= getTodaydate();
	
	VerifierNote(rat);
	rat.setRating_note((rat.getNote_activites()+rat.getNote_nourriture()+rat.getNote_maitres())/3);
	
	List<Rating> l = ratingrepository.verifierexistance(jardin,parent);
	if (l.isEmpty())
	{
	rat.setRating_date(ajoutDate);
	
	ratingrepository.save(rat);
	}
	else { 
		int id =l.get(0).getRating_id();
		updateRating(id,rat);
		}
	}else {    System.out.println("Sorry you can't rate this Garden");

		throw new Exception("Sorry you can't rate this Garden");
	}
}
@Override
public String getratesdetailByJardinParent(KinderGarten jardin, Parent parent) {
	return ratingrepository.getratesdetailByJardinParent(jardin,parent);

}

public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    long factor = (long) Math.pow(10, places);
    value = value * factor;
    long tmp = Math.round(value);
    return (double) tmp / factor;
}
@Override
public List<KinderGarten> getAllJardinTrierParNotelast() {
	List<KinderGarten> k=ratingrepository.getAllJardinTrierParNotee();
List<KinderGarten> ks=ratingrepository.getAllJardin(); 
System.out.println("Sorry"+ks.size()+" "+k.size());

for ( int i=0;i<ks.size();i++){
	System.out.println("Sorry"+getnoteByJardin(ks.get(i)));
	if(getnoteByJardin(ks.get(i))==0.0) k.add(ks.get(i));
	System.out.println("Sorry"+k.size());

	}


return k;
}
@Override
public List<Rating> getEvaluatioByKinder() {
	UserApp user = userservices.currentUserJsf();
	KinderGarten k = user.getKindergarten();
	return ratingrepository.getEvaluatioByKinder(k);
}

}
