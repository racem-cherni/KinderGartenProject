
package tn.esprit.spring.Service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Lesjours;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Rdv_dispo;
import tn.esprit.spring.entities.Rdv_reponse;
import tn.esprit.spring.entities.Reponse;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.RdvRepository;
import tn.esprit.spring.repository.Rdv_reponseRepository;
import tn.esprit.spring.repository.UserRepository;


@Service
public class RdvServiceImpl implements IRdvService{
	
	@Autowired
	RdvRepository rdvrepository;
	@Autowired
	UserRepository userrepository;
	@Autowired
	Rdv_reponseRepository rdv_Reponserepository;
	@Autowired
	KinderGartenRepository kgrepository;
	@Autowired
	ParentRepository prepository;
	@Autowired
	private Session sessionservice;
	@Autowired
	Rdv_reponseRepository rdvRrepository;
	@Autowired
	UserServices userservices;
	
	
	
	
	////////JSF
	////// JARDIN ///////////////////////////////////////////Jardin /////////////////////Jardin /////////////////////////S
	
////planifier
@Override
public void planifierUnRdv(Rdv rdv) throws Exception {

	UserApp user = userservices.currentUserJsf();
	KinderGarten jardin = user.getKindergarten();
	Parent parent = rdv.getParent();
	Rdv verifexistanceavecP = rdvrepository.existance(rdv.getRdv_date(),jardin,parent);
    System.out.println(rdv.getRdv_heure());
    Time RdvTime = addoneHour(rdv.getRdv_heure());
    System.out.println("/////////////////////////////");
    System.out.println(RdvTime);


	Date ajoutDate= getTodaydate();
	Rdv verifexistance = rdvrepository.existanceplus(RdvTime,rdv.getRdv_date(),jardin);
	int vtr = veriftempsRdv(jardin,rdv.getRdv_date(),RdvTime);
	if (vtr==2) { System.out.println("le jardin n'accepte des rdvs ce jours la");
		throw new Exception("le jardin n'accepte des rdvs ce jours la");
	}
	else if (vtr==1) {System.out.println("le jardin n'accepte pas de rdv dans ce temps");
		throw new Exception("le jardin n'accepte pas de rdv dans ce temps");
	}
	else if (RdvTime.getMinutes()!=0) {System.out.println("merci d'entrer un temps exact par exmples : 09:00,10:00");
		throw new Exception("merci d'entrer un temps exact par exmples : 09:00,10:00");
	}
	if(verifexistanceavecP==null){
	if (verifexistance == null){
	if (rdv.getRdv_date().after(ajoutDate)){
	
	rdv.setJardin(jardin);
	rdv.setParent(parent);
	rdv.setRdv_heure(RdvTime);
	rdv.setRdv_etat(0);

	rdvrepository.save(rdv);
	}
	else 
	{ System.out.println("merci d'entrer une nouvelle date ");
		throw new Exception("merci d'entrer une nouvelle date ");
	}
	}else {
		
		System.out.println("Il ya déja un rdv qui ce déroule ");
		 throw new Exception("Il ya déja un rdv qui ce déroule ");
	}
	}else {
		
		System.out.println("  Vous avez déja un rdv avec ce parent a la date entré ");
	 throw new Exception("  Vous avez déja un rdv avec ce parent a la date entré ");
	}
}
	
////annuler //delete //remove //annulerRdvById
@Override
public void annulerRdvById(int rdvId) throws Exception {
	Date todayDate=getTodaydatee();
	Rdv rdv = rdvrepository.findById(rdvId).get();
    Rdv_reponse RdvR= rdv_Reponserepository.findByRdv(rdv);
	Date rdvstart = combineDateTime(rdv.getRdv_date(),rdv.getRdv_heure()); 
    System.out.println(todayDate);
    System.out.println(rdvstart);


	if (rdvstart.after(todayDate))
	{
	if (RdvR != null){
	rdv_Reponserepository.delete(RdvR);
	}
	rdvrepository.delete(rdv);
	}
	else {System.out.println("Impossible d'annuler : la date de reunion est déja dépassé");
		throw new Exception("Impossible d'annuler : la date de reunion est déja dépassé");	
	}
}

///////////UPDATE //modifier
public void updateInfoRdvParjardinn(int RdvId,Rdv rdvn) throws Exception {
	Rdv rdv = rdvrepository.findById(RdvId).get();
	
	Date updateDate=getTodaydate();
    Time NewRdvTime = addoneHour(rdvn.getRdv_heure());

	if (rdv.getRdv_date().after(updateDate)){
	if (rdvn.getRdv_date().after(updateDate)){
		/*Rdv_reponse RdvR= rdv_Reponserepository.findByRdv(rdv);
		if (RdvR != null){
			rdv_Reponserepository.delete(RdvR);
			}*/
	    System.out.println("updateInfoRdvParjardinn"+rdvn.getRdv_date()+" "+NewRdvTime);

	Rdv verifexistanceplus = rdvrepository.existanceplusforupdate(NewRdvTime,rdvn.getRdv_date(),rdv.getJardin(),RdvId);
	int verifexistance = rdvrepository.existanceeforupdate(rdvn.getRdv_date(),rdv.getJardin(),rdv.getParent(),RdvId);	
	int vtr = veriftempsRdv(rdv.getJardin(),rdvn.getRdv_date(),NewRdvTime);
	if (vtr==2) { 		System.out.println("le jardin n'accepte des rdvs ce jours");
		throw new Exception("le jardin n'accepte des rdvs ce jours");
	}
	else if (vtr==1) {System.out.println("le jardin n'accepte pas de rdv dans ce temps");
		throw new Exception("le jardin n'accepte pas de rdv dans ce temps");
	}
	else if (rdvn.getRdv_heure().getMinutes()!=0) {System.out.println("merci d'entrer un temps exact par exmples : 09:00,10:00");
		throw new Exception("merci d'entrer un temps exact par exmples : 09:00,10:00");
	}

	if (verifexistance == 0){
	if (verifexistanceplus == null){
		rdv.setRdv_etat(0);
		rdv.setRdv_date(rdvn.getRdv_date());
		rdv.setRdv_heure(NewRdvTime);
		rdv.setRdv_description(rdvn.getRdv_description());
		rdv.setRdv_title(rdvn.getRdv_title());
		
	rdvrepository.save(rdv);
    System.out.println("updateInfoRdvParjardinn"+" "+"Save done ");

	} else {System.out.println("Vous avez déja qui se déroule");
		throw new Exception("Vous avez déja qui se déroule");
	}
	//}
	//else 
		// throw new Exception(" vous avez déja un rdv avec ce parent a la date entré ");
	}else {System.out.println("Vous avez déja un rdv avec ce parent a la date entré");
	throw new Exception("Vous avez déja un rdv avec ce parent a la date entré");
	}
	}
	else 
	{ System.out.println("merci d'entrer une nouvelle date ");
	throw new Exception("merci d'entrer une nouvelle date ");
	}
	}
	else {System.out.println("vous ne pouver pas modifier un rdv qui est déja passé ");
	throw new Exception("vous ne pouver pas modifier un rdv qui est déja passé ");
	}
}

/////////REPORTER ///RAPORTER
@Override
public void reportRdvFromDateUntilDate(Date date1, Date date22) throws ParseException, Exception {
	UserApp user = userservices.currentUserJsf();
	KinderGarten jardin = user.getKindergarten();
	//int maxRdv =2;

	
	List<Rdv> l = rdvrepository.getUpcomingRdvFromDateUntildate(jardin,date1,date22);
	Date NouvelleDate = date22;

	   System.out.println("aa"+NouvelleDate);

	Time heurenew=null;
	int b =0;
	Date ajoutDate= getTodaydate();
	if (!(ajoutDate.compareTo(NouvelleDate)==-1)){
		System.out.println(" verifer temps");
		throw new Exception("verifier le temps svp");

	}
	if (date1.after(ajoutDate)){
	for (int i=0;i<l.size();i++){
		System.out.println("report intial "+b);

		while(getHeureDdeJour(jardin,NouvelleDate)==null || getHeureFdeJour(jardin,NouvelleDate)==null){
			NouvelleDate = getDatefromDate(addOneDayToDate(NouvelleDate));
			   System.out.println("a"+NouvelleDate);

		}
		
		
		Time heurd =getHeureDdeJour(jardin,NouvelleDate);
		Time heurf =getHeureFdeJour(jardin,NouvelleDate);
		   System.out.println("aa "+heurd+" "+heurf);
		   
		   
		
		   

		if (b==0) {heurenew=heurd; 			   
		System.out.println("b "+heurenew+" "+NouvelleDate);}
		if (b==1){ // ci c'est pas la premiere traitement en verifie qu'on n'a pas depassé les horairesde travaille
			System.out.println("bplus "+heurenew+" "+NouvelleDate);
		if(VerifHourInIntervale(heurenew,heurd,heurf)==false){
			NouvelleDate = getDatefromDate(addOneDayToDate(NouvelleDate)); // on ajout un jour
			System.out.println("f"+heurenew+""+NouvelleDate);

			while(getHeureDdeJour(jardin,NouvelleDate)==null || getHeureFdeJour(jardin,NouvelleDate)==null){ 
				// on verifie qu'il n'est pas un weekend
				NouvelleDate = getDatefromDate(addOneDayToDate(NouvelleDate));
				System.out.println("g"+heurenew+""+NouvelleDate);

			}
			  heurd =getHeureDdeJour(jardin,NouvelleDate);
		      heurf =getHeureFdeJour(jardin,NouvelleDate);// on prend les nouvelle horaire
		      heurenew=heurd;
				System.out.println("gplus"+heurenew+""+NouvelleDate);

		}
		}
		Rdv verifexistanceplus = rdvrepository.existanceplus(heurenew,NouvelleDate,jardin);//si existe un rdv qui se deroule
		System.out.println("gplusplus"+heurenew+""+NouvelleDate);

		//if (VerifHourInIntervale(heurd,heurd,heurf)==true){
			while (verifexistanceplus != null){ // si existe un rdv qui se deroule
				System.out.println("d addonehour "+heurenew+" "+NouvelleDate);
				heurenew = addoneHour(heurenew); // on ajoute une heure
				System.out.println("d "+heurenew+" "+NouvelleDate);

				if (VerifHourInIntervale(heurenew,heurd,heurf)==true){ //si on a resté dans le temps de travail
					System.out.println("e "+heurenew+"  "+NouvelleDate);

				verifexistanceplus = rdvrepository.existanceplus(heurenew,NouvelleDate,jardin); // on verifie si un rdv deroule
				System.out.println("ee "+verifexistanceplus);

				}
				else {// si on a dépassé l'horaire
					System.out.println("f before"+heurenew+""+NouvelleDate);
					NouvelleDate = getDatefromDate(addOneDayToDate(NouvelleDate)); // on ajout un jour
					System.out.println("f"+heurenew+""+NouvelleDate);

					while(getHeureDdeJour(jardin,NouvelleDate)==null || getHeureFdeJour(jardin,NouvelleDate)==null){ 
						// on verifie que le jardin travail
						System.out.println("ge before"+heurenew+""+NouvelleDate);

						NouvelleDate = getDatefromDate(addOneDayToDate(NouvelleDate));
						System.out.println("ge"+heurenew+""+NouvelleDate);

					}
					System.out.println("geplus"+heurenew+""+NouvelleDate);
					  heurd =getHeureDdeJour(jardin,NouvelleDate);
				      heurf =getHeureFdeJour(jardin,NouvelleDate);// on prend les nouvelle horaire
				      heurenew=heurd;
					 verifexistanceplus = rdvrepository.existanceplus(heurenew,NouvelleDate,jardin); // on teste rdv deroule ou nn
						System.out.println("h "+heurenew+" "+NouvelleDate);

				}
			}// il nya pas un  rdv qui se deroule
			System.out.println("pass"+heurenew+" "+NouvelleDate);

			l.get(i).setRdv_date(getDatefromDate(NouvelleDate));
			l.get(i).setRdv_heure(heurenew);
			System.out.println("i"+l.get(i).getRdv_heure()+" "+l.get(i).getRdv_date() +"heurenew "+ heurenew);

			savechanges(l.get(i).getRdv_id(),l.get(i));
			heurenew=addoneHour(heurenew);
            b=1;
			System.out.println("j "+heurenew+" "+NouvelleDate);
}	
	}else 
	{ 				System.out.println("entrer une nouvelle date");

	throw new Exception("merci d'entrer une nouvelle date ");
}
}


//reportRdvToDate
	@Override
	public void reportRdvToDate(Date datefromDate, int rdvId) throws ParseException, Exception {
		UserApp user = userservices.currentUserJsf();
		KinderGarten jardin = user.getKindergarten();
		Rdv r = rdvrepository.findById(rdvId).get();

		
		
		
		Date NouvelleDate = getDatefromDate(datefromDate);

		System.out.println("aa"+NouvelleDate);
		Date ajoutDate= getTodaydate();
		if (!(datefromDate.after(ajoutDate))){
						System.out.println("entrer une nouvelle date");
			throw new Exception("merci d'entrer une nouvelle date ");
	
		}

			while (getHeureDdeJour(jardin,NouvelleDate)==null || getHeureFdeJour(jardin,NouvelleDate)==null){
				NouvelleDate = getDatefromDate(addOneDayToDate(NouvelleDate));
				   System.out.println("a"+NouvelleDate);
			}
			
			
			
			Time heurd =getHeureDdeJour(jardin,NouvelleDate);
			Time heurf =getHeureFdeJour(jardin,NouvelleDate);
			   System.out.println("aa "+heurd+" "+heurf);
			  
			   Time heurenew=heurd; 			   
		
			Rdv verifexistanceplus = rdvrepository.existanceplus(heurenew,NouvelleDate,jardin);//si existe un rdv qui se deroule
		
			//if (VerifHourInIntervale(heurd,heurd,heurf)==true){
				while (verifexistanceplus != null){ // si existe un rdv qui se deroule
					System.out.println("d addonehour "+heurenew+" "+NouvelleDate);
					heurenew = addoneHour(heurenew); // on ajoute une heure
					System.out.println("d "+heurenew+" "+NouvelleDate);

					if (VerifHourInIntervale(heurenew,heurd,heurf)==true){ //si on a resté dans le temps de travail
						System.out.println("e "+heurenew+"  "+NouvelleDate);

					verifexistanceplus = rdvrepository.existanceplus(heurenew,NouvelleDate,jardin); // on verifie si un rdv deroule
					System.out.println("ee "+verifexistanceplus);

					}
					else {// si on a dépassé l'horaire
						System.out.println("f before"+heurenew+""+NouvelleDate);
						NouvelleDate = getDatefromDate(addOneDayToDate(NouvelleDate)); // on ajout un jour
						System.out.println("f"+heurenew+""+NouvelleDate);

						while(getHeureDdeJour(jardin,NouvelleDate)==null || getHeureFdeJour(jardin,NouvelleDate)==null){ 
							// on verifie qu'il n'est pas un weekend
							System.out.println("ge before"+heurenew+""+NouvelleDate);

							NouvelleDate = getDatefromDate(addOneDayToDate(NouvelleDate));
							System.out.println("ge"+heurenew+""+NouvelleDate);

						}
						System.out.println("geplus"+heurenew+""+NouvelleDate);
						  heurd =getHeureDdeJour(jardin,NouvelleDate);
					      heurf =getHeureFdeJour(jardin,NouvelleDate);// on prend les nouvelle horaire
					      heurenew=heurd;
						 verifexistanceplus = rdvrepository.existanceplus(heurenew,NouvelleDate,jardin); // on teste rdv deroule ou nn
							System.out.println("h "+heurenew+" "+NouvelleDate);
				}
				}
				r.setRdv_etat(0);
				r.setRdv_date(getDatefromDate(NouvelleDate));
				r.setRdv_heure(heurenew);
				System.out.println("i"+r.getRdv_heure()+" "+r.getRdv_date() +"heurenew "+ heurenew);

				savechanges(r.getRdv_id(),r);
				
		
		
		

	}


	@Override
	public List<Rdv> getAllRdvByJardin() {
		UserApp user = userservices.currentUserJsf();
		KinderGarten jardin = user.getKindergarten();
		return rdvrepository.getAllRdvByJardinn(jardin);
		
	}
	
	@Override
	public List<Rdv> getweekRdvByJardin() {
		UserApp user = userservices.currentUserJsf();
		KinderGarten jardin = user.getKindergarten();
		return rdvrepository.getweekRdvByJardin(jardin);

	}

	@Override
	public List<Rdv> getoldRdvByJardin() {
		UserApp user = userservices.currentUserJsf();
		KinderGarten jardin = user.getKindergarten();
		return rdvrepository.getoldRdvByJardin(jardin);
	}

	@Override
	public List<Rdv> getUpcomingRdvByJardin() {
		UserApp user = userservices.currentUserJsf();
		KinderGarten jardin = user.getKindergarten();
		return rdvrepository.getUpcomingRdvByJardin(jardin);
	}


	@Override
	public Parent getParentByRdvID(int idrdv) {
		return rdvrepository.getParentByRdvID(idrdv);
	}
	
	@Override
	public String getEtatByRdv(int rdvId) {
		String etat =null;
		Rdv rdv = rdvrepository.findById(rdvId).get();
	    if (rdv.getRdv_etat()==0) return etat="pending";
	    else if (rdv.getRdv_etat()==1) return etat="Confirmed";
	    else if (rdv.getRdv_etat()==2) return etat="Rejected";
	    return etat;
		
	}
	
	
	@Override
	public List<Parent> getParentOfkindergarten(KinderGarten jardin) {
		return rdvrepository.getParentOfkindergarten(jardin);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////
	////// Parent ///////////////////////////////////////////parent /////////////////////parent /////////////////////////S
    //////////////////////////////////////////////////////////////////////////////////////
	
	//prendrerdv //take app
	@Override
	public int prendreUnRdv(Rdv rdv) throws Exception {
		
		UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
			KinderGarten jardin = rdv.getJardin();
	        Time RdvTime = addoneHour(rdv.getRdv_heure());

			Date ajoutDate= getTodaydate();
			Rdv verifexistanceplus = rdvrepository.existanceplus(RdvTime,rdv.getRdv_date(),jardin);
			int verifexistance = rdvrepository.existancee(rdv.getRdv_date(),jardin,parent);

			int vtr = veriftempsRdv(jardin,rdv.getRdv_date(),RdvTime);
			if (vtr==2) {		System.out.println("le jardin n'accepte des rdvs le weekend");
				throw new Exception("le jardin n'accepte des rdvs le weekend");
			}
			else if (vtr==1) {System.out.println("le jardin n'accepte pas de rdv dans ce temps");
				throw new Exception("le jardin n'accepte pas de rdv dans ce temps");
			}
			if (verifexistance == 0){
			if (verifexistanceplus == null){
			if (rdv.getRdv_date().after(ajoutDate)){
			
			rdv.setParent(parent);
			rdv.setRdv_heure(RdvTime);
	        rdv.setRdv_etat(3);

			rdvrepository.save(rdv);
			}
			else 
			{ System.out.println("le jardin n'accepte pas de rdv dans ce temps");
				throw new Exception("merci d'entrer une nouvelle date ");
			}
			}else {System.out.println("il ya déja un Rdv qui se déroule");
				throw new Exception("il ya déja un Rdv qui se déroule ");
			}
			}else 
			 {System.out.println("vous avez déja un rdv a la date entré");
				throw new Exception(" vous avez déja un rdv a la date entré ");
			 }
			return rdv.getRdv_id();
		
	}

	//getkidenrgatren /getjardin
	public List<KinderGarten> getKinderGartenofParent(){
		UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
		return rdvrepository.getKinderGartenofParent(parent);
		
	}
	
	//getRdvByParentToConfirm
	@Override
	public List<Rdv> getRdvByParentToConfirm() {
		UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
		List<Rdv> rr = rdvrepository.getRdvByParentToConfirm(parent);
		for (int i=0;i<rr.size();i++){
			if (rr.get(i).getRdvreponse()!=null){
				rr.remove(i);
			}
		}
		return rr;
	}

	@Override
	public KinderGarten getJardinByRdvID(int idrdv) {
		return rdvrepository.getJardinByRdvID(idrdv);
	}
	
	
	public void updateInfoRdvParparentt(int RdvId,Rdv rdvn) throws Exception {
		Rdv rdv = rdvrepository.findById(RdvId).get();
		
		Date updateDate=getTodaydate();
		if (rdv.getRdv_date().after(updateDate)){
		if (rdvn.getRdv_date().after(updateDate)){
	        Time NewRdvTime = addoneHour(rdvn.getRdv_heure());

		
		Rdv verifexistanceplus = rdvrepository.existanceplus(NewRdvTime,rdvn.getRdv_date(),rdv.getJardin());
		int verifexistance = rdvrepository.existancee(rdvn.getRdv_date(),rdv.getJardin(),rdv.getParent());	
		int vtr = veriftempsRdv(rdv.getJardin(),rdvn.getRdv_date(),NewRdvTime);
		if (vtr==2){System.out.println("vous avez déja un rdv a la date entré");
			throw new Exception("vous avez déja un rdv a la date entré");
		}
		else if (vtr==1) {System.out.println("vous avez déja un rdv a la date entré");
			throw new Exception("vous avez déja un rdv a la date entré");
		}
		if (verifexistance == 0){
		if (verifexistanceplus == null){
			rdv.setRdv_etat(3);
			rdv.setRdv_date(rdvn.getRdv_date());
			rdv.setRdv_description(rdvn.getRdv_description());
			rdv.setRdv_heure(NewRdvTime);

			rdv.setRdv_title(rdvn.getRdv_title());
			Rdv_reponse RdvR= rdv_Reponserepository.findByRdv(rdv);
			if (RdvR != null){
				rdv_Reponserepository.delete(RdvR);
				}
		rdvrepository.save(rdv);
		} else {System.out.println("Il ya déja un rdv qui se déroule");
			throw new Exception("Il ya déja un rdv qui se déroule");
		}
		}else {System.out.println(" vous avez déja un rdv a la date entré ");
			throw new Exception(" vous avez déja un rdv a la date entré ");
		}
		}
		else 
		{ System.out.println("vous avez déja un rdv a la date entré");
			throw new Exception("merci d'entrer une nouvelle date ");
		}
		}
		else {System.out.println("vous avez déja un rdv a la date entré");
			throw new Exception("vous ne pouver pas modifier un rdv qui est déja passé ");
		}
	}

	@Override
	public List<Rdv> getTakenRdvByParent() {
		UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
		return rdvrepository.getTakenRdvByParent(parent);
	}

	@Override
	public List<Rdv> getUpcommingRdvByParent() {
		UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
		return rdvrepository.getUpcommingRdvByParent(parent);
	}


	@Override
	public void updatereponseAuRdv(Rdv_reponse rdvR,int idrdvR) throws Exception {
		Rdv rdvv = rdvrepository.findById(idrdvR).get();
		Rdv_reponse rdvrr = rdvRrepository.findByRdv(rdvv);
		System.out.println("*****imhere5****" );

		Rdv rdv = rdvrr.getRdv();
		 Date today =getTodaydate();
			System.out.println("*****imhere6****" );

         if (today.compareTo(rdv.getRdv_date())<=0)
         {
		if (rdvrr.getReponse()==rdvR.getReponse()){ System.out.println("vous avez déja repondu par "+rdvR.getReponse());
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
					System.out.println("*****imhere7****" );
		rdvrr.setReponse(rdvR.getReponse());
		rdvrr.setRdvr_message(rdvR.getRdvr_message());
		System.out.println("*****imhere8****" );
		rdvrepository.save(rdv);
		System.out.println("*****imhere9****" );

		rdvRrepository.save(rdvrr);
		System.out.println("*****imhere10****" );


		}	
			}else {		System.out.println(" la date de rdv est déja ecoulé");

				throw new Exception(" la date de rdv est déja ecoulé");
			}

	}

	
	
	@Override
	public List<Rdv> getAllRdvByParent() {
		UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
		return rdvrepository.getAllRdvByParentt(parent);
	}





	@Override
	public List<String> getDisponibiltyforP() {
		UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
	    List<Rdv_dispo> l = rdvrepository.getDisponibiltyforP(parent);
	    List<String> s = new ArrayList<>();
		for (int i=0;i<l.size();i++){
			String m = ""+l.get(i).getJour()+" :"+" De"+ " "+l.get(i).getHeuredm()+" "+"A" + " "+l.get(i).getHeurefm();
			s.add(m);
		}
		for (int i=0;i<s.size()-1;i++){
			if (l.get(i).getJour()==l.get(i+1).getJour())
			s.remove(l.get(i+1));
		}
		return s;
	}

	@Override
	public List<Rdv_dispo> getDisponibiltyforrP() {
		UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
	    List<Rdv_dispo> l = rdvrepository.getDisponibiltyforP(parent);
	  
		return l;
	}

	public int repondreAuRdv(Rdv_reponse rdvR,int idrdv) throws Exception {
		
		Rdv rdv = rdvrepository.findById(idrdv).get();
		
	    Reponse reponse = rdvR.getReponse();
	    rdvR.setRdv(rdv);
	    
	     Date today = getTodaydate();
	    
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
	}else {System.out.println(" la date de rdv est déja ecoulé");
		throw new Exception(" la date de rdv est déja ecoulé");
	}
	     
		return rdv.getRdv_id();

	}

	@Override
	public List<Rdv> getoldRdvByParent() {
		UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
		
		return rdvrepository.getoldRdvByParent(parent);

	}

	
	
	
	
	
	public int ajouterRdv1(Rdv rdv, long idparent,HttpServletRequest request) throws Exception {
		
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		
		//long idparent = new Long(idparentt);
		UserApp parentuser = userrepository.findById(idparent).get();
		Parent parent = parentuser.getParent();
		Date ajoutDate= getTodaydate();
		Rdv verifexistance = rdvrepository.existance(rdv.getRdv_date(),jardin,parent);
		if (verifexistance == null){
		if (rdv.getRdv_date().after(ajoutDate)){
		
		rdv.setJardin(jardin);
		rdv.setParent(parent);
        rdv.setRdv_etat(0);

		rdvrepository.save(rdv);
		}
		else 
		{ throw new Exception("merci d'entrer une nouvelle date ");
		}
		}else 
		 throw new Exception(" Vous avez déja un rdv avec ce parent a la date entré ");
		return rdv.getRdv_id();
	}
	
	@Override
	public int planifierRdv(Rdv rdv, long idparent,HttpServletRequest request) throws Exception {
			/*UserApp connecteduser = sessionservice.session(request);
			KinderGarten jardin = connecteduser.getKindergarten();		
			
			UserApp parentuser = userrepository.findById(idparent).get();
			Parent parent = parentuser.getParent();

			Date ajoutDate= getTodaydate();
			Rdv verifexistance = rdvrepository.existanceplus(rdv.getRdv_heure(),rdv.getRdv_date(),jardin);
			int vtr = veriftempsRdv(jardin,rdv.getRdv_date(),rdv.getRdv_heure());
			if (vtr==2) throw new Exception("le jardin n'accepte des rdvs le weekend");
			else if (vtr==1) throw new Exception("le jardin n'accepte pas de rdv dans ce temps");
			if (verifexistance == null){
			if (rdv.getRdv_date().after(ajoutDate)){
			
			rdv.setJardin(jardin);
			rdv.setParent(parent);
	        rdv.setRdv_etat(0);

			rdvrepository.save(rdv);
			}
			else 
			{ throw new Exception("merci d'entrer une nouvelle date ");
			}
			}else 
			 throw new Exception(" Il ya déja un rdv qui ce déroule ");*/
			return rdv.getRdv_id();
		
	}
	
	@Override
	public int prendreRdv(Rdv rdv, long idjardin,HttpServletRequest request) throws Exception {
	/*	UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();		
		
		UserApp jardinuser = userrepository.findById(idjardin).get();
		KinderGarten jardin = jardinuser.getKindergarten();
			
			Date ajoutDate= getTodaydate();
			Rdv verifexistanceplus = rdvrepository.existanceplus(rdv.getRdv_heure(),rdv.getRdv_date(),jardin);
			int verifexistance = rdvrepository.existancee(rdv.getRdv_date(),jardin,parent);

			int vtr = veriftempsRdv(jardin,rdv.getRdv_date(),rdv.getRdv_heure());
			if (vtr==2) throw new Exception("le jardin n'accepte des rdvs le weekend");
			else if (vtr==1) throw new Exception("le jardin n'accepte pas de rdv dans ce temps");
			if (verifexistance == 0){
			if (verifexistanceplus == null){
			if (rdv.getRdv_date().after(ajoutDate)){
			
			rdv.setJardin(jardin);
			rdv.setParent(parent);
	        rdv.setRdv_etat(3);

			rdvrepository.save(rdv);
			}
			else 
			{ throw new Exception("merci d'entrer une nouvelle date ");
			}
			}else throw new Exception("il ya déja un Rdv qui se déroule ");
			}else 
			 throw new Exception(" vous avez déja un rdv a la date entré ");*/
			return rdv.getRdv_id();
		
	}
	
	public void updateInfoRdvParjardin(int RdvId,Rdv rdvn,HttpServletRequest request) throws Exception {
	/*	Rdv rdv = rdvrepository.findById(RdvId).get();
		
		Date updateDate=getTodaydate();
		if (rdv.getRdv_date().after(updateDate)){
		if (rdvn.getRdv_date().after(updateDate)){
			Rdv_reponse RdvR= rdv_Reponserepository.findByRdv(rdv);
			if (RdvR != null){
				rdv_Reponserepository.delete(RdvR);
				}
		
		Rdv verifexistanceplus = rdvrepository.existanceplus(rdvn.getRdv_heure(),rdvn.getRdv_date(),rdv.getJardin());
		//int verifexistance = rdvrepository.existancee(rdvn.getRdv_date(),rdv.getJardin(),rdv.getParent());	
		int vtr = veriftempsRdv(rdv.getJardin(),rdvn.getRdv_date(),rdvn.getRdv_heure());
		if (vtr==2) throw new Exception("le jardin n'accepte des rdvs le weekend");
		else if (vtr==1) throw new Exception("le jardin n'accepte pas de rdv dans ce temps");
		//if (verifexistance == 0){
		if (verifexistanceplus == null){
			rdv.setRdv_etat(0);
			rdv.setRdv_date(rdvn.getRdv_date());
			rdv.setRdv_heure(rdvn.getRdv_heure());
			rdv.setRdv_description(rdvn.getRdv_description());
			rdv.setRdv_title(rdvn.getRdv_title());
			
		rdvrepository.save(rdv);
		} else throw new Exception("Vous avez déja qui se déroule");
		//}
		//else 
			// throw new Exception(" vous avez déja un rdv avec ce parent a la date entré ");
		}
		else 
		{ throw new Exception("merci d'entrer une nouvelle date ");
		}
		}
		else throw new Exception("vous ne pouver pas modifier un rdv qui est déja passé ");*/
	}
	
	public void updateInfoRdvParparent(int RdvId,Rdv rdvn,HttpServletRequest request) throws Exception {
		/*Rdv rdv = rdvrepository.findById(RdvId).get();
		
		Date updateDate=getTodaydate();
		if (rdv.getRdv_date().after(updateDate)){
		if (rdvn.getRdv_date().after(updateDate)){
		
		
		Rdv verifexistanceplus = rdvrepository.existanceplus(rdvn.getRdv_heure(),rdvn.getRdv_date(),rdv.getJardin());
		//int verifexistance = rdvrepository.existancee(rdv.getRdv_date(),rdv.getJardin(),rdv.getParent());	
		int vtr = veriftempsRdv(rdv.getJardin(),rdvn.getRdv_date(),rdvn.getRdv_heure());
		if (vtr==2) throw new Exception("le jardin n'accepte des rdvs le weekend");
		else if (vtr==1) throw new Exception("le jardin n'accepte pas de rdv dans ce temps");
		//if (verifexistance == 1){
		if (verifexistanceplus == null){
			rdv.setRdv_etat(3);
			rdv.setRdv_date(rdvn.getRdv_date());
			rdv.setRdv_description(rdvn.getRdv_description());
			rdv.setRdv_heure(rdvn.getRdv_heure());

			rdv.setRdv_title(rdvn.getRdv_title());
			Rdv_reponse RdvR= rdv_Reponserepository.findByRdv(rdv);
			if (RdvR != null){
				rdv_Reponserepository.delete(RdvR);
				}
		rdvrepository.save(rdv);
		} else throw new Exception("Il ya déja un rdv qui se déroule");
		//}
	//	else 
			// throw new Exception(" vous avez déja un rdv avec ce parent a la date entré ");
		}
		else 
		{ throw new Exception("merci d'entrer une nouvelle date ");
		}
		}
		else throw new Exception("vous ne pouver pas modifier un rdv qui est déja passé ");*/
	}
	
public void annulerRdvById(int rdvId,HttpServletRequest request) throws Exception {
/*	SimpleDateFormat df = new SimpleDateFormat("HH:mm");

		Date todayDate=getTodaydatee();
		Rdv rdv = rdvrepository.findById(rdvId).get();
	    Rdv_reponse RdvR= rdv_Reponserepository.findByRdv(rdv);
		Date rdvstart = combineDateTime(rdv.getRdv_date(),df.parse(rdv.getRdv_heure())); 
        System.out.println(todayDate);
        System.out.println(rdvstart);


		if (rdvstart.after(todayDate))
		{
		if (RdvR != null){
		rdv_Reponserepository.delete(RdvR);
		}
		rdvrepository.delete(rdv);
		}
		else throw new Exception("Impossible d'annuler : la date de reunion est déja dépassé");*/
		
	}
public void ReportRdvFromDateUntilDatee(String date, String date2,HttpServletRequest request) throws Exception {
	/*UserApp connecteduser = sessionservice.session(request);
	KinderGarten jardin = connecteduser.getKindergarten();	
	//int maxRdv =2;
	List<String> y = new ArrayList<>();

	Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
	Date date22=new SimpleDateFormat("yyyy-MM-dd").parse(date2);
	List<Rdv> l = rdvrepository.getUpcomingRdvFromDateUntildate(jardin,date1,date22);
	Date NouvelleDate = addOneDayToDate(date22);
	   System.out.println("aa"+NouvelleDate);

	String heurenew="a";
	int b =0;
	for (int i=0;i<l.size();i++){
		while(getHeureDFdeJour(jardin,NouvelleDate)==null){
			NouvelleDate = addOneDayToDate(NouvelleDate);
			   System.out.println("a"+NouvelleDate);

		} //s'assurer que le jardin travail à la nouvelle date sinon on ajoute un jour
		y =getHeureDFdeJour(jardin,NouvelleDate);
		String[] h = y.get(0).split(",");
		String heurd =h[0];
		String heurf =h[1]; // ici en a pris les horaies de traville
		if (b==0) heurenew=heurd; 			   
		System.out.println("b"+heurenew+""+NouvelleDate);
		if (b==1){ // ci c'est pas la premiere traitement en verifie qu'on n'a pas depassé les horairesde travaille
		while(VerifHourInIntervale(heurenew,heurd,heurf)==false)
			NouvelleDate = addOneDayToDate(NouvelleDate);//si on a dépassé on ajout un jour et on prend les nv horaires
		y =getHeureDFdeJour(jardin,NouvelleDate);
		h = y.get(0).split(",");
		heurd =h[0];
		heurf =h[1];
		System.out.println("c"+heurenew+""+NouvelleDate);

		}
		Rdv verifexistanceplus = rdvrepository.existanceplus(heurd,NouvelleDate,jardin);//si existe un rdv qui se deroule
	
		//if (VerifHourInIntervale(heurd,heurd,heurf)==true){
			while (verifexistanceplus != null){ // si existe un rdv qui se deroule
				heurenew = addoneHour(heurenew); // on ajoute une heure
				System.out.println("d"+heurenew+""+NouvelleDate);

				if (VerifHourInIntervale(heurenew,heurd,heurf)==true){ //si on a resté dans le temps de travail
					System.out.println("e"+heurenew+""+NouvelleDate);

				verifexistanceplus = rdvrepository.existanceplus(heurenew,NouvelleDate,jardin); // on verifie si un rdv deroule
				}
				else {// si on a dépassé l'horaire
					NouvelleDate = addOneDayToDate(NouvelleDate); // on ajout un jour
					System.out.println("f"+heurenew+""+NouvelleDate);

					while(getHeureDFdeJour(jardin,NouvelleDate)==null){ // on verifie qu'il n'est pas un weekend
						NouvelleDate = addOneDayToDate(NouvelleDate);
						System.out.println("g"+heurenew+""+NouvelleDate);

					}
					y =getHeureDFdeJour(jardin,NouvelleDate);
					 h = y.get(0).split(",");
					 heurd =h[0];
					 heurf =h[1]; // on prend les nouvelle horaire
					 verifexistanceplus = rdvrepository.existanceplus(heurenew,NouvelleDate,jardin); // on teste rdv deroule ou nn
						System.out.println("h"+heurenew+""+NouvelleDate);

				}
			}// il nya pas un  rdv qui se deroule
			l.get(i).setRdv_date(NouvelleDate);
			//l.get(i).setRdv_heure(heurenew);
			System.out.println("i"+heurenew+""+NouvelleDate);

			updateInfoRdvParjardin(l.get(i).getRdv_id(),l.get(i),request);
			heurenew=addoneHour(heurenew);
            b=1;
			System.out.println("j"+heurenew+""+NouvelleDate);*/
	}

		
		
	
	
	private int veriftempsRdv(KinderGarten jardin,Date date, String heure) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		int i =c.get(Calendar.DAY_OF_WEEK)-1;
        System.out.println(i);

		List<String> l = new ArrayList<>();
		if (i==7){
		 l = rdvrepository.getHeureDF(jardin,Lesjours.DIMANCHE);
		} else if (i==1){
			 l = rdvrepository.getHeureDF(jardin,Lesjours.LUNDI);
			}
		else if (i==2){
			 l = rdvrepository.getHeureDF(jardin,Lesjours.MARDI);
			}
		else if (i==3){
			 l = rdvrepository.getHeureDF(jardin,Lesjours.MERCREDI);
			}
		else if (i==4){
			 l = rdvrepository.getHeureDF(jardin,Lesjours.JEUDI);
			} else 
				if (i==5){
		l = rdvrepository.getHeureDF(jardin,Lesjours.VENDREDI);
					}
				else return 2;
        System.out.println(l.get(0));
        
		String[] h = l.get(0).split(",");
		String heurd =h[0];
		String heurf =h[1];
		Date rdvstart = combineDateTime(date,df.parse(heure)); 
		Date acceptRdvstart = combineDateTime(date,df.parse(heurd)); 
		Date  acceptRdvstop = combineDateTime(date,df.parse(heurf)); 
		if (rdvstart.compareTo(acceptRdvstart) >=0 && rdvstart.compareTo(acceptRdvstop) <0)
			return 0;
		else return 1;

		
	}
	
	private Date combineDateTime(Date date, Date time)
	{
		Calendar calendarA = Calendar.getInstance();
		calendarA.setTime(date);
		Calendar calendarB = Calendar.getInstance();
		calendarB.setTime(time);
	 
		calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
		calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
			 
		Date result = calendarA.getTime();
		return result;
	}

	public Date getTodaydatee() throws ParseException{
		Calendar cal = Calendar.getInstance();
		String date1= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cal.getTime());
		Date todaydate=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date1);

		return todaydate;
		
	}
	
	private List<String> getHeureDFdeJour(KinderGarten jardin,Date date) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		int i =c.get(Calendar.DAY_OF_WEEK)-1;
	   System.out.println(i);

		List<String> l = new ArrayList<>();
		if (i==7){
		 l = rdvrepository.getHeureDF(jardin,Lesjours.DIMANCHE);
		} else if (i==1){
			 l = rdvrepository.getHeureDF(jardin,Lesjours.LUNDI);
			}
		else if (i==2){
			 l = rdvrepository.getHeureDF(jardin,Lesjours.MARDI);
			}
		else if (i==3){
			 l = rdvrepository.getHeureDF(jardin,Lesjours.MERCREDI);
			}
		else if (i==4){
			 l = rdvrepository.getHeureDF(jardin,Lesjours.JEUDI);
			} else 
				if (i==5){
		l = rdvrepository.getHeureDF(jardin,Lesjours.VENDREDI);
					}
	return l;    
		

		
	}
	
	/*public User getUserById(int id) {
		long userid=new Long(id);
		return userrepository.findById(userid).get();			
	}*/
	
	public List<Rdv> getAllRdvByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rdvrepository.getAllRdvByJardinn(jardin);
	}
	
	public List<Rdv> getAllRdvBytitleJ(HttpServletRequest request,String title) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		List<Rdv> l = rdvrepository.getAllRdvByJardinn(jardin);
		for (int i=l.size() - 1;i>=0;i--){
			if (!(l.get(i).getRdv_title().toLowerCase().contains(title.toLowerCase()))) {
				l.remove(i);
			}
		}
   return l;	}
	
	public List<Rdv> getAllRdvByNomparentJ(HttpServletRequest request,String nom) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rdvrepository.getAllRdvByNomparentJJ(jardin,nom);
	}
	
	public List<Rdv> getAllRdvByParent(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		Parent user = connecteduser.getParent();
		return rdvrepository.getAllRdvByParentt(user);
	}


	@Override
	public List<Rdv> getAllRdvTrierDateByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rdvrepository.getAllRdvTrierDateByJardin(jardin);

	}
	
	@Override
	public List<Rdv> getConfirmedRdvByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rdvrepository.getConfirmedRdvByJardin(jardin);

	}
	
	@Override
	public List<Rdv> getRejectedRdvByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rdvrepository.getRejectedRdvByJardin(jardin);

	}
	
	
	

	@Override
	public List<Rdv> getTodayRdvByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rdvrepository.getTodayRdvByJardin(jardin);
	}


	@Override
	public List<Rdv> getweekRdvByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rdvrepository.getweekRdvByJardin(jardin);
	}

	@Override
	public List<Rdv> getoldRdvByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rdvrepository.getoldRdvByJardin(jardin);

	}

	
	@Override
	public List<Rdv> getUpcomingRdvByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return rdvrepository.getUpcomingRdvByJardin(jardin);
	}
	
	//////////
	//////////
	//////////
	
	/*public void annulerRdvById(int rdvId) throws Exception {
		
		Date todayDate=getTodaydate();
		Rdv rdv = rdvrepository.findById(rdvId).get();
	    Rdv_reponse RdvR= rdv_Reponserepository.findByRdv(rdv);
		if (rdv.getRdv_date().after(todayDate))
		{
		if (RdvR != null){
		rdv_Reponserepository.delete(RdvR);
		}
		rdvrepository.delete(rdv);
		}
		else throw new Exception("Impossible d'annuler : la date de reunion est déja dépassé");
		
	}*/
	
	////////////
	////////////
	////////////
	public void updateRdv(int RdvId,Rdv rdvn) throws Exception {
		Rdv rdv = rdvrepository.findById(RdvId).get();
		
		Date updateDate=getTodaydate();
		if (rdv.getRdv_date().after(updateDate)){
		if (rdvn.getRdv_date().after(updateDate)){
		
		Rdv_reponse RdvR= rdv_Reponserepository.findByRdv(rdv);
		if (RdvR != null){
			rdv_Reponserepository.delete(RdvR);
			}
		Rdv verifexistance = rdvrepository.existance(rdvn.getRdv_date(),rdv.getJardin(),rdv.getParent());
		if (verifexistance == null){
			rdv.setRdv_etat(0);
			rdv.setRdv_date(rdvn.getRdv_date());
			rdv.setRdv_description(rdvn.getRdv_description());
			rdv.setRdv_title(rdvn.getRdv_title());
		rdvrepository.save(rdv);
		} else throw new Exception("Vous avez déja un rdv avec ce parent a la date entré");
		}	
		else 
		{ throw new Exception("merci d'entrer une nouvelle date ");
		}
		}
		else throw new Exception("vous ne pouver pas modifier un rdv qui est déja passé ");
	}
	
	/////////
	////////
	//////////
	
	public void ReportUpcommingRdvUntilDate(String dateRetourr) throws Exception {
	/*	long idjardin =3;
		User jardin = userrepository.findById(idjardin).get();
		Date dateRetourrr=new SimpleDateFormat("yyyy-MM-dd").parse(dateRetourr);

		int maxRdv =2;
		
		List<Rdv> l = rdvrepository.getUpcomingRdvByJardinUntildate(jardin,dateRetourrr);
		Date NouvelleDate = addOneDayToDate(dateRetourrr);
		
		int nbrRdv = rdvrepository.getNbrRdvAtDate(jardin,NouvelleDate);
		for (int i=0;i<l.size();i++){
			while (nbrRdv>=maxRdv){
				NouvelleDate =addOneDayToDate( NouvelleDate);
				nbrRdv = rdvrepository.getNbrRdvAtDate(jardin,NouvelleDate);
			}
			Rdv verifexistance = rdvrepository.existance(NouvelleDate,jardin,l.get(i).getParent());
			if (verifexistance == null){
				l.get(i).setRdv_date(NouvelleDate);
			updateRdv(l.get(i).getRdv_id(),l.get(i));
			nbrRdv++;
			}
			else annulerRdvById(l.get(i).getRdv_id());
		}*/
	}
	////////////////
	///////////////
	///////////////
	
	/*public void ReportRdvFromDateUntilDate(String date, String date2) throws Exception {
		long idjardin =3;
		User jardin = userrepository.findById(idjardin).get();
		int maxRdv =2;
		
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);
		Date date22=new SimpleDateFormat("yyyy-MM-dd").parse(date2);
		List<Rdv> l = rdvrepository.getUpcomingRdvFromDateUntildate(jardin,date1,date22);
		Date NouvelleDate = addOneDayToDate(date22);
		
		int nbrRdv = rdvrepository.getNbrRdvAtDate(jardin,NouvelleDate);
		for (int i=0;i<l.size();i++){
			while (nbrRdv>=maxRdv){
				NouvelleDate =addOneDayToDate( NouvelleDate);
				nbrRdv = rdvrepository.getNbrRdvAtDate(jardin,NouvelleDate);
			}
			Rdv verifexistance = rdvrepository.existance(NouvelleDate,jardin,l.get(i).getParent());
			if (verifexistance == null){
				l.get(i).setRdv_date(NouvelleDate);

			updateRdv(l.get(i).getRdv_id(),l.get(i));
			nbrRdv++;
			}
			else annulerRdvById(l.get(i).getRdv_id());
		}
	}*/
	//////////
	//////////
	/////////
	//jawha mrigél
	public void ReportRdvOfDate(String date) throws Exception {
		/*long idjardin =3;
		User jardin = userrepository.findById(idjardin).get();
		Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date);

		int maxRdv =2;
		Date NouvelleDate= addOneDayToDate(date1);

		List<Rdv> l = rdvrepository.getRdvBydate(jardin,NouvelleDate);

		int nbrRdv = rdvrepository.getNbrRdvAtDate(jardin,NouvelleDate);
		for (int i=0;i<l.size();i++){
			while (nbrRdv>=maxRdv){
				NouvelleDate =addOneDayToDate( NouvelleDate);
				nbrRdv = rdvrepository.getNbrRdvAtDate(jardin,NouvelleDate);
			}
			Rdv verifexistance = rdvrepository.existance(NouvelleDate,jardin,l.get(i).getParent());
			if (verifexistance == null){
				l.get(i).setRdv_date(NouvelleDate);

			updateRdv(l.get(i).getRdv_id(),l.get(i));
			nbrRdv++;
			}
			else annulerRdvById(l.get(i).getRdv_id());
		}*/
	}

	/////////////
	////////////
	///////////
	
	public Date addOneDayToDate(Date date) throws ParseException{
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, 1);
		//date = c.getTime();
		String date1= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		Date date2 =new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		   System.out.println("fct"+date);

		return date2;
	}
	

	
	public Date getTodaydate() throws ParseException{
		Calendar cal = Calendar.getInstance();
		   cal.add(Calendar.DATE, +1);
		String date1= new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		Date todaydate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);

		return todaydate;
		
	}
	
	
	

	/**/


	/*public int ajouterRdv(Rdv rdv) {
		long idjardin =1;
		User parent = rdv.getParent();
		User jardin = userrepository.findById(idjardin).get();
		rdv.setJardin(jardin);
		rdv.setParent(parent);

		rdvrepository.save(rdv);
		return rdv.getRdv_id();
	}*/

	/*

	

	/*int nbrRdv = rdvrepository.getNbrRdvAtDate(jardin,NouvelleDate);
	for (int i=0;i<l.size();i++){
		while (nbrRdv>=maxRdv){
			NouvelleDate =addOneDayToDate( NouvelleDate);
			nbrRdv = rdvrepository.getNbrRdvAtDate(jardin,NouvelleDate);
		}
		Rdv verifexistance = rdvrepository.existance(NouvelleDate,jardin,l.get(i).getParent());
		if (verifexistance == null){
			l.get(i).setRdv_date(NouvelleDate);

		updateRdv(l.get(i).getRdv_id(),l.get(i));
		nbrRdv++;
		}
		else annulerRdvById(l.get(i).getRdv_id());
	}
}*/
/*public String addoneHour (String heure) throws ParseException{
	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
	Date heureFormat = df.parse(heure);
	Calendar c = Calendar.getInstance(); 
	c.setTime(heureFormat); 
	c.add(Calendar.HOUR_OF_DAY, 1);
	System.out.println("try"+""+c);

	String heureReturn = df.format(c.getTime());
	System.out.println("try2"+""+c);

  //  System.out.println(heureReturn);

	return heureReturn;
}*/

public Boolean VerifHourInIntervale (String heure,String heureOne,String heureTwo) throws ParseException{
	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
	Date heuree = df.parse(heure);
	Date heureeOne = df.parse(heureOne);
	Date heureeTwo = df.parse(heureTwo);
	if (heuree.compareTo(heureeOne) >=0 && heuree.compareTo(heureeTwo) <0)
      return true;
	else

	return true;
	
}
/**/









public void savechanges(int RdvId,Rdv rdvn) throws Exception {
	Rdv rdv = rdvrepository.findById(RdvId).get();
		rdv.setRdv_etat(0);
		rdv.setRdv_date(rdvn.getRdv_date());
		rdv.setRdv_description(rdvn.getRdv_description());
		rdv.setRdv_title(rdvn.getRdv_title());
	
}

@Override
public List<Parent> getAllParent() {
	return rdvrepository.getAllParent();
}


///////////////useful

private int veriftempsRdv(KinderGarten jardin,Date date, Time heure) throws Exception {
	Calendar c = Calendar.getInstance(); 
	c.setTime(date); 
	
	int i =c.get(Calendar.DAY_OF_WEEK)-1;
	//
	if (c.get(Calendar.DAY_OF_WEEK)==1) i=7;
	 //
    System.out.println(i+" "+date);

	List<Time> l = new ArrayList<>();
	if (i==7){
	 l.add(rdvrepository.getHeureD(jardin,Lesjours.DIMANCHE));
	 l.add(rdvrepository.getHeureF(jardin,Lesjours.DIMANCHE));

	} else if (i==1){
		 l.add(rdvrepository.getHeureD(jardin,Lesjours.LUNDI));
		 l.add(rdvrepository.getHeureF(jardin,Lesjours.LUNDI));

		}
	else if (i==2){
		 l.add(rdvrepository.getHeureD(jardin,Lesjours.MARDI));
		 l.add(rdvrepository.getHeureF(jardin,Lesjours.MARDI));
		}
	else if (i==3){
		l.add(rdvrepository.getHeureD(jardin,Lesjours.MERCREDI));
		 l.add(rdvrepository.getHeureF(jardin,Lesjours.MERCREDI));			
		 }
	else if (i==4){
		l.add(rdvrepository.getHeureD(jardin,Lesjours.JEUDI));
		 l.add(rdvrepository.getHeureF(jardin,Lesjours.JEUDI));		
		 } else 
			if (i==5){
				l.add(rdvrepository.getHeureD(jardin,Lesjours.VENDREDI));
				 l.add(rdvrepository.getHeureF(jardin,Lesjours.VENDREDI));			
				 }
			else if (i==6){
				l.add(rdvrepository.getHeureD(jardin,Lesjours.SAMEDI));
				 l.add(rdvrepository.getHeureF(jardin,Lesjours.SAMEDI));	
				
				 }
    

    System.out.println(l);
    if (l.get(0)==null) return 2;
    else {
	Date rdvstart = combineDateTime(date,heure); 
	Date acceptRdvstart = combineDateTime(date,l.get(0)); 
	Date  acceptRdvstop = combineDateTime(date,l.get(1)); 
	if (rdvstart.compareTo(acceptRdvstart) >=0 && rdvstart.compareTo(acceptRdvstop) <0){
		return 0;
	}
	else return 1;

    }
	
}
//VerifHourInIntervale
public Boolean VerifHourInIntervale (Time heure,Time heureOne,Time heureTwo) throws ParseException{
	Date heuree = heure;
	Date heureeOne = heureOne;
	Date heureeTwo = heureTwo;
	if (heuree.compareTo(heureeOne) >=0 && heuree.compareTo(heureeTwo) <0)
      return true;
	else

	return false;
	
}
//getHeureDdeJour
private Time getHeureDdeJour(KinderGarten jardin,Date date) throws Exception {
	Calendar c = Calendar.getInstance(); 
	c.setTime(date); 
	int i =c.get(Calendar.DAY_OF_WEEK)-1;
	if (c.get(Calendar.DAY_OF_WEEK)==1) i=7;

   System.out.println(i);

	Time l = null;
	if (i==7){
	 l = rdvrepository.getHeureD(jardin,Lesjours.DIMANCHE);
	} else if (i==1){
		 l = rdvrepository.getHeureD(jardin,Lesjours.LUNDI);
		}
	else if (i==2){
		 l = rdvrepository.getHeureD(jardin,Lesjours.MARDI);
		}
	else if (i==3){
		 l = rdvrepository.getHeureD(jardin,Lesjours.MERCREDI);
		}
	else if (i==4){
		 l = rdvrepository.getHeureD(jardin,Lesjours.JEUDI);
		} else 
			if (i==5){
	l = rdvrepository.getHeureD(jardin,Lesjours.VENDREDI);
				}else 
					if (i==6){
						l = rdvrepository.getHeureD(jardin,Lesjours.SAMEDI);
									}
return l;  
}
	
private Time getHeureFdeJour(KinderGarten jardin,Date date) throws Exception {
	Calendar c = Calendar.getInstance(); 
	c.setTime(date); 
	int i =c.get(Calendar.DAY_OF_WEEK)-1;
	if (c.get(Calendar.DAY_OF_WEEK)==1) i=7;

   System.out.println(i);

	Time l = null;
	if (i==7){
	 l = rdvrepository.getHeureF(jardin,Lesjours.DIMANCHE);
	} else if (i==1){
		 l = rdvrepository.getHeureF(jardin,Lesjours.LUNDI);
		}
	else if (i==2){
		 l = rdvrepository.getHeureF(jardin,Lesjours.MARDI);
		}
	else if (i==3){
		 l = rdvrepository.getHeureF(jardin,Lesjours.MERCREDI);
		}
	else if (i==4){
		 l = rdvrepository.getHeureF(jardin,Lesjours.JEUDI);
		} else 
			if (i==5){
	l = rdvrepository.getHeureF(jardin,Lesjours.VENDREDI);
				}
			else 
				if (i==6){
					l = rdvrepository.getHeureD(jardin,Lesjours.SAMEDI);
								}
return l;    
	
}

/////////////tools
public Time MinusoneHour (Time heure) throws ParseException{
	Calendar c = Calendar.getInstance(); 
	c.setTime(heure); 
	c.add(Calendar.HOUR_OF_DAY, -1);
	Date d = c.getTime();
	System.out.println("tryminus"+""+c.getTime());
	System.out.println(d);

	
	Time timeValue = new Time(d.getTime());


  //  System.out.println(heureReturn);

	return timeValue;
}

public Time addoneHour (Time heure) throws ParseException{
	Calendar c = Calendar.getInstance(); 
	c.setTime(heure); 
	c.add(Calendar.HOUR_OF_DAY, 1);
	Date d = c.getTime();
	System.out.println("tryaddone"+""+c.getTime());
	System.out.println(d);

	
	Time timeValue = new Time(d.getTime());


  //  System.out.println(heureReturn);

	return timeValue;
}


@Override
public List<KinderGarten> getAllJardin() {
	return rdvrepository.getAllJardin();

}


@Override
public List<Rdv> getweekRdvByParent() {
	UserApp user = userservices.currentUserJsf();
	Parent parent = user.getParent();
	return rdvrepository.getweekRdvByParent(parent);
}

@Override
public List<Rdv> getTodayRdvByParent() {
	// TODO Auto-generated method stub
	return null;
}




//getDatefromDate
	public Date getDatefromDate(Date theDatefull) throws ParseException {
		Calendar c = Calendar.getInstance(); 
		c.setTime(theDatefull); 
		c.add(Calendar.HOUR_OF_DAY, 1);
			String date1= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(c.getTime());
			Date todaydate=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date1);
			System.out.println("getDatefromDate fulldatevalue: "+theDatefull+" datevalue "+todaydate + " with gettime "+ todaydate.getTime());
			return todaydate;
		}

	@Override
	public List<Rdv_dispo> getDisponibiltyforJ() {
		UserApp user = userservices.currentUserJsf();
		KinderGarten jardin = user.getKindergarten();
		return rdvrepository.getDisponibiltyforJ(jardin);	
	}

	@Override
	public List<Rdv> findRdvByTitle(String searchString, KinderGarten jardin) {
		return rdvrepository.findRdvByTitle(searchString,jardin);
	}

	@Override
	public List<Rdv> findRdvByParentName(String searchString, KinderGarten jardin) {
		return rdvrepository.findRdvByParentName(searchString,jardin);
	}

	@Override
	public List<Rdv_dispo> getDisponibiltyforP(KinderGarten k) {
		
		return rdvrepository.getDisponibiltyforJ(k);	

	}

	

	
	

	
}
