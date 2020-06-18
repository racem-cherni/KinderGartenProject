package tn.esprit.spring.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.SujetReclam;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.RdvRepository;
import tn.esprit.spring.repository.ReclamationRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class ReclamationServiceImpl implements IReclamationService {
	
	@Autowired
	ReclamationRepository reclamationrepository;
	
	@Autowired
	ReclamationServiceImpl reclamationService;
	
	@Autowired
	UserRepository userrepository;
	
	@Autowired
	private Session sessionservice;
	
	@Autowired
	ParentRepository parentrepository;
	@Autowired
	KinderGartenRepository kgrepository;
	@Autowired
	UserServices userservices;
	//jardin
	//////////////////////////////////////////////////////////////////////////////////////////////////
	//getNombreParType
		public Integer getNombreParType(SujetReclam type,KinderGarten jardin) throws ParseException {
			
			Double Ntype = (double) reclamationrepository.getNombreTotalParType(jardin,type);
			
			return Ntype.intValue();
		}
	    //getPourcentageParType
		@Override
		public Double getPourcentageParType(SujetReclam type,KinderGarten jardin) throws ParseException {
			
			Double Ntype = (double) reclamationrepository.getNombreTotalParType(jardin,type);
			Double Ntotale = (double) reclamationrepository.getNombreTotalReclam(jardin);
			Double pourcentage = (Ntype/Ntotale)*100;
			double d = (double) Math.round(pourcentage * 100) / 100;
			return d;
		}
	
		public List<Reclamation> getListRParType(SujetReclam type,KinderGarten jardin){

			
			List<Reclamation > R =reclamationrepository.getReclamationParType(jardin,type);
			List<Reclamation > Re = new  ArrayList<>();
			if (!R.isEmpty()){
				for (int i=0; i<R.size();i++){
					Re.add(R.get(i));
					if(i==2) break;
				}
			}
			

		return Re;
		}
public List<Reclamation> getListParType(SujetReclam type,KinderGarten jardin){

			
	return reclamationrepository.getReclamationParType(jardin,type);
			
			

		
		}
	//////////////////////////////////////////////////////////////////////////////////////////
		/////parent
		@Override
		public List<KinderGarten> getJardinByParent(Parent parent) {
			
	     return reclamationrepository.getJardinByParent(parent);
		}
	
		@Override
		public List<Reclamation> getReclamationparparent() {
			UserApp user = userservices.currentUserJsf();
			Parent parent = user.getParent();
			return reclamationrepository.getReclamationparparent(parent);		
		}

		@Override
		public void EnvoyerReclamation(Reclamation rec) throws ParseException {
			UserApp user = userservices.currentUserJsf();
			Parent parent = user.getParent();
			
			Date date = new Date();
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, 1);


			String date1= new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			Date ajoutDate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
			
			
			rec.setReclamation_date(ajoutDate);
			rec.setParent(parent);
			if(rec.getSujet_reclam()==SujetReclam.SITEWEB ){
			rec.setReclam_reponse("votre réclmation est transmise vers le bloc technique");
			}else if(rec.getSujet_reclam()==SujetReclam.ACTIVITE) {
				rec.setReclam_reponse("votre reclamation est bien recu et va etre prise en compte");
				}else if(rec.getSujet_reclam()==SujetReclam.MAITRE) {
					rec.setReclam_reponse("votre réclamation a été bien tenue en compte merci");
				}else if(rec.getSujet_reclam()==SujetReclam.NOURRITURE) {
					rec.setReclam_reponse("votre reclamation est bien recu et va etre prise en compte");
				}else if(rec.getSujet_reclam()==SujetReclam.AUTRE) {
					rec.setReclam_reponse("votre reclamation est bien recu");
				}
			reclamationrepository.save(rec);
		}
	
		
		
		
		
		
		
		
		
	
	
	public int EnvoyerReclamation(Reclamation rec, long idJardin,HttpServletRequest request) throws ParseException {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();		
		Date date = new Date();
		/*String date1= new SimpleDateFormat("yyyy-MM-dd").format(date);
		Date ajoutDate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);*/
	
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);


		String date1= new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		Date ajoutDate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		
		KinderGarten jardin = kgrepository.findById( idJardin).get();
		rec.setReclamation_date(ajoutDate);
		rec.setJardin(jardin);
		rec.setParent(parent);
		if(rec.getSujet_reclam()==SujetReclam.SITEWEB ){
		rec.setReclam_reponse("votre réclmation est transmise vers le bloc technique");
		}else if(rec.getSujet_reclam()==SujetReclam.ACTIVITE) {
			rec.setReclam_reponse("votre reclamation est bien recu et va etre prise en compte");
			}else if(rec.getSujet_reclam()==SujetReclam.MAITRE) {
				rec.setReclam_reponse("votre réclamation a été bien tenue en compte merci");
			}else if(rec.getSujet_reclam()==SujetReclam.NOURRITURE) {
				rec.setReclam_reponse("votre reclamation est bien recu et va etre prise en compte");
			}else if(rec.getSujet_reclam()==SujetReclam.AUTRE) {
				rec.setReclam_reponse("votre reclamation est bien recu");
			}
		reclamationrepository.save(rec);
		return rec.getReclamation_id();		
	}
	/////////////
	/////////////
	/////////////
	
	public int ajouterReclmation(Reclamation rec) throws ParseException {
	/*	long idparent =2;
		Date date = new Date();
		String date1= new SimpleDateFormat("yyyy-MM-dd").format(date);
		Date ajoutDate=new SimpleDateFormat("yyyy-MM-dd").parse(date1);

		User jardin = rec.getJardin();
		User parent = userrepository.findById(idparent).get();
		rec.setReclamation_date(ajoutDate);
		rec.setJardin(jardin);
		rec.setParent(parent);


		reclamationrepository.save(rec);*/
		return rec.getReclamation_id();
	}
	///////////////
	////////////////
	//////////////
	public Double getPourcentageParTypeEtPeriode(String datedd, String dateff,SujetReclam type) throws ParseException {
		Date dated=new SimpleDateFormat("yyyy-MM-dd").parse(datedd);
		Date datef=new SimpleDateFormat("yyyy-MM-dd").parse(dateff);
        
		Double Ntype = (double) reclamationrepository.getNombreParTypeEtPeriode(dated,datef,type);
		Double Ntotale = (double) reclamationrepository.getNombreParPeriode(dated,datef);
		Double pourcentage = (Ntype/Ntotale)*100;

		return (double) pourcentage;
}
	////////////
	////////////
	////////////
	
	public String getPourcentageParPeriode(String datedd, String dateff) throws ParseException {
		Date dated=new SimpleDateFormat("yyyy-MM-dd").parse(datedd);
		Date datef=new SimpleDateFormat("yyyy-MM-dd").parse(dateff);
		
		int NbrNOURRITURE = (int) reclamationrepository.getNombreParTypeEtPeriode(dated,datef,SujetReclam.NOURRITURE);
		Double NbrACTIVITE = (double) reclamationrepository.getNombreParTypeEtPeriode(dated,datef,SujetReclam.ACTIVITE);
		Double NbrAUTRE = (double) reclamationrepository.getNombreParTypeEtPeriode(dated,datef,SujetReclam.AUTRE);
		Double MAITRE = (double) reclamationrepository.getNombreParTypeEtPeriode(dated,datef,SujetReclam.MAITRE);
		Double SITEWEB = (double) reclamationrepository.getNombreParTypeEtPeriode(dated,datef,SujetReclam.SITEWEB);
		Double Ntotale = (double) reclamationrepository.getNombreParPeriode(dated,datef);

		return "Les pourcentages par Type : \n Reclamation de nourriture : " + ((double) (NbrNOURRITURE/Ntotale))*100 +"%("+NbrNOURRITURE+")"
				+"\n Reclamation des activités : " + (NbrACTIVITE/Ntotale)*100 +"%("+NbrACTIVITE+")"
				+"\n Reclamation a props les maitres : " + (MAITRE/Ntotale)*100 +"%("+MAITRE+")"
						+"\n Reclamation de siteweb : " + (SITEWEB/Ntotale)*100 +"%("+SITEWEB+")"
								+"\n Autre : " + (NbrAUTRE/Ntotale)*100 +"%("+NbrAUTRE+")";	 
	}
	public List<String> getPourcentageParPeriod(String datedd, String dateff) throws ParseException {
		Date dated=new SimpleDateFormat("yyyy-MM-dd").parse(datedd);
		Date datef=new SimpleDateFormat("yyyy-MM-dd").parse(dateff);
		return null;
	}
	
	////////////
	///////////
	//////////////
	public List<Reclamation> getReclamationParType(SujetReclam type,HttpServletRequest request){

		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
	return reclamationrepository.getReclamationParType(jardin,type);
	}

	
	public List<Reclamation> getAllReclamationByJardin(HttpServletRequest request) {

		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		return reclamationrepository.getAllReclamationByJardinn(jardin);

}
	
	public List<Reclamation> getAllReclamationByParent(HttpServletRequest request) {
		UserApp connecteduser = sessionservice.session(request);
		Parent parent = connecteduser.getParent();		
		return reclamationrepository.getAllReclamationByParentt(parent);

}

	public List<Reclamation> getReclamationParTypeEtPeriode(SujetReclam type, String datedd, String dateff,HttpServletRequest request) throws ParseException {

		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
		Date dated=new SimpleDateFormat("yyyy-MM-dd").parse(datedd);
		Date datef=new SimpleDateFormat("yyyy-MM-dd").parse(dateff);
		return reclamationrepository.getReclamationParTypeEtPeriode(jardin,dated,datef,type);
	}
	/////////
	//////////
	//////////

	@Override
	public List<Reclamation> getReclamationByYear(int year,HttpServletRequest request) throws ParseException {

		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
        List<Date> l = getDateIntervalleByYear(year);
        return reclamationrepository.getReclamationByPeriode(jardin,l.get(0),l.get(1));

	}
	


	@Override
	public List<Reclamation> getReclamationByMonthOfYear(String mois, int year, SujetReclam type,HttpServletRequest request) throws Exception {

		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
        List<Date> l = reclamationService.getDateIntervalleByMonthOfYear(mois,year);
        return reclamationrepository.getReclamationParTypeEtPeriode(jardin,l.get(0),l.get(1),type);	
	}

	////////
	////////
	/////////
	@Override
	public List<Reclamation> getReclamationByMonthOfYear(String month, int year,HttpServletRequest request) throws Exception {

		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
        List<Date> l = reclamationService.getDateIntervalleByMonthOfYear(month,year);
        return reclamationrepository.getReclamationByPeriode(jardin,l.get(0),l.get(1));	
        }
	
	public List<Date> getDateIntervalleByYear( int year) throws ParseException{
		Calendar Datedd = Calendar.getInstance();
		Calendar Dateff = Calendar.getInstance();
		Datedd.set(year,0,1);
		Dateff.set(year,11,32);
		
		String date1= new SimpleDateFormat("yyyy-MM-dd").format(Datedd.getTime());
		String date2= new SimpleDateFormat("yyyy-MM-dd").format(Dateff.getTime());

		Date dated=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		Date datef=new SimpleDateFormat("yyyy-MM-dd").parse(date2);
        List<Date> l = new  ArrayList<>();
        l.add(dated);
        l.add(datef);
		return l;
	}
	public List<Date> getDateIntervalleByMonthOfYear(String month,int year) throws Exception{
		Calendar Datedd = Calendar.getInstance();
		Calendar Dateff = Calendar.getInstance();
	
		if (month.equals("JANUARY")){Datedd.set(year,0,1);Dateff.set(year,0,32);}
		else if (month.equals("FEBRUARY")){Datedd.set(year,1,1);Dateff.set(year,1,32);}
		else if (month.equals("MARCH")){Datedd.set(year,2,1);Dateff.set(year,2,32);}
		else if (month.equals("APRIL")){Datedd.set(year,3,1);Dateff.set(year,3,32);}
		else if (month.equals("MAY")){Datedd.set(year,4,1);Dateff.set(year,4,32);}
		else if (month.equals("JUNE")){Datedd.set(year,5,1);Dateff.set(year,5,32);}
		else if (month.equals("JULY")){Datedd.set(year,6,1);Dateff.set(year,6,32);}
		else if (month.equals("AUGUST")){Datedd.set(year,7,1);Dateff.set(year,7,32);}
		else if (month.equals("SEPTEMBER")){Datedd.set(year,8,1);Dateff.set(year,8,32);}
		else if (month.equals("OCTOBER")){Datedd.set(year,9,1);Dateff.set(year,9,32);}
		else if (month.equals("NOVEMBER")){Datedd.set(year,10,1);Dateff.set(year,10,32);}
		else if (month.equals("DECEMBER")){Datedd.set(year,11,1);Dateff.set(year,11,32);}
		else throw new Exception("LE mois entrer est non valide");
		
		String date1= new SimpleDateFormat("yyyy-MM-dd").format(Datedd.getTime());
		String date2= new SimpleDateFormat("yyyy-MM-dd").format(Dateff.getTime());

		Date dated=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		Date datef=new SimpleDateFormat("yyyy-MM-dd").parse(date2);
        List<Date> l = new  ArrayList<>();
        l.add(dated);
        l.add(datef);
		return l;
	}
/////////////////////////////////////////
// JSF ///////////////////////////// JSF /////////////////
	//////////////////////////////////////////////////
	
	
	




	

	

	@Override
	public Reclamation getLastReclamAddedForJadin(KinderGarten jardin) {
List<Reclamation> lr = reclamationrepository.getLastReclamAddedForJadin(jardin);
Reclamation r =null;
if (!(lr.isEmpty())){
	r=lr.get(0);
}
return r;
	}





}
