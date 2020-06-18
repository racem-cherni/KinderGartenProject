package tn.esprit.spring.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.Reunion;
import tn.esprit.spring.entities.Reunion_feedback;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ReunionRepository;
import tn.esprit.spring.repository.Reunion_feedbackRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class Reunion_feedbackServiceImpl implements IReunion_feedbackService {
	@Autowired
	IReunion_feedbackService irfb;
	
	@Autowired
	UserRepository userrepository;
	
	@Autowired
	Reunion_feedbackRepository rfbpository;
	
	@Autowired
	ReunionRepository reunionrepository;
	
	@Autowired
	private Session sessionservice;
	
	
	
	public int ajouterfb1(Reunion_feedback rec,int idReunion,HttpServletRequest request) throws Exception {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();	
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		   cal.add(Calendar.DATE, +1);
		String date1= new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		Date ajoutDate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
        Reunion reun = reunionrepository.findById(idReunion).get();
		if ( reun.getReun_date().before(ajoutDate)) {
		rec.setReunion(reun);
		rec.setReunfb_date(ajoutDate);
		rec.setParent(parent);

		rfbpository.save(rec);
		} 
		else 
		{ throw new Exception("Vous ne peuvez pas envoyez des feedback avant la reunion");
		}
		
		return rec.getReunfb_id();
	}
	
	
	public List<Reunion_feedback> getAllfbByParent(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();	
		return rfbpository.getAllfbByParentt(parent);

}
	
	public List<Reunion_feedback> getAllfbByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rfbpository.getAllfbByJardinn(jardin);

}
	



	public int ajouterfb(Reunion_feedback rec) throws ParseException {
		/*long idparent =2;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		   cal.add(Calendar.DATE, +1);
		String date1= new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		Date ajoutDate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);

		User parent = userrepository.findById(idparent).get();
		rec.setReunfb_date(ajoutDate);;
		rec.setParent(parent);

		rfbpository.save(rec);*/
		return rec.getReunfb_id();
	}

	public void deleteRdvById(int idfb) {
		Reunion_feedback rfb = rfbpository.findById(idfb).get();
		rfbpository.delete(rfb);		
	}
}
