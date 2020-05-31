package tn.esprit.spring.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.ClasseServices;
import tn.esprit.spring.Service.RechercheKinderServices;
import tn.esprit.spring.Service.RelationServices;
import tn.esprit.spring.Service.TeacherServices;
import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.RechercheMenu;
import tn.esprit.spring.entities.Teacher;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.UserRepository;

@Controller(value = "RecherchController")
@ELBeanName(value = "RecherchController")
public class RecherchController {

	private Long id;
	private Float maxprix=1000F;
	private Float minprix=0F;
	private int score;
	private boolean trieByscore;

	private String country;
	private String ville;
	private String village;
	private String rue;
	private String search;
	
	
	List<KinderGarten> kinders=new ArrayList<>();
	private boolean testE;
	private String kinderGartenName;
	private String adresse;
	private String email;
	private int capacite ;
	private int tel;
	private Float prix;
	private  int maxRdv;
	KinderGarten k;
	List<Classe> cl=new ArrayList<>();
	List<Teacher> teachers=new ArrayList<>();
	private KinderGarten ki;
	@Autowired
	private ClasseServices classeServices;
	@Autowired
	private TeacherServices teacherServices;
	@Autowired
	private ClasseServices ClasseServices;
	@Autowired
	private RechercheKinderServices  rechercheKinderServices;
	@Autowired
	private RelationServices relationServices;
	@Autowired
	UserServices userServices;
	
	@PostConstruct
	public void init(){
		this.kinders=rechercheKinderServices.findall();
	}
	
	
	
	
	public String recherch(){
		if(search!="")
		{
			this.kinders=rechercheKinderServices.recherchKinderGarten(search);
			System.err.println(search);
			this.kinders.forEach(e->	System.err.println(e.getKinderGartenName()));
			return	null;
		}
			
		RechercheMenu r=new RechercheMenu();
		r.setMaxprix(maxprix);
		r.setMinprix(minprix);
		r.setScore(score);
		r.setTrieByscore(trieByscore);
		r.setCountry(country);
		r.setVille(ville);
		r.setVillage(village);
		r.setRue(rue);
		//rechercheKinderServices.getKinderByRechercheMenu(r, userServices.currentUserJsf()).forEach(e->System.err.println(e.getKinderGartenName()));;
		
		this.kinders=rechercheKinderServices.getKinderByRechercheMenu(r, userServices.currentUserJsf());
		
	return	null;
		
		
	}
	
	
	
public String showVisitor(KinderGarten kinder){
		this.ki=kinder;
		System.err.println("*************************************"+ kinder.getId());
		this.testE=relationServices.testAbonner(userServices.currentUserJsf(),userServices.getUserBykinder(kinder) );
	 this.kinderGartenName=kinder.getKinderGartenName();
	 this. adresse=kinder.getAdresse();
	 this.email =kinder.getEmail();
	 this.capacite=kinder. getCapacite();
	 this.tel=kinder.getTel();
	 this.prix=kinder.getPrix();
	 this.maxRdv=kinder.getMaxRdv();
	
	 this.cl=classeServices.getClasseBykinder(kinder);
	 
	 this.teachers=teacherServices.getTeachers(kinder);
		
		return "/pages/kindergarten/showProfileToOther.xhtml?faces-redirect=true";		
	}
	
	public boolean test(KinderGarten kinder){
		
		return true;
		
	}
	
public String Abonner(){
	
System.err.println("*************************************"+ ki.getUserapp().getUsername());
	relationServices.abonnerAKinderGarten(userServices.currentUserJsf(), ki.getUserapp());
	
	
return null;	
}

public String DesAbonner(){
	
System.err.println("*************************************"+ ki.getUserapp().getUsername());
	relationServices.DesabonnerAKinderGarten(userServices.currentUserJsf(), ki.getUserapp());
	
	
return null;	
}
	
	
	
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Float getMaxprix() {
		return maxprix;
	}
	public void setMaxprix(Float maxprix) {
		this.maxprix = maxprix;
	}
	public Float getMinprix() {
		return minprix;
	}
	public void setMinprix(Float minprix) {
		this.minprix = minprix;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isTrieByscore() {
		return trieByscore;
	}
	public void setTrieByscore(boolean trieByscore) {
		this.trieByscore = trieByscore;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}



	public List<KinderGarten> getKinders() {
	
		
		
		
		
		return kinders;
	}


	public void setKinders(List<KinderGarten> kinders) {
		this.kinders = kinders;
	}


	public String getSearch() {
		return search;
	}


	public void setSearch(String search) {
		this.search = search;
	}




	public String getKinderGartenName() {
		return kinderGartenName;
	}




	public void setKinderGartenName(String kinderGartenName) {
		this.kinderGartenName = kinderGartenName;
	}




	public String getAdresse() {
		return adresse;
	}




	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public int getCapacite() {
		return capacite;
	}




	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}




	public int getTel() {
		return tel;
	}




	public void setTel(int tel) {
		this.tel = tel;
	}




	public Float getPrix() {
		return prix;
	}




	public void setPrix(Float prix) {
		this.prix = prix;
	}




	public int getMaxRdv() {
		return maxRdv;
	}




	public void setMaxRdv(int maxRdv) {
		this.maxRdv = maxRdv;
	}




	public KinderGarten getK() {
		return k;
	}




	public void setK(KinderGarten k) {
		this.k = k;
	}




	public List<Classe> getCl() {
		return cl;
	}




	public void setCl(List<Classe> cl) {
		this.cl = cl;
	}




	public List<Teacher> getTeachers() {
		return teachers;
	}




	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}




	public KinderGarten getKi() {
		return ki;
	}




	public void setKi(KinderGarten ki) {
		this.ki = ki;
	}




	public boolean isTestE() {
		return testE;
	}




	public void setTestE(boolean testE) {
		this.testE = testE;
	}

	
	
	
	
	
}
