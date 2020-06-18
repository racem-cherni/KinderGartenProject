package tn.esprit.spring.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Rdv_reponse;
import tn.esprit.spring.entities.Reponse;
import tn.esprit.spring.repository.RdvRepository;
import tn.esprit.spring.repository.Rdv_reponseRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class Rdv_reponseServiceImpl implements IRdv_reponseService {

	
	@Autowired
	Rdv_reponseRepository rdvRrepository;
	@Autowired
	RdvRepository rdvrepository;
	@Autowired
	UserRepository userrepository;
	@Autowired
	Rdv_reponseServiceImpl rdvRservice;
	
	
	public int repondreAuRdv(Rdv_reponse rdvR,int idrdv) throws Exception {
		
		Rdv rdv = rdvrepository.findById(idrdv).get();
		
        Reponse reponse = rdvR.getReponse();
        rdvR.setRdv(rdv);
        
         Date today =rdvRservice.getTodayDate();
         if (rdv.getRdv_etat()==0){
         if (today.compareTo(rdv.getRdv_date())<=0)
        {
        if (reponse == Reponse.CONFIRM) {
        	rdv.setRdv_etat(1);
        }
        else if (reponse == Reponse.REJECT)
        {
        	rdv.setRdv_etat(2);
        	}
        rdvR.setRdvr_date(today);;

		rdvrepository.save(rdv);
		rdvRrepository.save(rdvR);
}else throw new Exception(" la date de rdv est déja ecoulé");
         }else updatereponseAuRdv(rdvR,idrdv);
		return rdv.getRdv_id();

	}
	
	public void updatereponseAuRdv(Rdv_reponse rdvR,int idrdvR) throws Exception {
		Rdv rdvv = rdvrepository.findById(idrdvR).get();
		Rdv_reponse rdvrr = rdvRrepository.findByRdv(rdvv);
		Rdv rdv = rdvrr.getRdv();
		 Date today =rdvRservice.getTodayDate();
         if (today.compareTo(rdv.getRdv_date())<=0)
         {
		if (rdvrr.getReponse()==rdvR.getReponse()){
			throw new Exception("vous avez déja repondu par "+rdvR.getReponse());
		}
			else {		rdvrepository.save(rdv);

				 if (rdvR.getReponse() == Reponse.CONFIRM) {
			        	rdv.setRdv_etat(1);
			        }
			        else if (rdvR.getReponse() == Reponse.REJECT)
			        {
			        	rdv.setRdv_etat(2);
			        	}
		
		rdvrr.setReponse(rdvR.getReponse());
		rdvrr.setRdvr_message(rdvR.getRdvr_message());
		rdvrepository.save(rdv);
		
		rdvRrepository.save(rdvrr);


		}	
			}else throw new Exception(" la date de rdv est déja ecoulé");

	}
	
	public Date getTodayDate() throws ParseException{
		Calendar cal = Calendar.getInstance();
		   cal.add(Calendar.DATE, +1);
		String date1= new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		Date today=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		return today;
		
	}

	@Override
	public List<Rdv_reponse> getReponseByRdv(Rdv rdv) {
		
		return rdvRrepository.getReponseByRdv(rdv);
	}

}
