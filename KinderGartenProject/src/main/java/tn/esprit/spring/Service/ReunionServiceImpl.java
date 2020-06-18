
package tn.esprit.spring.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Lesjours;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Reunion;
import tn.esprit.spring.entities.Reunion_dispo;
import tn.esprit.spring.entities.Reunion_feedback;
import tn.esprit.spring.entities.TypeReunion;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.ReunionRepository;
import tn.esprit.spring.repository.Reunion_feedbackRepository;
import tn.esprit.spring.repository.UserRepository;


@Service
@EnableScheduling
public class ReunionServiceImpl implements IReunionService {
	@Autowired
	Reunion_feedbackRepository rfbpository;
	@Autowired
	ReunionRepository reunionrepository;
	@Autowired
	UserRepository userrepository;
	@Autowired
	ParentRepository parentrepository;
	@Autowired
	KinderGartenRepository kgrepository;
	@Autowired
	ReunionServiceImpl rsImpl;
	
	@Autowired
	Reunion_feedbackServiceImpl rfbImpl;
	@Autowired
	private Session sessionservice;
	@Autowired
	UserServices userservices;
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////jardin ////////////////////////jardin//////////////////////jardin/////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	////creerReunion //ajouter reunion //add reun
    public int creerReunion(Reunion re) throws Exception {
	System.out.println("*****voila "+re.getReun_date() + " heured = "+re.getReun_heureD()+" heuref="+re.getReun_heureF());
	
	Reunion ren = new Reunion();
	Date currentdaytime = getTodayDateAndTime();
	Date Reunionstart = combineDateTime(re.getReun_date(),re.getReun_heureD());
	UserApp user = userservices.currentUserJsf();
	KinderGarten jardin = user.getKindergarten();
	Time ReunStartTime = addoneHour(re.getReun_heureD());
	Time ReunEndTime = addoneHour(re.getReun_heureF());

	Time heurdjour =getHeureDdeJour(jardin,re.getReun_date());
	Time heurfjour =getHeureFdeJour(jardin,re.getReun_date());
	System.out.println("veriftempsReun Procedure pour ladate : "+re.getReun_date() + " ReunStartTime = "+ReunStartTime);
	int vtr = veriftempsReun(jardin,re.getReun_date(),ReunStartTime);
	
	if (Reunionstart.after(currentdaytime)){
	if (vtr==2) {
		System.out.println("le jardin ne planifie pas de reunion ce jours la");
		throw new Exception("le jardin ne planifie pas de reunion ce jours la");
	}
	else if (vtr==1) {System.out.println("Veuiller choisir des horaires entre "+MinusoneHour(heurdjour)+" et "+MinusoneHour(heurfjour));
		throw new Exception("Veuiller choisir des horaires entre "+MinusoneHour(heurdjour)+" et "+MinusoneHour(heurfjour));
	}
	else 
	if (VerifHourInIntervale(ReunStartTime,ReunEndTime,heurdjour,heurfjour)==false) {
		
		System.out.println("Veuiller choisir des horaires entre "+MinusoneHour(heurdjour)+" et "+MinusoneHour(heurfjour));
		throw new Exception("Veuiller choisir des horaires entre "+MinusoneHour(heurdjour)+" et "+MinusoneHour(heurfjour));}
	if ( re.getJardin()==null){
	ren.setJardin(jardin);
	}
	else ren.setJardin(re.getJardin());

	int s=getcontraintenew(re,jardin,re.getReun_date());
	if (s==-1){
		
		System.out.println("verifier le temps svp");

	throw new Exception("verifier le temps svp");
	}
	else
	if (s == 0) {
	ren.setReun_nbrplace(re.getReun_nbrplace());
	ren.setReun_title(re.getReun_title());
	ren.setReun_nbrparticipant(0);
	ren.setReun_description(re.getReun_description());
	ren.setReun_date(re.getReun_date());
	ren.setReun_heureD(ReunStartTime);
	ren.setReun_heureF(ReunEndTime);
	ren.setReun_type(re.getReun_type());
	reunionrepository.save(ren);
	}
	else {System.out.println("Vous avez deja une reunion qui se deroule");
		throw new Exception("Vous avez deja une reunion qui se deroule");
	}
	} else {System.out.println("Merci d'entrer une nouvelle date");
		throw new Exception("Merci d'entrer une nouvelle date");
	}

	return re.getReun_id();	

	}
    
    
    
    
    
    public void ccreationReunion(Reunion re,long id) {
    	Reunion ren = new Reunion();

    	KinderGarten jardin = kgrepository.findById(id).get();

    	ren.setJardin(jardin);
    	ren.setReun_nbrplace(re.getReun_nbrplace());
    	ren.setReun_title(re.getReun_title());
    	ren.setReun_nbrparticipant(0);
    	ren.setReun_description(re.getReun_description());
    	ren.setReun_date(re.getReun_date());
    	ren.setReun_heureD(re.getReun_heureD());
    	ren.setReun_heureF(re.getReun_heureF());
    	ren.setReun_type(re.getReun_type());
    	reunionrepository.save(ren);
    	System.out.println("creaaaaation terminer");
    }
    //update
    public void updateReunion(int ReId,Reunion ren) throws Exception {
	Reunion re = reunionrepository.findById(ReId).get();

	Date now=getTodayDateAndTime();
	Date currentdaytime = getTodayDateAndTime();
	Date Reunionstart = combineDateTime(ren.getReun_date(),ren.getReun_heureD());
	
	Time ReunStartTime = addoneHour(ren.getReun_heureD());
	Time ReunEndTime = addoneHour(ren.getReun_heureF());

	Time heurdjour =getHeureDdeJour(re.getJardin(),ren.getReun_date());
	Time heurfjour =getHeureFdeJour(re.getJardin(),ren.getReun_date());

	int vtr = veriftempsReun(re.getJardin(),ren.getReun_date(),ReunStartTime);

	
	Date finROld = combineDateTime(re.getReun_date(),re.getReun_heureF());
	System.out.println("1111updateReunion Reunionstart: "+Reunionstart+" ReunStartTime "+ReunStartTime
			+" ReunEndTime "+ReunEndTime+" heurdjour "+heurfjour+" vtr "+vtr+
			" finROld "+finROld
			);

	if (finROld.compareTo(now)>=0){
	if (Reunionstart.after(currentdaytime)){

	Date dateROld = re.getReun_date();
	System.out.println("re.getReun_date(): "+re.getReun_date());
	int s= getNbrReunionsBydate(re,ren);

	Date debutR = combineDateTime(ren.getReun_date(),ren.getReun_heureD()); 
	Date finR = combineDateTime(ren.getReun_date(),ren.getReun_heureF());
	if (!(debutR.compareTo(finR)==-1))
	{System.out.println("verifier le temps svp");
		throw new Exception("verifier le temps svp");
	}
	else 
	if (vtr==2) {System.out.println("le jardin n'accepte des rdvs ce jours la");
		throw new Exception("le jardin n'accepte des rdvs ce jours la");
	}
	else if (vtr==1) {System.out.println("Veuiller choisir des horaires entre "+MinusoneHour(heurdjour)+" et "+MinusoneHour(heurfjour));
		throw new Exception("Veuiller choisir des horaires entre "+MinusoneHour(heurdjour)+" et "+MinusoneHour(heurfjour));
	}
	if (VerifHourInIntervale(ReunStartTime,ReunEndTime,heurdjour,heurfjour)==false) 
		{System.out.println("Veuiller choisir des horaires entre "+MinusoneHour(heurdjour)+" et "+MinusoneHour(heurfjour));
		throw new Exception("Veuiller choisir des horaires entre "+MinusoneHour(heurdjour)+" et "+MinusoneHour(heurfjour));
		}
	
	if ( ren.getReun_type() == TypeReunion.EXCEPTIONNEL)
	{
		if(re.getReun_type() == TypeReunion.EXCEPTIONNEL){
			
			int m = getNbrIntersectionBydateForupdate( re.getJardin(), ren.getReun_date(), addoneHour(ren.getReun_heureD()),addoneHour(ren.getReun_heureF()),re.getReun_heureD());
			ren.setReun_heureD(addoneHour(ren.getReun_heureD())); 
			ren.setReun_heureF(addoneHour(ren.getReun_heureF()));
			System.out.println("here 9 m="+m+" "+ren.getReun_heureD()+" "+ren.getReun_heureF());

			 if (m==0) setupdates ( re, ren);
				else {System.out.println("Vous avez deja une reunion qui se deroule");
					throw new Exception("Vous avez deja une reunion qui se deroule");
				}
			
		}else {
			if (re.getReun_type() == TypeReunion.MENSUEL){
				
				int m = getNbrIntersectionBydateForupdate( re.getJardin(), ren.getReun_date(), addoneHour(ren.getReun_heureD()),addoneHour(ren.getReun_heureF()),re.getReun_heureD());
				ren.setReun_heureD(addoneHour(ren.getReun_heureD())); 
				ren.setReun_heureF(addoneHour(ren.getReun_heureF()));
				System.out.println("here 10 m="+m+" "+ren.getReun_heureD()+" "+ren.getReun_heureF());
				 if (m==0) {
					 ccreationReunion(ren, re.getJardin().getId());
					 annulerReunionByIdnew(ReId);
				 }
					else {System.out.println("Vous avez deja une reunion qui se deroule");
						throw new Exception("Vous avez deja une reunion qui se deroule");
					}
				 
				/*System.out.println("AVOID INTERSECTION START");
				System.out.println("add1MonthToDate(dateROld) " + add1MonthToDate(dateROld));
				List<Reunion> lr = reunionrepository.getReunionsBydate(re.getJardin(),add1MonthToDate(dateROld));
				for (int i=0;i<lr.size();i++){
					System.out.println("*+*+*" + lr.get(i).getReun_id() +" avec la date" + add1MonthToDate(dateROld));
				}
				Date theDatefull = AvoidIntersectionnew(re,add1MonthToDate(dateROld));
				
				Date theDate =getDatefromDate(theDatefull);
				
				Time theStartTime = getTimefromDate(theDatefull);
				Time theEndTime = addDurationtoTime(theDatefull,re.getReun_heureF().getTime()-re.getReun_heureD().getTime());
				System.out.println("theDatefull: "+theDatefull+" theDate "+theDate
						+" theStartTime "+theStartTime+" theEndTime "+theEndTime+" setReun_heureD "+addoneHour(theStartTime)+
						" setReun_heureF "+addoneHour(theEndTime) +" setReun_date "+theDate
						);
				
					
					re.setReun_date(addOneDayToDate(theDate));
					re.setReun_heureD(theStartTime);
					re.setReun_heureF(theEndTime);
					
					System.out.println("2222updateReunion theDatefull: "+theDatefull+" theDate "+theDate
							+" theStartTime "+theStartTime+" theEndTime "+theEndTime+" setReun_heureD "+addoneHour(theStartTime)+
							" setReun_heureF "+addoneHour(theEndTime) +" setReun_date "+theDate
							);
					reunionrepository.save(re);
				*/
			}
			else if (re.getReun_type() == TypeReunion.HEBDOMADAIRE){
				int m = getNbrIntersectionBydateForupdate( re.getJardin(), ren.getReun_date(), addoneHour(ren.getReun_heureD()),addoneHour(ren.getReun_heureF()),re.getReun_heureD());
				ren.setReun_heureD(addoneHour(ren.getReun_heureD())); 
				ren.setReun_heureF(addoneHour(ren.getReun_heureF()));
				System.out.println("here 11 m="+m+" "+ren.getReun_heureD()+" "+ren.getReun_heureF());

				 if (m==0) {
					 ccreationReunion(ren, re.getJardin().getId());
					 annulerReunionByIdnew(ReId);
				 }
					else {System.out.println("Vous avez deja une reunion qui se deroule");
						throw new Exception("Vous avez deja une reunion qui se deroule");
					}
				/*System.out.println("AVOID INTERSECTION START");
				Date theDatefull = AvoidIntersectionnew(re,add1WeekToDate(dateROld));
				Date theDate =getDatefromDate(theDatefull);
				Time theStartTime = getTimefromDate(theDatefull);
				Time theEndTime = addDurationtoTime(theDatefull,re.getReun_heureF().getTime()-re.getReun_heureD().getTime());
				
				re.setReun_date(addOneDayToDate(theDate));
				re.setReun_heureD(theStartTime);
				re.setReun_heureF(theEndTime);
				reunionrepository.save(re);*/
				
			}
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		int reid = getReun_idOrdered(re.getJardin()).get(0);
        if (re.getParents().size()!=0 ){
		for ( int i=0 ; i<re.getParents().size(); i++ ){
			affecterParentAReunion(re.getParents().get(i).getId(), reid);
		}}
		}
	}
	else if (ren.getReun_type() == TypeReunion.MENSUEL || ren.getReun_type() == TypeReunion.HEBDOMADAIRE){
		int m = getNbrIntersectionBydateForupdate( re.getJardin(), ren.getReun_date(), addoneHour(ren.getReun_heureD()),addoneHour(ren.getReun_heureF()),re.getReun_heureD());
		ren.setReun_heureD(addoneHour(ren.getReun_heureD())); 
		ren.setReun_heureF(addoneHour(ren.getReun_heureF()));
		 if (m==0) setupdates ( re, ren);
			else {System.out.println("Vous avez deja une reunion qui se deroule");
				throw new Exception("Vous avez deja une reunion qui se deroule");
			}
	
		
	}
	}
	else {System.out.println("Veuillez svp entrer une nouvelle date ");
		throw new Exception("Veuillez svp entrer une nouvelle date ");	
	}
	 }
	else {System.out.println("Cette reunion est déja faite impossible de le modifier");
		throw new Exception("Cette reunion est déja faite impossible de le modifier");	
	}
}
	
    //report //raport
    public void reportReunionFromDateUntilDatenew(Date dateDR, Date dateFR) throws Exception {
    	Reunion ren = new Reunion();
    	Date theDatefull =null;

    	UserApp user = userservices.currentUserJsf();
    	KinderGarten jardin = user.getKindergarten();
    	
    	List<Reunion> l = reunionrepository.getUpcomingReunionFromDateUntildate(jardin,dateDR,dateFR);
    	for (int i=0;i<l.size();i++){
    	System.out.println("+++" + l.get(i).getReun_id() + " dateDR " +dateDR+" dateFR "+dateFR);
    	
    	}
    	Date NouvelleDate = dateFR;
    	System.out.println(dateFR);
    	
    	for (int i=0;i<l.size();i++){
    		System.out.println("dans reporter avoid intersection pour" + l.get(i).getReun_id() +" avec la date" + NouvelleDate);

    		 theDatefull = AvoidIntersectionnew(l.get(i),justdate(dateFR));
    			System.out.println("dans reporter after avoid intersection nouvelledate " +NouvelleDate +"thedatefull: " + theDatefull +" heure"+	l.get(i).getReun_heureD() +" "+l.get(i).getReun_heureF() );
    			Date theDate =getDatefromDate(theDatefull);
    			Time theStartTime = getTimefromDate(theDatefull);
    		Time theEndTime = addDurationtoTime(theDatefull,l.get(i).getReun_heureF().getTime()-l.get(i).getReun_heureD().getTime());
    		
    		System.out.println("reportReunionFromDateUntilDatenew theDatefull: "+theDatefull+" theDate "+theDate
    				+" theStartTime "+theStartTime+" theEndTime "+theEndTime+" setReun_heureD "+addoneHour(theStartTime)+
    				" setReun_heureF "+addoneHour(theEndTime) +" setReun_date "+theDate
    				);
    		
    		
    		ren.setReun_nbrplace(l.get(i).getReun_nbrplace());
    		ren.setReun_title(l.get(i).getReun_title());
    		ren.setReun_nbrparticipant(l.get(i).getReun_nbrparticipant());
    		ren.setReun_description(l.get(i).getReun_description());
    		ren.setReun_date(add1DayToDate(theDate));
    		ren.setReun_heureD(theStartTime);
    		ren.setReun_heureF(theEndTime);
    		ren.setReun_type(TypeReunion.EXCEPTIONNEL);
    		System.out.println("ren.gethf"+" "+ren.getReun_heureD()+" re.gethf "+ren.getReun_heureF()+"start time"
    				+theStartTime +" endtime "+theEndTime + "l.get(i).getParents().size() "+l.get(i).getParents().size()
    				);
    		creationReunion(ren);	
    		int reid = getReun_idOrdered(jardin).get(0);
            if (l.get(i).getParents().size()!=0 ){
    		for ( int index=0 ; index<l.get(i).getParents().size(); index++ ){
    			System.out.println(".................affeceter "+l.get(i).getParents().get(0) + " a reunion id "+reid);

    			affecterParentAReunion(l.get(i).getParents().get(index).getId(), reid);
    		}}
    		System.out.println(i+".crée");
    		
    		System.out.println("debuuuuuuuuuuut annuler");
            annulerReunionByIdnew(l.get(i).getReun_id());
    		System.out.println("fiiiiiiiiiiiiiiiin annuler");

    	}
    }
    
    
    //annuler
    public void annulerReunionByIdnew(int idRe) throws Exception {
    	Reunion re = reunionrepository.findById(idRe).get();
    	 Date dateR = re.getReun_date();
    	 Date finR = combineDateTime(re.getReun_date(),re.getReun_heureF());		
    	Calendar cal = Calendar.getInstance();
    	String date1= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    	Date now=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date1);
    	
    	if (now.compareTo(finR) <0 ){
    		 if (re.getReun_type() == TypeReunion.EXCEPTIONNEL ){
    			 deleteReunionById(idRe);
    		 }
    	 if (re.getReun_type() == TypeReunion.MENSUEL ){
    		 
    			

    			Date theDatefull = AvoidIntersectionnew(re,add1MonthToDate(dateR));
    			System.out.println("dans annuler nouvelledate " +add1MonthToDate(dateR) +"thedatefull: " + theDatefull +" heure"+	re.getReun_heureD() +" "+re.getReun_heureF() );
    			System.out.println("dans annuler nouvelleheured " +getTimefromDate(theDatefull) );

    			Date theDate =getDatefromDate(theDatefull);
    			Time theStartTime = getTimefromDate(theDatefull);
    			Time theEndTime = addDurationtoTime(theDatefull,re.getReun_heureF().getTime()-re.getReun_heureD().getTime());
    			System.out.println("annulerReunionByIdnew theDatefull: "+theDatefull+" theDate "+theDate
    					+" theStartTime "+theStartTime+" theEndTime "+theEndTime
    					+" setReun_date "+theDate
    					);
    			re.setReun_date(addOneDayToDate(theDate));
    			re.setReun_heureD(theStartTime);
    			re.setReun_heureF(theEndTime);
    	    	reunionrepository.save(re);
    		
    			 
    			}
    	 else if ( re.getReun_type() == TypeReunion.HEBDOMADAIRE){
    		
    			
    			 	Date theDatefull = AvoidIntersectionnew(re,add1WeekToDate(dateR));
    				Date theDate =getDatefromDate(theDatefull);
    				Time theStartTime = getTimefromDate(theDatefull);
    				Time theEndTime = addDurationtoTime(theDatefull,re.getReun_heureF().getTime()-re.getReun_heureD().getTime());
    				
    				re.setReun_date(addOneDayToDate(theDate));
    				re.setReun_heureD(theStartTime);
    				re.setReun_heureF(theEndTime);
    				reunionrepository.save(re); 
    		
                }
    	}
    	else throw new Exception("La reunion est déja passé vous ne pouver pas l'annuler pourtant vous pouver l'effacer de l'historique");
    				
    }
	
	//getUpcomingReunionByJardinn()
    @Override
    public List<Reunion> getUpcomingReunionByJardinn() {
    	UserApp user = userservices.currentUserJsf();
    	KinderGarten jardin = user.getKindergarten();
    	
    return reunionrepository.getUpcomingReunionByJardinn(jardin);
    }
	
	
    @Override
    public List<Reunion> getOldReunionByJardin() {
    	UserApp user = userservices.currentUserJsf();
    	KinderGarten jardin = user.getKindergarten();
    	
    return reunionrepository.getOldReunionByJardin(jardin);
    }
    @Override
    public List<Reunion> getTodayReunionByJardin() {
    	UserApp user = userservices.currentUserJsf();
    	KinderGarten jardin = user.getKindergarten();
    	
    return reunionrepository.getTodayReunionByJardin(jardin);
    }
    @Override
    public List<Reunion> getWeekReunionByJardin() {
    	UserApp user = userservices.currentUserJsf();
    	KinderGarten jardin = user.getKindergarten();
    	
    return reunionrepository.getWeekReunionByJardin(jardin);
    }
    @Override
    public List<Reunion> getMonthReunionByJardin() {
    	UserApp user = userservices.currentUserJsf();
    	KinderGarten jardin = user.getKindergarten();
    	
    return reunionrepository.getMonthReunionByJardin(jardin);
    }
	//affecterParentAReunion
    public void affecterParentAReunion(long id, int reId) throws Exception {

		Reunion reunionEntity = reunionrepository.findById(reId).get();
	Parent parentEntity = parentrepository.findById(id).get();
		
         Date currentdaytime = getTodayDateAndTime();
		 Date Reunionstart = combineDateTime(reunionEntity.getReun_date(),reunionEntity.getReun_heureD()); 
		
	if (parentEntity.getReunionsP().contains(reunionEntity) )
	{ throw new Exception("Le pardent avec l'id "+ id+" est deja affecté au reunion d'id"+reId);
	
	}
	else {
		if (reunionEntity.getReun_nbrparticipant()< reunionEntity.getReun_nbrplace() )
		{
			if ( (Reunionstart.after(currentdaytime))){
			reunionEntity.setReun_nbrparticipant(reunionEntity.getReun_nbrparticipant()+1);
			reunionrepository.save(reunionEntity);			
	
		if(parentEntity.getReunionsP() == null){

			List<Reunion> reun = new ArrayList<>();
			reun.add(reunionEntity);
			parentEntity.setReunionsP(reun);
			parentrepository.save(parentEntity);
		}else{

			parentEntity.getReunionsP().add(reunionEntity);
			parentrepository.save(parentEntity);

		}
		
		
			}else {		System.out.println("La date de debut de reunion est déja passé" );

				throw new Exception("La date de debut de reunion est déja passé");
			}
		}else {System.out.println("le nbr total de place est atteint" );
			throw new Exception("le nbr total de place est atteint");
		}
		}

}
	
    public void desaffecterParentduReunion(long id, int reId) throws Exception
    {	
    Parent parentEntity = parentrepository.findById(id).get();
    Reunion reunionEntity = reunionrepository.findById(reId).get();
    int exist =0;
    	int participantNb = parentEntity.getReunionsP().size();
    	for(int index = 0; index < participantNb; index++){
    		if(parentEntity.getReunionsP().get(index).getReun_id() == reId){
    			parentEntity.getReunionsP().remove(index);
    			reunionEntity.setReun_nbrparticipant(reunionEntity.getReun_nbrparticipant()-1);
    			exist ++;
    			break;//a revoir
    		}
    	}
    	if (exist ==1){
    		parentrepository.save(parentEntity);
    	reunionrepository.save(reunionEntity);	
    	}else throw new Exception ("Le pardent avec l'id "+ id+" n'est pas affecté au reunion d'id"+reId);

    }
	
	
	
	
	
	
	
    
    
    
    
   /////////////////////////////parent /////////////////////////////parent/////////////////////////parent
    public List<Reunion> getAllReunionByParent() {
    	UserApp user = userservices.currentUserJsf();
		Parent parent = user.getParent();
    	
    return reunionrepository.getAllReunionByParentt(parent);
    }
    
    @Override
    public void ajouterfb(Reunion_feedback rec) throws Exception {
    	
    	Calendar cal = Calendar.getInstance();
    	   cal.add(Calendar.DATE, +1);
    	String date1= new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    	Date ajoutDate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
        Reunion reun = reunionrepository.findById(rec.getReunion().getReun_id()).get();
    	if ( reun.getReun_date().before(ajoutDate)) {
    	rec.setReunion(reun);
    	rec.setReunfb_date(ajoutDate);

    	rfbpository.save(rec);
    	} 
    	else 
    	{ throw new Exception("Vous ne peuvez pas envoyez des feedback avant la reunion");
    	}	
    }
    
    
    @Override
    public List<Reunion_feedback> getReunfbByReunETParent(int reun_idattach, Parent p) {
    	return reunionrepository.getReunfbByReunETParent(reun_idattach,p);
    }
	
    @Override
    public List<Reunion_feedback> getReunfbByReun(int reun_idattach) {
    	return reunionrepository.getReunfbByReun(reun_idattach);
    }
    @Override
    public Parent getParentByFbID(int idfb) {
    	return reunionrepository.getParentByFbID(idfb);
    }
    @Override
    public int getCountfbByRdv(int reun_idattach) {
    	return reunionrepository.getCountfbByRdv(reun_idattach);
    }
	
	
	
////////////////////11
@Override
public int creerReunion1(Reunion re,HttpServletRequest request) throws Exception {
	/*UserApp connecteduser = sessionservice.session(request);
	KinderGarten jardin = connecteduser.getKindergarten();
	Reunion ren = new Reunion();
SimpleDateFormat df = new SimpleDateFormat("HH:mm");
Date currentdaytime = getTodayDateAndTime();
Date Reunionstart = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureD())); 

if (Reunionstart.after(currentdaytime)){
if ( re.getJardin()==null){
ren.setJardin(jardin);
}
else ren.setJardin(re.getJardin());
int s=0;

List<Reunion> lr = reunionrepository.getReunionsBydate(jardin,re.getReun_date());
Date debutR = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureD())); 
Date finR = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureF()));
if (!(debutR.compareTo(finR)==-1))
throw new Exception("verifier le temps svp");

if (!(lr.isEmpty())){

for(int index = 0; index < lr.size(); index++){
Date debutRE = combineDateTime(lr.get(index).getReun_date(),df.parse(lr.get(index).getReun_heureD()));
Date finRE = combineDateTime(lr.get(index).getReun_date(),df.parse(lr.get(index).getReun_heureF()));
if (! ((debutR.compareTo(debutRE)== -1 && finR.compareTo(debutRE)<=0 ) || (debutR.compareTo(finRE)>=0 && finR.compareTo(finRE)>0 )) )
s=s+1;

}
}
if (s == 0) {
ren.setReun_nbrplace(re.getReun_nbrplace());
ren.setReun_title(re.getReun_title());
ren.setReun_nbrparticipant(0);
ren.setReun_description(re.getReun_description());
ren.setReun_date(re.getReun_date());
ren.setReun_heureD(re.getReun_heureD());
ren.setReun_heureF(re.getReun_heureF());
ren.setReun_type(re.getReun_type());
reunionrepository.save(ren);
}
else throw new Exception("Vous avez deja une reunion qui se deroule");
} else throw new Exception("Merci d'entrer une nouvelle date");
*/
return re.getReun_id();	

}
////////
public void deleteReunionById(int idRe) throws Exception {
	Reunion re = reunionrepository.findById(idRe).get();
	System.out.println("");
	int s=0 ;
	while ( !re.getReunfb().isEmpty() && s < re.getReunfb().size())
	{ 
	  int i = re.getReunfb().get(s).getReunfb_id();
		System.out.println("i a effacer"+i);

	  Reunion_feedback rf =  rfbpository.findById(i).get();
		System.out.println(rf.getReunfb_id());

	  rfbpository.deleteById(rf.getReunfb_id());

      s++;
	}
	
	int x=0;
	while ( !re.getParents().isEmpty() && x <re.getParents().size())
	{ long i = re.getParents().get(x).getId();
	  desaffecterParentduReunion(i, idRe); 
      x++;
	}
	
	reunionrepository.delete(re);
}



	
	///////////////
	//////////////
	//////////////
/*	}*/
	///////////////
	//////////////
	/////////////////22
/*	public void affecterParentAReunion(long id, int reId) throws Exception {
		
		Date date = new Date();
		
				Reunion reunionEntity = reunionrepository.findById(reId).get();
				Parent parentEntity = parentrepository.findById(id).get();
				
				SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		         Date currentdaytime = getTodayDateAndTime();
				 Date Reunionstart = combineDateTime(reunionEntity.getReun_date(),df.parse(reunionEntity.getReun_heureD())); 
				
			if (parentEntity.getReunionsP().contains(reunionEntity) )
			{ throw new Exception("Le pardent avec l'id "+ id+" est deja affecté au reunion d'id"+reId);
			
			}
			else {
				if (reunionEntity.getReun_nbrparticipant()< reunionEntity.getReun_nbrplace() )
				{
					if ( (Reunionstart.after(currentdaytime))){
					reunionEntity.setReun_nbrparticipant(reunionEntity.getReun_nbrparticipant()+1);
					reunionrepository.save(reunionEntity);			
			
				if(parentEntity.getReunionsP() == null){

					List<Reunion> reun = new ArrayList<>();
					reun.add(reunionEntity);
					parentEntity.setReunionsP(reun);
					parentrepository.save(parentEntity);
				}else{

					parentEntity.getReunionsP().add(reunionEntity);
					parentrepository.save(parentEntity);

				}
				
				
					}else throw new Exception("La date de debut de reunion est déja passé");
				}else {
					throw new Exception("le nbr total de place est atteint");
				}
				}
				
	}*/

	/////////////////
	////////////////
	/////////////////33
	/**/
	
	////////////////////
	///////////////////
	//////////////////55
	public void updateReunionold(int ReId,Reunion ren) throws Exception {
	/*	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date now=getTodayDateAndTime();

		Reunion re = reunionrepository.findById(ReId).get();
		
		Date finROld = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureF()));
		if (finROld.compareTo(now)>=0){

		Date dateROld = re.getReun_date();
		int s= getNbrReunionsBydate(re,ren);

		Date debutR = combineDateTime(ren.getReun_date(),df.parse(ren.getReun_heureD())); 
		Date finR = combineDateTime(ren.getReun_date(),df.parse(ren.getReun_heureF()));
		if (!(debutR.compareTo(finR)==-1))
		throw new Exception("verifier le temps svp");
		

		 
		if ( ren.getReun_type() == TypeReunion.EXCEPTIONNEL)
		{
			if(re.getReun_type() == TypeReunion.EXCEPTIONNEL){

				if (s==0) setupdates ( re, ren);
				else throw new Exception("Vous avez deja une reunion qui se deroule");
				
			}else {
				if (re.getReun_type() == TypeReunion.MENSUEL){
					Date theDate = AvoidIntersection(re,add1MonthToDate(dateROld));
					if(re.getEtat_R()==0)
					re.setReun_date(theDate);
					reunionrepository.save(re);
				}
				else if (re.getReun_type() == TypeReunion.HEBDOMADAIRE){
					Date theDate = AvoidIntersection(re,add1WeekToDate(dateROld));
					if(re.getEtat_R()==0)
					re.setReun_date(theDate);
					reunionrepository.save(re);
				}
			creerReunion12(ren,re.getJardin());
			int reid = getReun_idOrdered(re.getJardin()).get(0);
            if (re.getParents().size()!=0 ){
			for ( int i=0 ; i<re.getParents().size(); i++ ){
				affecterParentAReunion( re.getParents().get(i).getId(), reid);
			}}
			
			}
		
		}
		else if (ren.getReun_type() == TypeReunion.MENSUEL || ren.getReun_type() == TypeReunion.HEBDOMADAIRE){

			if (s == 0) {
				setupdates (re,ren);
			}
			else throw new Exception("Vous avez deja une reunion qui se deroule");
		}
		 }
		else throw new Exception("Cette reunion est déja faite impossible de le modifier");
			*/
	}
	
	public void setupdates (Reunion re,Reunion ren){
		re.setReun_date(ren.getReun_date());
		re.setReun_description(ren.getReun_description());
		re.setReun_nbrplace(ren.getReun_nbrplace());
		re.setReun_title(ren.getReun_title());
		re.setReun_heureD(ren.getReun_heureD());
		re.setReun_heureF(ren.getReun_heureF());
		re.setReun_type(ren.getReun_type());
		reunionrepository.save(re);
	}
	//////////////////
	//////////////////
	/////////////////
	public List<Reunion> getAllReunionByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return reunionrepository.getAllReunionByJardinn(jardin);
	}

	public List<Reunion> getAllReunionByParent(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();	
		return reunionrepository.getAllReunionByParentt(parent);
	}

	/////////////////////
	/////////////////////
	
	/////////////////////////
	////////////////////////
	////////////////////////
	
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

	//////////////////////////
	/////////////////////////
	/////////////////////////44
	@Override
	public void RenouvellementReunion(int reId) throws Exception {
		
	}
	
	private List<Integer> getReun_idOrdered(KinderGarten jardin)
	{
		List<Integer> l = reunionrepository.getReun_idOrdered(jardin);
		return l;
	}

	///66
	@Override
	public void annulerReunionById(int idRe) throws Exception {
		/*Reunion re = reunionrepository.findById(idRe).get();
		 Date dateR = re.getReun_date();
		 SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		 Date finR = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureF()));		
		Calendar cal = Calendar.getInstance();
		String date1= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cal.getTime());
		Date now=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date1);
		
		if (now.compareTo(finR) <0 ){
			 if (re.getReun_type() == TypeReunion.EXCEPTIONNEL ){
				 deleteReunionById(idRe);
			 }
		 if (re.getReun_type() == TypeReunion.MENSUEL ){
				Calendar c = Calendar.getInstance(); 
				c.setTime(dateR); 
				c.add(Calendar.MONTH, 1);
				Date theDate = AvoidIntersection(re,c.getTime());
				re.setReun_date(theDate);
				updateReunion(idRe, re);		
				}
		 else if ( re.getReun_type() == TypeReunion.HEBDOMADAIRE){
				Calendar c = Calendar.getInstance(); 
				c.setTime(dateR); 
				c.add(Calendar.DAY_OF_YEAR, 7);
				Date theDate = AvoidIntersection(re,c.getTime());
				re.setReun_date(theDate);
				updateReunion(idRe, re);		
                }
		}
		else throw new Exception("La reunion est déja passé vous ne pouver pas l'annuler pourtant vous pouver l'effacer de l'historique");
				*/	
	}
	
	@Override
	public void annulerMultiReunionById(int idRe, int nbr) throws Exception {
	/*	Date now = getTodayDateAndTime();
		Reunion re = reunionrepository.findById(idRe).get();
		 Date dateR = re.getReun_date();
		 SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		 Date finR = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureF()));
		 
		 if (now.compareTo(finR) <0 ){
			 if (re.getReun_type() == TypeReunion.EXCEPTIONNEL ){
				 deleteReunionById(idRe);
			 }
		 if (re.getReun_type() == TypeReunion.MENSUEL ){
				Calendar c = Calendar.getInstance(); 
				c.setTime(dateR); 
				for ( int i=0;i<nbr;i++){
				c.add(Calendar.MONTH, 1);
				}
				Date theDate = AvoidIntersection(re,c.getTime());
				re.setReun_date(theDate);
				updateReunion(idRe, re);		
				}
		 else if ( re.getReun_type() == TypeReunion.HEBDOMADAIRE){
				Calendar c = Calendar.getInstance(); 
				c.setTime(dateR); 
				for ( int i=0;i<nbr;i++){
				c.add(Calendar.DAY_OF_YEAR, 7);
				}
				Date theDate = AvoidIntersection(re,c.getTime());
				re.setReun_date(theDate);
				updateReunion(idRe, re);		
                }
		}
		else throw new Exception("La reunion est déja passé vous ne pouver pas l'annuler pourtant vous pouver l'effacer de l'historique");
					*/
	}

	@Override
	public List<Reunion> getOldReunionByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();	
		return reunionrepository.getOldReunionByJardin(jardin);
	}

	@Override
	public List<Reunion> getTodayReunionByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();	
		return reunionrepository.getTodayReunionByJardin(jardin);

	}

	@Override
	public List<Reunion> getUpcomingReunionByJardin(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();	
		return reunionrepository.getUpcomingReunionByJardinn(jardin);

	}

	@Override
	public List<Reunion> getTodayReunionByParent(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();	
		return reunionrepository.getTodayReunionByParent(parent);

	}

	@Override
	public List<Reunion> getUpcomingReunionByParent(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();	
		return reunionrepository.getUpcomingReunionByParent(parent);

	}

	@Override
	public List<Reunion> getOldReunionByParent(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();	
		
		return reunionrepository.getOldReunionByParent(parent);
	}
	
	public Date getTodaydate() throws ParseException{
		Calendar cal = Calendar.getInstance();
		   cal.add(Calendar.DATE, +1);
		String date1= new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		Date todaydate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		return todaydate;
	}
	
	public Date getTodayDateAndTime() throws ParseException{
		Calendar cal = Calendar.getInstance();
		  // cal.add(Calendar.DATE, +1);
		String date1= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cal.getTime());
		Date todaydate=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date1);

		return todaydate;
		
	}
	
	public Date add1MonthToDate(Date dateR) throws ParseException{
		Calendar c = Calendar.getInstance(); 
		c.setTime(dateR); 
		c.add(Calendar.MONTH, 1);

		return c.getTime();
		
	}
	public Date add1WeekToDate(Date dateR) throws ParseException{
		Calendar c = Calendar.getInstance(); 
		c.setTime(dateR); 
		c.add(Calendar.DAY_OF_YEAR, 7);
		return c.getTime();
		
	}
	public Date add1DayToDate(Date dateR) throws ParseException{
		Calendar c = Calendar.getInstance(); 
		c.setTime(dateR); 
		c.add(Calendar.DATE, 1);
		return c.getTime();
		
	}
	//551
	public int getNbrReunionsBydate (Reunion re, Reunion ren) throws ParseException{
		/*SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        int s=0;
        Date debutR = combineDateTime(ren.getReun_date(),df.parse(ren.getReun_heureD())); 
		 Date finR = combineDateTime(ren.getReun_date(),df.parse(ren.getReun_heureF()));
	
		//List<Reunion> lr = reunionrepository.getReunionsBydate(re.getJardin(),ren.getReun_date());
	    List<Reunion> lr = reunionrepository.getReunionsBydateDestinct(re.getReun_id(),re.getJardin(),ren.getReun_date());

		if (!(lr.isEmpty())){

		for(int index = 0; index < lr.size(); index++){
			Date debutRE = combineDateTime(lr.get(index).getReun_date(),df.parse(lr.get(index).getReun_heureD()));
			Date finRE = combineDateTime(lr.get(index).getReun_date(),df.parse(lr.get(index).getReun_heureF()));
            if (! ((debutR.compareTo(debutRE)== -1 && finR.compareTo(debutRE)<=0 ) || (debutR.compareTo(finRE)>=0 && finR.compareTo(finRE)>0 )) )
            s=s+1;
            
           }
		
	}*/
		return 0;

	}
	@Override
	public void ReportReunionFromDateUntilDate(String date, String date2,HttpServletRequest request) throws Exception {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();	
		Date dateDR=new SimpleDateFormat("yyyy-MM-dd").parse(date);
		Date dateFR=new SimpleDateFormat("yyyy-MM-dd").parse(date2);
		List<Reunion> l = reunionrepository.getUpcomingReunionFromDateUntildate(jardin,dateDR,dateFR);
		for (int i=0;i<l.size();i++){
			annulerReunionById(l.get(i).getReun_id());
		}
		
	}
	
	public Date AvoidIntersection(Reunion re, Date dateR) throws ParseException{
		/*int s = getNbrIntersectionBydate( re.getJardin(), dateR, re.getReun_heureD(),re.getReun_heureF());
		if ( s==0) return dateR ;
		else if (s!=0) { 
			while(s!=0){ 
		dateR =add1DayToDate(dateR);
		s = getNbrIntersectionBydate( re.getJardin(), dateR, re.getReun_heureD(),re.getReun_heureF());
		}}*/
		return dateR;
	}
	
	public int getNbrIntersectionBydate (KinderGarten jardin,Date dateR,String HeureD, String HeureF) throws ParseException{
	/*	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        int s=0;
        Date debutR = combineDateTime(dateR,df.parse(HeureD)); 
		 Date finR = combineDateTime(dateR,df.parse(HeureF));
	
		List<Reunion> lr = reunionrepository.getReunionsBydate(jardin,dateR);
		
		if (!(lr.isEmpty())){

		for(int index = 0; index < lr.size(); index++){
			Date debutRE = combineDateTime(lr.get(index).getReun_date(),df.parse(lr.get(index).getReun_heureD()));
			Date finRE = combineDateTime(lr.get(index).getReun_date(),df.parse(lr.get(index).getReun_heureF()));
            if (! ((debutR.compareTo(debutRE)== -1 && finR.compareTo(debutRE)<=0 ) || (debutR.compareTo(finRE)>=0 && finR.compareTo(finRE)>0 )) )
            s=s+1;
            
           }
		
	}*/
		return 0;

	}
	
	
	
	public int creerReunion12(Reunion re,KinderGarten jardin) throws Exception {
	/*	Reunion ren = new Reunion();

	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
	Date currentdaytime = getTodayDateAndTime();
	Date Reunionstart = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureD())); 

	if (Reunionstart.after(currentdaytime)){
	if ( re.getJardin()==null){
	ren.setJardin(jardin);
	}
	else ren.setJardin(re.getJardin());
	int s=0;

	List<Reunion> lr = reunionrepository.getReunionsBydate(jardin,re.getReun_date());
	Date debutR = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureD())); 
	Date finR = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureF()));
	if (!(debutR.compareTo(finR)==-1))
	throw new Exception("verifier le temps svp");

	if (!(lr.isEmpty())){

	for(int index = 0; index < lr.size(); index++){
	Date debutRE = combineDateTime(lr.get(index).getReun_date(),df.parse(lr.get(index).getReun_heureD()));
	Date finRE = combineDateTime(lr.get(index).getReun_date(),df.parse(lr.get(index).getReun_heureF()));
	if (! ((debutR.compareTo(debutRE)== -1 && finR.compareTo(debutRE)<=0 ) || (debutR.compareTo(finRE)>=0 && finR.compareTo(finRE)>0 )) )
	s=s+1;

	}
	}
	if (s == 0) {
	ren.setReun_nbrplace(re.getReun_nbrplace());
	ren.setReun_title(re.getReun_title());
	ren.setReun_nbrparticipant(0);
	ren.setReun_description(re.getReun_description());
	ren.setReun_date(re.getReun_date());
	ren.setReun_heureD(re.getReun_heureD());
	ren.setReun_heureF(re.getReun_heureF());
	ren.setReun_type(re.getReun_type());
	reunionrepository.save(ren);
	}
	else throw new Exception("Vous avez deja une reunion qui se deroule");
	} else throw new Exception("Merci d'entrer une nouvelle date");
*/
	return re.getReun_id();	

	}
	////
	////
	///////
	/*public int creerReunion(Reunion re) {
		long idjardin =3;
		User jardin = userrepository.findById(idjardin).get();
		re.setJardin(jardin);
		re.setReun_nbrparticipant(0);
		reunionrepository.save(re);
		return re.getReun_id();
	}*/
	
	
	
/*	@Scheduled(cron="0 0/2 0 ? * * *")
	public void RenewReunion() throws Exception {
		List<Reunion> listR = reunionrepository.getAllReunionNotRenewed();
		for (int i=0 ; i<listR.size();i++){
		Reunion re = reunionrepository.findById(listR.get(i).getReun_id()).get();
	     Reunion ren = new Reunion();

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		 Date finR = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureF()));
		 Date dateR = re.getReun_date();
		 
		 /////getNOW date and time
			Calendar cal = Calendar.getInstance();
		
			String date1= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cal.getTime());
			Date now=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date1);
        /////////
			if(re.getEtat_R()==0){
			if (now.compareTo(finR) >=0){
				if (re.getReun_type() == TypeReunion.MENSUEL ){
					Calendar c = Calendar.getInstance(); 
					c.setTime(dateR); 
					c.add(Calendar.MONTH, 1);
					Date theDate = AvoidIntersection(re,c.getTime());
					re.setReun_date(theDate);
					
					///
					creerReunion1(re);
					//c.add(Calendar.MONTH, -1);
					re.setReun_date(dateR);
					re.setEtat_R(1);
			    	reunionrepository.save(re);
					int reid = getReun_idOrdered(re.getJardin()).get(0);
					if (re.getParents().size()!=0){
					for ( int j=0 ; j<re.getParents().size(); j++ ){
						affecterParentAReunion((int) re.getParents().get(j).getId(), reid);
					}
					}



				
							        }
				else if ( re.getReun_type() == TypeReunion.HEBDOMADAIRE){
					Calendar c = Calendar.getInstance(); 
					c.setTime(dateR); 
					c.add(Calendar.DAY_OF_YEAR, 7);
					Date theDate = AvoidIntersection(re,c.getTime());
					re.setReun_date(theDate);					///
					creerReunion1(re);
					//c.add(Calendar.DAY_OF_YEAR, -7);
					re.setReun_date(dateR);
					re.setEtat_R(1);
			    	reunionrepository.save(re);
					int reid = getReun_idOrdered(re.getJardin()).get(0);
					if (re.getParents().size()!=0){
					for ( int j=0 ; j<re.getParents().size(); j++ ){
						affecterParentAReunion((int) re.getParents().get(j).getId(), reid);
					}
					
					}
					}}
				}
			}
	
	}*/
	
	
	//////////////////////////////////
	/////////////////////////////////////////////////
	/////////////// JSF /////////  JSF //////////

public List<Reunion> getAllReunionByJardin() {
	long idjardin=1;
	KinderGarten jardin = kgrepository.findById(idjardin).get();
	
return reunionrepository.getAllReunionByJardinn(jardin);
}


@Override
public List<Reunion> getParentbyReunion(int reun_idattach) {
	return reunionrepository.getParentbyReunion(reun_idattach);

}

@Override
public Reunion getReunionByReunID(int reun_idattach) {
	return reunionrepository.getReunionByReunID(reun_idattach);
}
@Override
public List<Parent> getParentByReunID(int reun_idattach) {
	// 
	return reunionrepository.getParentByReunID(reun_idattach);
}
@Override
public void desaffecterParentduReunion(String parent_email, int reId) throws Exception {
	Parent parentEntity = parentrepository.findByIdEmail(parent_email);
	Reunion reunionEntity = reunionrepository.findById(reId).get();
	int exist =0;
		int participantNb = parentEntity.getReunionsP().size();
		for(int index = 0; index < participantNb; index++){
			if(parentEntity.getReunionsP().get(index).getReun_id() == reId){
				parentEntity.getReunionsP().remove(index);
				reunionEntity.setReun_nbrparticipant(reunionEntity.getReun_nbrparticipant()-1);
				exist ++;
				break;//a revoir
			}
		}
		if (exist ==1){
			parentrepository.save(parentEntity);
		reunionrepository.save(reunionEntity);	
		}else throw new Exception ("Le pardent avec l'email "+ parent_email+" n'est pas affecté au reunion d'id"+reId);
}

@Override
public List<Parent> getAllParent(int reun_idattach) {
	int s=0;

	List<Parent> preturn = new ArrayList<>();
	List<Parent> p =reunionrepository.getAllParent();
	List<Parent> pr =reunionrepository.getParentByReunID(reun_idattach);
	if(!(pr.isEmpty()))
	{ 	if(!(p.isEmpty())){
		for (int i=0;i<p.size();i++){
			for(int j=0;j<pr.size();j++)
			{
			if(p.get(i).getId()==pr.get(j).getId()){
				s=s+1;
			}
		}
			if (s==0) preturn.add(p.get(i));
			s=0;
		}
		
	}
	}
	else preturn=p;
	return preturn;
}
@Override
public void ajoutreunion(Reunion r) {
	long idjardin=1;
	KinderGarten jardin = kgrepository.findById(idjardin).get();
	r.setReun_nbrparticipant(0);
	r.setJardin(jardin);

	reunionrepository.save(r);
	
}
/////////////////////////////
////////////////////////////
/////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////
///////////////jsf PLUS 




public int creerReunionAfterTest(Reunion re) throws Exception {
long idjardin =3;
Reunion ren = new Reunion();
Date currentdaytime = getTodayDateAndTime();
Date Reunionstart = combineDateTime(re.getReun_date(),re.getReun_heureD()); 

if (Reunionstart.after(currentdaytime)){
KinderGarten jardin = kgrepository.findById(idjardin).get();
if ( re.getJardin()==null){
ren.setJardin(jardin);
}
else ren.setJardin(re.getJardin());

int s=getcontrainte(re,jardin,re.getReun_date());
if (s==-1)
throw new Exception("verifier le temps svp");

else
if (s == 0) {
ren.setReun_nbrplace(re.getReun_nbrplace());
ren.setReun_title(re.getReun_title());
ren.setReun_nbrparticipant(0);
ren.setReun_description(re.getReun_description());
ren.setReun_date(re.getReun_date());
ren.setReun_heureD(re.getReun_heureD());
ren.setReun_heureF(re.getReun_heureF());
ren.setReun_type(re.getReun_type());
reunionrepository.save(ren);
}
else throw new Exception("Vous avez deja une reunion qui se deroule");
} else throw new Exception("Merci d'entrer une nouvelle date");

return re.getReun_id();	

}

public Date addOneDayToDate(Date date) throws ParseException{
	Calendar c = Calendar.getInstance(); 
	c.setTime(date); 
	c.add(Calendar.DATE, 1);
	//date = c.getTime();
	String date1= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	Date date2 =new SimpleDateFormat("yyyy-MM-dd").parse(date1);
	return date2;
}


private Time addDurationtoTime(Date theDate, long l) throws ParseException {
	Calendar cal = Calendar.getInstance();
	cal.setTime(theDate);
    cal.add(Calendar.MILLISECOND, (int) l);
    String date1= new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
	Date todaydate=new SimpleDateFormat("HH:mm:ss").parse(date1);
	Time timeValue = new Time(todaydate.getTime());
	System.out.println("addDurationtoTime timeValue: "+timeValue+" todaydate "+todaydate
			+" theDate "+theDate+" date1 "+date1 +" l "+l +" cal.getTime() "+cal.getTime());

	return timeValue;
}
//getNbrIntersectionBydate
public int getNbrIntersectionBydate (KinderGarten jardin,Date dateR,Time HeureD, Time HeureF) throws Exception{
    int s=0;
    Date debutR = combineDateTime(dateR,HeureD); 
	 Date finR = combineDateTime(dateR,HeureF);
		System.out.println("getNbrIntersectionBydate debutR :"+debutR +" finR "+finR);
		System.out.println("getNbrIntersectionBydate dateR :"+dateR);
		Time heurdjour =getHeureDdeJour(jardin,dateR);
		Time heurfjour =getHeureFdeJour(jardin,dateR);
		
		if (VerifHourInIntervale(HeureD,HeureF,heurdjour,heurfjour)==false){
			System.out.println("01234 heured "+HeureD+" heuref "+HeureF+" heurdjour "+heurdjour+" heuefjour "+heurfjour);

			s=s+1;
		}
	List<Reunion> lr = reunionrepository.getReunionsBydate(jardin,dateR);
	System.out.println("***get reunion pour la date "+dateR+" "+lr);

	if (!(lr.isEmpty())){
		System.out.println("getNbrIntersectionBydate lr"+lr.get(0).getReun_id());
		
	for(int index = 0; index < lr.size(); index++){
		
		Date debutRE = combineDateTime(dateR,lr.get(index).getReun_heureD());
		Date finRE = combineDateTime((dateR),lr.get(index).getReun_heureF());
		System.out.println("2getNbrIntersectionBydate debutR :"+debutR +" finR "+finR+" avec debutRE " +debutRE+" avec finRE " +finRE);

        if (! ((debutR.compareTo(debutRE)== -1 && finR.compareTo(debutRE)<=0 ) || (debutR.compareTo(finRE)>=0 && finR.compareTo(finRE)>0 )) )
        s=s+1;
        
       }
	
}
	return s;

}
//getNbrIntersectionBydateForupdate
public int getNbrIntersectionBydateForupdate (KinderGarten jardin,Date dateR,Time HeureD, Time HeureF,Time HeureDold) throws Exception{
    int s=0;
    Date debutR = combineDateTime(dateR,HeureD); 
	 Date finR = combineDateTime(dateR,HeureF);
		System.out.println("getNbrIntersectionBydate debutR :"+debutR +" finR "+finR);
		System.out.println("getNbrIntersectionBydate dateR :"+dateR);
		Time heurdjour =getHeureDdeJour(jardin,dateR);
		Time heurfjour =getHeureFdeJour(jardin,dateR);
		
		if (VerifHourInIntervale(HeureD,HeureF,heurdjour,heurfjour)==false){
			System.out.println("01234 heured "+HeureD+" heuref "+HeureF+" heurdjour "+heurdjour+" heuefjour "+heurfjour);

			s=s+1;
		}
	List<Reunion> lr = reunionrepository.getReunionsBydate(jardin,dateR);
	System.out.println("***get reunion pour la date "+dateR+" "+lr);
	if (!(lr.isEmpty())){
		for (int i = 0; i< lr.size(); i++){
			System.out.println("***get reunion pour la date "+dateR+" "+lr.get(i).getReun_heureD()+ " old "+HeureDold);

			if (combineDateTime(dateR,lr.get(i).getReun_heureD()).compareTo(combineDateTime(dateR,HeureDold))==0) {
				lr.remove(i);
				break;
			}
		}
	}

	if (!(lr.isEmpty())){
		System.out.println("getNbrIntersectionBydate lr"+lr.get(0).getReun_id());
		
	for(int index = 0; index < lr.size(); index++){
		
		Date debutRE = combineDateTime(dateR,lr.get(index).getReun_heureD());
		Date finRE = combineDateTime((dateR),lr.get(index).getReun_heureF());
		System.out.println("2getNbrIntersectionBydate debutR :"+debutR +" finR "+finR+" avec debutRE " +debutRE+" avec finRE " +finRE);

        if (! ((debutR.compareTo(debutRE)== -1 && finR.compareTo(debutRE)<=0 ) || (debutR.compareTo(finRE)>=0 && finR.compareTo(finRE)>0 )) )
        s=s+1;
        
       }
	
}
	return s;

}

public Time getTimefromDate(Date theDatefull) throws ParseException {	
	Time timeValue = new Time(theDatefull.getTime());

	return timeValue;
}

public Time addoneHour (Time heure) throws ParseException{
	Calendar c = Calendar.getInstance(); 
	c.setTime(heure); 
	c.add(Calendar.HOUR_OF_DAY, 1);
	Date d = c.getTime();
	System.out.println("try addoneHour d:"+" "+c.getTime());

	
	Time timeValue = new Time(d.getTime());
	System.out.println("try addoneHour timeValue: "+ timeValue);


  //  System.out.println(heureReturn);

	return timeValue;
}

private Time getHeureDdeJour(KinderGarten jardin,Date date) throws Exception {
	Calendar c = Calendar.getInstance(); 
	c.setTime(date); 
	int i =c.get(Calendar.DAY_OF_WEEK)-1;
	if (c.get(Calendar.DAY_OF_WEEK)==1) i=7;

   System.out.println("get heure de jour de "+i +" pour la date :"+date);

	Time l = null;
	if (i==7){
	 l = reunionrepository.getHeureD(jardin,Lesjours.DIMANCHE);
	} else if (i==1){
		 l = reunionrepository.getHeureD(jardin,Lesjours.LUNDI);
		}
	else if (i==2){
		 l = reunionrepository.getHeureD(jardin,Lesjours.MARDI);
		}
	else if (i==3){
		 l = reunionrepository.getHeureD(jardin,Lesjours.MERCREDI);
		}
	else if (i==4){
		 l = reunionrepository.getHeureD(jardin,Lesjours.JEUDI);
		} else 
			if (i==5){
	l = reunionrepository.getHeureD(jardin,Lesjours.VENDREDI);
				}else 
					if (i==6){
						l = reunionrepository.getHeureD(jardin,Lesjours.SAMEDI);
						 }
	   System.out.println("heure debut de jour "+l);

return l;  
}
	
private Time getHeureFdeJour(KinderGarten jardin,Date date) throws Exception {
	Calendar c = Calendar.getInstance(); 
	c.setTime(date); 
	int i =c.get(Calendar.DAY_OF_WEEK)-1;
	if (c.get(Calendar.DAY_OF_WEEK)==1) i=7;

   System.out.println("getHeureFdeJour pour le jour  " +i+"pour la date : "+ date);

	Time l = null;
	if (i==7){
	 l = reunionrepository.getHeureF(jardin,Lesjours.DIMANCHE);
	} else if (i==1){
		 l = reunionrepository.getHeureF(jardin,Lesjours.LUNDI);
		}
	else if (i==2){
		 l = reunionrepository.getHeureF(jardin,Lesjours.MARDI);
		}
	else if (i==3){
		 l = reunionrepository.getHeureF(jardin,Lesjours.MERCREDI);
		}
	else if (i==4){
		 l = reunionrepository.getHeureF(jardin,Lesjours.JEUDI);
		} else 
			if (i==5){
	l = reunionrepository.getHeureF(jardin,Lesjours.VENDREDI);
				}else 
					if (i==6){
						l = reunionrepository.getHeureF(jardin,Lesjours.SAMEDI);
						 }
	   System.out.println("getHeureFdeJour "+l);

return l;    
	
}
public Time MinusoneHour (Time heure) throws ParseException{
	Calendar c = Calendar.getInstance(); 
	c.setTime(heure); 
	c.add(Calendar.HOUR_OF_DAY, -1);
	Date d = c.getTime();
	System.out.println("tryMinusoneHour"+" c.getTime() "+c.getTime()+" d "+d);

	
	Time timeValue = new Time(d.getTime());


  System.out.println("tryMinusoneHour: Time value "+" "+timeValue);

	return timeValue;
}

private int veriftempsReun(KinderGarten jardin,Date date, Time heure) throws Exception {
	Calendar c = Calendar.getInstance(); 
	c.setTime(date); 
	
	int i =c.get(Calendar.DAY_OF_WEEK)-1;
	//
	if (c.get(Calendar.DAY_OF_WEEK)==1) i=7;
	 //
    System.out.println("veriftempsReun de jour "+i + " pour la date"+date + " pour l'heure "+heure);

	List<Time> l = new ArrayList<>();
	if (i==7){
	 l.add(reunionrepository.getHeureD(jardin,Lesjours.DIMANCHE));
	 l.add(reunionrepository.getHeureF(jardin,Lesjours.DIMANCHE));

	} else if (i==1){
		 l.add(reunionrepository.getHeureD(jardin,Lesjours.LUNDI));
		 l.add(reunionrepository.getHeureF(jardin,Lesjours.LUNDI));

		}
	else if (i==2){
		 l.add(reunionrepository.getHeureD(jardin,Lesjours.MARDI));
		 l.add(reunionrepository.getHeureF(jardin,Lesjours.MARDI));
		}
	else if (i==3){
		l.add(reunionrepository.getHeureD(jardin,Lesjours.MERCREDI));
		 l.add(reunionrepository.getHeureF(jardin,Lesjours.MERCREDI));			
		 }
	else if (i==4){
		l.add(reunionrepository.getHeureD(jardin,Lesjours.JEUDI));
		 l.add(reunionrepository.getHeureF(jardin,Lesjours.JEUDI));		
		 } else 
			if (i==5){
				l.add(reunionrepository.getHeureD(jardin,Lesjours.VENDREDI));
				 l.add(reunionrepository.getHeureF(jardin,Lesjours.VENDREDI));			
				 }else 
			if (i==6){
							l.add(reunionrepository.getHeureD(jardin,Lesjours.SAMEDI));
							 l.add(reunionrepository.getHeureF(jardin,Lesjours.SAMEDI));			
							 }
    

    System.out.println("veriftempsReun: les heures " +l);
    if (l.get(0)==null) return 2;
    else {
    Date reunstart = combineDateTime(date,heure); 
	Date acceptReunstart = combineDateTime(date,l.get(0)); 
	Date  acceptReunstop = combineDateTime(date,l.get(1)); 
    
	if (reunstart.compareTo(acceptReunstart) >=0 && reunstart.compareTo(acceptReunstop) <0){
		return 0;
    }		
	else return 1;

    }
}

public Boolean VerifHourInIntervale (Time ReunStartT,Time ReunEndT,Time heuredj,Time heurefj) throws ParseException{
	Date ReunStartTime = ReunStartT;
    System.out.println( "VerifHourInIntervale " +ReunStartTime);

	Date ReunEndTime = ReunEndT;
    System.out.println("VerifHourInIntervale " +ReunEndTime);

	Date heuredjour = heuredj;
    System.out.println("VerifHourInIntervale " +heuredjour);

	Date heurefjour = heurefj;
    System.out.println("VerifHourInIntervale " +heurefjour);

	if (ReunStartTime.compareTo(heuredjour) >=0 && ReunEndTime.compareTo(heurefjour) <=0)
      return true;
	else

	return false;
	
}
//getcontraintenew
public int getcontraintenew(Reunion re,KinderGarten jardin,Date d) throws ParseException{
	int s =0;
	List<Reunion> lr = reunionrepository.getReunionsBydate(jardin,d);
	Date debutR = combineDateTime(re.getReun_date(),addoneHour(re.getReun_heureD())); 
	Date finR = combineDateTime(re.getReun_date(),addoneHour(re.getReun_heureF()));
	if (!(lr.isEmpty())){
		for(int index = 0; index < lr.size(); index++){
		Date debutRE = combineDateTime(d,lr.get(index).getReun_heureD());
		Date finRE = combineDateTime(d,lr.get(index).getReun_heureF());
		if (! ((debutR.compareTo(debutRE)== -1 && finR.compareTo(debutRE)<=0 ) || (debutR.compareTo(finRE)>=0 && finR.compareTo(finRE)>=0 )) )
		s=s+1;
		}
		}
	if (!(debutR.compareTo(finR)==-1)) s=-1;
	return s ;
}


//AvoidIntersectionnew
public Date AvoidIntersectionnew(Reunion re ,Date dateR) throws Exception{
	List<Reunion> l = new ArrayList<>();
	while( getHeureDdeJour(re.getJardin(),dateR) == null)
	{
		dateR = add1DayToDate(dateR);
	}
	Time heurdjour =getHeureDdeJour(re.getJardin(),dateR);
	Time heurfjour =getHeureFdeJour(re.getJardin(),dateR);
	System.out.println("re.getReun_heureD() "+re.getReun_heureD());

	int s = getNbrIntersectionBydate( re.getJardin(), dateR, re.getReun_heureD(),re.getReun_heureF());
	if ( s==0) {
        dateR =combineDateTime(dateR, re.getReun_heureD());
		System.out.println("AvoidIntersectionnew NOO intersection "+dateR +" "+ re.getReun_heureD() );

		return dateR ;
	}
	else if (s!=0) { 
		while(s!=0){ 
			 l = reunionrepository.getReunionsBydate(re.getJardin(), dateR);
			 if (!(l.isEmpty())){
				for(int index = 0; index < l.size(); index++){
 					System.out.println(" "+l.get(index).getReun_id()+ " index "+index);
				}
			 
			 int longueur = l.size();
				Reunion tampon = l.get(0);
				boolean permut;
		 
				do {
					// hypothèse : le tableau est trié
					permut = false;
					for (int i = 0; i < longueur - 1; i++) {
						// Teste si 2 éléments successifs sont dans le bon ordre ou non
						if (combineDateTime(dateR,l.get(i).getReun_heureD()).compareTo(combineDateTime(dateR,l.get(i+1).getReun_heureD()))>0) {
							// s'ils ne le sont pas, on échange leurs positions
							tampon = l.get(i);
							l.set(i, l.get(i+1)) ;
							l.set(i+1,tampon ) ;
							permut = true;
						}
					}
				} while (permut); 
			 }
				System.out.println("AvoidIntersectionnew l: "+l+" dateR "+dateR);
				if ((l.isEmpty())){
                	dateR =combineDateTime(dateR, heurdjour);
					System.out.println("555AvoidIntersectionnew l: "+dateR);

                    s=0;
                    break;
				} else {

					for(int index = 0; index < l.size(); index++){
						
	 					System.out.println(" "+l.get(index).getReun_id()+ " index "+index);

	 					
                    if ((l.get(0).getReun_heureD().getTime() -heurdjour.getTime())>=(re.getReun_heureF().getTime() - re.getReun_heureD().getTime()) ){
                   // ken fama espace bin el heuredebutde jour w awel reunion fel liste
                    	//////////////////
                    	if (!((l.get(0).getReun_heureD().getTime() -heurdjour.getTime())>86400000))
                    	{
                    	dateR =combineDateTime(dateR, heurdjour);
					System.out.println("000AvoidIntersectionnew l: loula "+l.get(0).getReun_heureD()+" heurdjour"+heurdjour
							+" re.heref "+re.getReun_heureF()+" re.heurd"+re.getReun_heureD()
							);

                    s=0;
                    break;
                    	}
                    }
                    if (index < l.size()-1) {
                    	//ken fama espace bin 2 reunions fel liste
                        if ((l.get(index+1).getReun_heureD().getTime() -l.get(index).getReun_heureF().getTime())>=(re.getReun_heureF().getTime() - re.getReun_heureD().getTime()) )
                        {
                        	if (!((l.get(index+1).getReun_heureD().getTime() -l.get(index).getReun_heureF().getTime())>86400000))
                        	{
        					System.out.println("111AvoidIntersectionnew l.get(index): "+l.get(index).getReun_heureF()+ " l.get(index+1)" +l.get(index+1).getReun_heureD()
        							+" reheuref "+re.getReun_heureF()+" reheured "+re.getReun_heureD()
        							+" math1 "+(l.get(index+1).getReun_heureD().getTime() -l.get(index).getReun_heureF().getTime())
        							+" math2 " +(re.getReun_heureF().getTime() - re.getReun_heureD().getTime())
        							+" math3 " + (l.get(index).getReun_heureF().getTime() -l.get(index+1).getReun_heureD().getTime())
        							+ " math4 "+ (re.getReun_heureD().getTime() - re.getReun_heureF().getTime()));
                        	dateR =combineDateTime(dateR, l.get(index).getReun_heureF());
                        	s=0;
                        	break;
                        	}
                        }
                    } if (((heurfjour.getTime() - l.get(index).getReun_heureF().getTime())>=(re.getReun_heureF().getTime() - re.getReun_heureD().getTime())) && (index ==(l.size()-1)))
                    {
                    	// ken fama espace bin ekher element fel liste wel herefin de jour
                    	
                    	if (!((heurfjour.getTime() - l.get(index).getReun_heureF().getTime())>86400000))
                    	{
    					System.out.println("222AvoidIntersectionnew heurfjour: "+heurfjour + "eliwraha "+l.get(index).getReun_heureF()
    							+" reheuref "+re.getReun_heureF()+" reheured "+re.getReun_heureD());

                    	dateR =combineDateTime(dateR, l.get(index).getReun_heureF());
                    	s=0;
                    	break;
                    	}
                    } /*else {
                        index=-1;
                		dateR =add1DayToDate(dateR);
                		System.out.println("333AvoidIntersectionnew dateR:"+dateR);
                        s=1;
       				 l = reunionrepository.getReunionsBydate(re.getJardin(), dateR);
                         heurdjour =getHeureDdeJour(re.getJardin(),dateR);
                		 heurfjour =getHeureFdeJour(re.getJardin(),dateR);
                 		System.out.println("444AvoidIntersectionnew new valeur :"+heurdjour +" "+heurfjour);
                 	
                    }*/
	}
				if (s!=0){
					
					dateR=add1DayToDate(dateR);
					while (getHeureDdeJour(re.getJardin(),dateR) ==null)
					{
						dateR=add1DayToDate(dateR);

					}
					System.out.println("add1DayToDate in avoidintersectionl: "+dateR);

					l = reunionrepository.getReunionsBydate(re.getJardin(), dateR);
                    heurdjour =getHeureDdeJour(re.getJardin(),dateR);
           		    heurfjour =getHeureFdeJour(re.getJardin(),dateR);
 					System.out.println("last in annuler heurdjour"+heurdjour + " heurfjour "+heurfjour+ " dateR "+dateR);
 					 if (!(l.isEmpty())){
 					for(int index = 0; index < l.size(); index++){
	 					System.out.println("last in annuler heurdjour "+l.get(index).getReun_id()+ " index "+index);
					}
 					 }
				}
				}
				}
					}
	
	//long difference = dateR.getTime() - dateR.getTime();
	
	return dateR;
}
public Date getDatefromDate(Date theDatefull) throws ParseException {
	
	String date1= new SimpleDateFormat("yyyy-MM-dd").format(theDatefull.getTime());
	Date todaydate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
	System.out.println("getDatefromDate fulldatevalue: "+theDatefull+" datevalue "+todaydate + " with gettime "+ todaydate.getTime());
	return todaydate;
}

public int getcontrainte(Reunion re,KinderGarten jardin,Date d){
	int s =0;
	List<Reunion> lr = reunionrepository.getReunionsBydate(jardin,d);
	Date debutR = combineDateTime(re.getReun_date(),re.getReun_heureD()); 
	Date finR = combineDateTime(re.getReun_date(),re.getReun_heureF());
	if (!(lr.isEmpty())){
		for(int index = 0; index < lr.size(); index++){
		Date debutRE = combineDateTime(d,lr.get(index).getReun_heureD());
		Date finRE = combineDateTime(d,lr.get(index).getReun_heureF());
		if (! ((debutR.compareTo(debutRE)== -1 && finR.compareTo(debutRE)<=0 ) || (debutR.compareTo(finRE)>=0 && finR.compareTo(finRE)>0 )) )
		s=s+1;
		}
		}
	if (!(debutR.compareTo(finR)==-1)) s=-1;
	return s ;
}


///////////// /reporter


public int creationReunion(Reunion re) {
	Reunion ren = new Reunion();
	long idjardin =1;

	KinderGarten jardin = kgrepository.findById(idjardin).get();

	ren.setJardin(jardin);
	ren.setReun_nbrplace(re.getReun_nbrplace());
	ren.setReun_title(re.getReun_title());
	ren.setReun_nbrparticipant(0);
	ren.setReun_description(re.getReun_description());
	ren.setReun_date(re.getReun_date());
	ren.setReun_heureD(re.getReun_heureD());
	ren.setReun_heureF(re.getReun_heureF());
	ren.setReun_type(re.getReun_type());
	reunionrepository.save(ren);
	return re.getReun_id();
}
public Date justdate(Date dateR) throws ParseException{
	Calendar c = Calendar.getInstance(); 
	c.setTime(dateR); 
	c.add(Calendar.HOUR_OF_DAY, 1);

	return c.getTime();
	
}








@Override
public String getAlphaNumericString(int n) {
	// chose a Character random from this String 
    String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                + "0123456789"
                                + "abcdefghijklmnopqrstuvxyz"; 

    // create StringBuffer size of AlphaNumericString 
    StringBuilder sb = new StringBuilder(n); 

    for (int i = 0; i < n; i++) { 

        // generate a random number between 
        // 0 to AlphaNumericString variable length 
        int index 
            = (int)(AlphaNumericString.length() 
                    * Math.random()); 

        // add Character one by one in end of sb 
        sb.append(AlphaNumericString 
                      .charAt(index)); 
    } 

    return sb.toString(); 
}
@Override
public String getDescriptionByReun_id(int reun_idattach) {
	return reunionrepository.getDescriptionByReun_id(reun_idattach);
}
@Override
public List<Reunion_feedback> getFbByRdv(int reun_idattach) {
	return reunionrepository.getFbByRdv(reun_idattach);
}

@Override
public List<Parent> getParentOfkindergarten(int reun_idattach) {
	int s=0;
    Reunion r = reunionrepository.findById(reun_idattach).get();
	List<Parent> preturn = new ArrayList<>();
	List<Parent> p =reunionrepository.getParentOfkindergarten(r.getJardin());
	List<Parent> pr =reunionrepository.getParentByReunID(reun_idattach);
	if(!(pr.isEmpty()))
	{ 	if(!(p.isEmpty())){
		for (int i=0;i<p.size();i++){
			for(int j=0;j<pr.size();j++)
			{
			if(p.get(i).getId()==pr.get(j).getId()){
				s=s+1;
			}
		}
			if (s==0) preturn.add(p.get(i));
			s=0;
		}
		
	}
	}
	else preturn=p;
	return preturn;	
}

//renouvellement
@Scheduled(cron = "0 * * * * ?")
public void RenouvellementReunion() throws Exception {
	/*UserApp user = userservices.currentUserJsf();
	KinderGarten jardin = user.getKindergarten();*/
	List<KinderGarten> kg = reunionrepository.getAllJardin();
	for (int k=0;k<kg.size();k++){
		
	
	List<Reunion> l = reunionrepository.getReunionToRenew(kg.get(k));
	if (!(l.isEmpty())){
	for (int i=0;i<l.size();i++){
    	System.out.println("+++" + l.get(i).getReun_id());
    	
    	}
	
	for (int i=0;i<l.size();i++){
		Reunion re = reunionrepository.findById(l.get(i).getReun_id()).get();
    	System.out.println("a " + re.getReun_id());

		if (re.getReun_type() == TypeReunion.MENSUEL ){
			Date dateR = re.getReun_date();
			Time heuredr =re.getReun_heureD() ;
			Time heurefr = re.getReun_heureF();
					
			Date theDate = AvoidIntersectionnew(re,add1MonthToDate(dateR));
			Time theStartTime = getTimefromDate(theDate);
			Time theEndTime = addDurationtoTime(theDate,re.getReun_heureF().getTime()-re.getReun_heureD().getTime());
			
			re.setReun_date(theDate);
			re.setReun_heureD(theStartTime);
			re.setReun_heureF(theEndTime);
	    	System.out.println("b " + theDate);
			creerReunion1(re,kg.get(k));
	    	System.out.println("c " );

			re.setReun_heureD(heuredr);
			re.setReun_heureF(heurefr);
			re.setReun_date(dateR);
			re.setEtat_R(1);
	    	System.out.println("d " );

	    	reunionrepository.save(re);
	    	System.out.println("f " );

			int reid = getReun_idOrdered(re.getJardin()).get(0);
	    	System.out.println("g "+ reid);
	    	System.out.println("gplus "+ re.getParents().size());

			if (re.getParents().size()!=0){
				System.out.println("h "+ re.getParents().size());
				System.out.println("AVOID INTERSECTION START"+ re.getParents().get(0).getId());

				for ( int j=0 ; j<re.getParents().size(); j++ ){
					System.out.println("i "+ j);
					Reunion reunionEntity = reunionrepository.findById(reid).get();
					Parent parentEntity = parentrepository.findById((re.getParents().get(j).getId())).get();
					reunionEntity.setReun_nbrparticipant(reunionEntity.getReun_nbrparticipant()+1);
					parentEntity.getReunionsP().add(reunionEntity);
					reunionrepository.save(reunionEntity);			
					parentrepository.save(parentEntity);
					System.out.println("j "+ j);

				}
			}
    	}
		else if ( re.getReun_type() == TypeReunion.HEBDOMADAIRE){
			Date dateR = re.getReun_date();
			Time heuredr =re.getReun_heureD() ;
			Time heurefr = re.getReun_heureF();
					
			Date theDate = AvoidIntersectionnew(re,add1WeekToDate(dateR));
			Time theStartTime = getTimefromDate(theDate);
			Time theEndTime = addDurationtoTime(theDate,re.getReun_heureF().getTime()-re.getReun_heureD().getTime());
			
			re.setReun_date(theDate);
			re.setReun_heureD(theStartTime);
			re.setReun_heureF(theEndTime);
	    	System.out.println("bb " + theDate);

			creerReunion1(re,kg.get(k));
	    	System.out.println("cc " );

			re.setReun_heureD(heuredr);
			re.setReun_heureF(heurefr);
			re.setReun_date(dateR);
			re.setEtat_R(1);
	    	System.out.println("dd " );

	    	reunionrepository.save(re);
	    	System.out.println("ff " );

			int reid = getReun_idOrdered(re.getJardin()).get(0);
	    	System.out.println("g "+ reid);
	    	System.out.println("gplus "+ re.getParents().size());

			if (re.getParents().size()!=0){
				System.out.println("h "+ re.getParents().size());
				System.out.println("AVOID INTERSECTION START"+ re.getParents().get(0).getId());

			for ( int j=0 ; j<re.getParents().size(); j++ ){
				System.out.println("i "+ j);
				Reunion reunionEntity = reunionrepository.findById(reid).get();
				Parent parentEntity = parentrepository.findById((re.getParents().get(j).getId())).get();
				reunionEntity.setReun_nbrparticipant(reunionEntity.getReun_nbrparticipant()+1);
				parentEntity.getReunionsP().add(reunionEntity);
				reunionrepository.save(reunionEntity);			
				parentrepository.save(parentEntity);
				System.out.println("j "+ j);

			}
			}
		}
		
		
}
	}
}
}


public int creerReunion1(Reunion re,KinderGarten k) throws Exception {
Reunion ren = new Reunion();
Date currentdaytime = getTodayDateAndTime();
Date Reunionstart = combineDateTime(re.getReun_date(),re.getReun_heureD()); 

if (Reunionstart.after(currentdaytime)){
KinderGarten jardin = k;
if ( re.getJardin()==null){
ren.setJardin(jardin);
}
else ren.setJardin(re.getJardin());

int s=getcontrainte(re,jardin,re.getReun_date());
if (s==-1)
throw new Exception("verifier le temps svp");

else
if (s == 0) {
ren.setReun_nbrplace(re.getReun_nbrplace());
ren.setReun_title(re.getReun_title());
ren.setReun_nbrparticipant(0);
ren.setReun_description(re.getReun_description());
ren.setReun_date(re.getReun_date());
ren.setReun_heureD(re.getReun_heureD());
ren.setReun_heureF(re.getReun_heureF());
ren.setReun_type(re.getReun_type());
reunionrepository.save(ren);
}
else throw new Exception("Vous avez deja une reunion qui se deroule");
} else throw new Exception("Merci d'entrer une nouvelle date");

return re.getReun_id();	

}

@Override
public List<Reunion_dispo> getDisponibiltyforrP() {
	UserApp user = userservices.currentUserJsf();
	Parent parent = user.getParent();
	
	return reunionrepository.getDisponibiltyforrP(parent);
}

@Override
public List<Reunion> findReunionByTitle(String searchString,Parent p) {
	return reunionrepository.findReunionByTitle(searchString,p);
}

@Override
public List<Reunion> findReunionByKinderGarten(String searchString,Parent p) {
	return reunionrepository.findReunionByKinderGarten(searchString,p);
}

@Override
public List<Reunion> findReunionByTitle(String searchString, KinderGarten jardin) {
	return reunionrepository.findReunionByTitle(searchString,jardin);
}

@Override
public List<Reunion> findReunionByKinderGarten(String searchString, KinderGarten jardin) {
	return reunionrepository.findReunionByKinderGarten(searchString,jardin);
}

@Override
public List<Reunion_dispo> getDisponibiltyforJ() {
	UserApp user = userservices.currentUserJsf();
	KinderGarten jardin = user.getKindergarten();
	
	return reunionrepository.getDisponibiltyforJ(jardin);	
}





@Override
public List<Reunion> findReunionByParentName(String searchString, KinderGarten jardin) {
	return reunionrepository.findReunionByParentName(searchString,jardin);

}





@Override
public List<Reunion> getWeekReunionByParent() {
	// TODO Auto-generated method stub
	UserApp user = userservices.currentUserJsf();
	Parent parent = user.getParent();
	return reunionrepository.getWeekReunionByParent(parent);
}





@Override
public List<Reunion> getMonthReunionByParent() {
	// TODO Auto-generated method stub
	UserApp user = userservices.currentUserJsf();
	Parent parent = user.getParent();
	return reunionrepository.getMonthReunionByParent(parent);
}




@Override
public List<Reunion> getOldReunionByParent() {
	// TODO Auto-generated method stub
	UserApp user = userservices.currentUserJsf();
	Parent parent = user.getParent();
	return reunionrepository.getOldReunionByParent(parent);}



/*Reunion re = reunionrepository.findById(reId).get();
Reunion ren = new Reunion();

SimpleDateFormat df = new SimpleDateFormat("HH:mm");
Date finR = combineDateTime(re.getReun_date(),df.parse(re.getReun_heureF()));
Date dateR = re.getReun_date();

	Calendar cal = Calendar.getInstance();

	String date1= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cal.getTime());
	Date now=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date1);
	if(re.getEtat_R()==0){
	if (now.compareTo(finR) >=0){
		if (re.getReun_type() == TypeReunion.MENSUEL ){
			Calendar c = Calendar.getInstance(); 
			c.setTime(dateR); 
			c.add(Calendar.MONTH, 1);
			Date theDate = AvoidIntersection(re,c.getTime());
			re.setReun_date(theDate);
			
			creerReunion12(re,re.getJardin());
			//c.add(Calendar.MONTH, -1);
			re.setReun_date(dateR);
			re.setEtat_R(1);
	    	reunionrepository.save(re);
			int reid = getReun_idOrdered(re.getJardin()).get(0);
			if (re.getParents().size()!=0){
			for ( int i=0 ; i<re.getParents().size(); i++ ){
				affecterParentAReunion( re.getParents().get(i).getId(), reid);
			}
			}

					        }
		else if ( re.getReun_type() == TypeReunion.HEBDOMADAIRE){
			Calendar c = Calendar.getInstance(); 
			c.setTime(dateR); 
			c.add(Calendar.DAY_OF_YEAR, 7);
			Date theDate = AvoidIntersection(re,c.getTime());
			re.setReun_date(theDate);					///
			creerReunion12(re,re.getJardin());
			//c.add(Calendar.DAY_OF_YEAR, -7);
			re.setReun_date(dateR);
			re.setEtat_R(1);
	    	reunionrepository.save(re);
			int reid = getReun_idOrdered(re.getJardin()).get(0);
			if (re.getParents().size()!=0){
			for ( int i=0 ; i<re.getParents().size(); i++ ){
				affecterParentAReunion(re.getParents().get(i).getId(), reid);
			}
			}
		}

}else throw new Exception ("La Reunion va etre renouveler apres que la reunion precedante est terminé");
}else throw new Exception ("La Reunion est déja renouvelé");
*/


/*
Reunion ren = new Reunion();
    	Date theDatefull =null;

    	UserApp user = userservices.currentUserJsf();
    	KinderGarten jardin = user.getKindergarten();
    	
    	List<Reunion> l = reunionrepository.getUpcomingReunionFromDateUntildate(jardin,dateDR,dateFR);
    	for (int i=0;i<l.size();i++){
    	System.out.println("+++" + l.get(i).getReun_id() + " dateDR " +dateDR+" dateFR "+dateFR);
    	
    	}
    	Date NouvelleDate = dateFR;
    	System.out.println(dateFR);
    	
    	for (int i=0;i<l.size();i++){
    		System.out.println("dans reporter avoid intersection pour" + l.get(i).getReun_id() +" avec la date" + NouvelleDate);

    		 theDatefull = AvoidIntersectionnew(l.get(i),justdate(dateFR));
    			System.out.println("dans reporter after avoid intersection nouvelledate " +NouvelleDate +"thedatefull: " + theDatefull +" heure"+	l.get(i).getReun_heureD() +" "+l.get(i).getReun_heureF() );
    			Date theDate =getDatefromDate(theDatefull);
    			Time theStartTime = getTimefromDate(theDatefull);
    		Time theEndTime = addDurationtoTime(theDatefull,l.get(i).getReun_heureF().getTime()-l.get(i).getReun_heureD().getTime());
    		
    		System.out.println("reportReunionFromDateUntilDatenew theDatefull: "+theDatefull+" theDate "+theDate
    				+" theStartTime "+theStartTime+" theEndTime "+theEndTime+" setReun_heureD "+addoneHour(theStartTime)+
    				" setReun_heureF "+addoneHour(theEndTime) +" setReun_date "+theDate
    				);
    		
    		
    		ren.setReun_nbrplace(l.get(i).getReun_nbrplace());
    		ren.setReun_title(l.get(i).getReun_title());
    		ren.setReun_nbrparticipant(l.get(i).getReun_nbrparticipant());
    		ren.setReun_description(l.get(i).getReun_description());
    		ren.setReun_date(add1DayToDate(theDate));
    		ren.setReun_heureD(theStartTime);
    		ren.setReun_heureF(theEndTime);
    		ren.setReun_type(TypeReunion.EXCEPTIONNEL);
    		System.out.println("ren.gethf"+" "+ren.getReun_heureD()+" re.gethf "+ren.getReun_heureF()+"start time"
    				+theStartTime +" endtime "+theEndTime + "l.get(i).getParents().size() "+l.get(i).getParents().size()
    				);
    		creationReunion(ren);	
    		int reid = getReun_idOrdered(jardin).get(0);
            if (l.get(i).getParents().size()!=0 ){
    		for ( int index=0 ; index<l.get(i).getParents().size(); index++ ){
    			System.out.println(".................affeceter "+l.get(i).getParents().get(0) + " a reunion id "+reid);

    			affecterParentAReunion(l.get(i).getParents().get(index).getId(), reid);
    		}}
    		System.out.println(i+".crée");
    		
    		System.out.println("debuuuuuuuuuuut annuler");
            annulerReunionByIdnew(l.get(i).getReun_id());
    		System.out.println("fiiiiiiiiiiiiiiiin annuler");
 */
}
