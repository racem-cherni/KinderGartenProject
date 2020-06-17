package tn.esprit.spring.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.ClasseServices;
import tn.esprit.spring.Service.RelationServices;
import tn.esprit.spring.Service.TeacherServices;
import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Teacher;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.UserRepository;

@Controller(value = "KindergartenSiteController")
@ELBeanName(value = "KindergartenSiteController")
public class KindergartenSiteController {
@Autowired
private UserRepository userRepository;
	@Autowired
	private ClasseServices classeServices;
	@Autowired
	private TeacherServices teacherServices;
	@Autowired
	RelationServices relationServices;
	@Autowired
	UserServices userServices;
	@Autowired
	KinderGartenRepository kinderGartenRepository;
private Long id;
	int i=0;
	
	private String kinderGartenName;
	private String adresse;
	private String searchAbonner="";
	private String searchAbonnerP="";
	private String email;
	private int capacite ;
	private int tel;
	private Float prix;
	private  int maxRdv;
	private String image;
	
	KinderGarten k;
	List<Classe> cl=new ArrayList<>();
	List<Teacher> teachers=new ArrayList<>();
	List<Parent> abonnerP=new ArrayList<>();
	List<KinderGarten> abonnerK=new ArrayList<>();
	boolean comutateur=false;

	public String  update(){
		KinderGarten kinder=userServices.currentUserJsf().getKindergarten();
		kinder.setAdresse(adresse);
		kinder.setEmail(email);
		kinder.setKinderGartenName(kinderGartenName);
		kinder.setTel(tel);
		kinder.setPrix(prix);
		
		kinderGartenRepository.save(kinder);
		return null;
	}
	
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getKinderGartenName() {
		return this.getK().getKinderGartenName();
	}
	public void setKinderGartenName(String kinderGartenName) {
		this.kinderGartenName = kinderGartenName;
	}
	public String getAdresse() {
		return this.getK().getAdresse();
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return this.getK().getEmail();
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getCapacite() {
		return this.getK().getCapacite();
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	public int getTel() {
		return this.getK().getTel();
	}
	public void setTel(int tel) {
		this.tel = tel;
	}
	public Float getPrix() {
		return this.getK().getPrix();
	}
	public void setPrix(Float prix) {
		this.prix = prix;
	}
	public int getMaxRdv() {
		return this.getK().getMaxRdv();
	}
	public void setMaxRdv(int maxRdv) {
		this.maxRdv = maxRdv;
	}
	
	
	
	public KinderGarten getK() {
		
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		System.err.println(userName);
		UserApp user=userRepository.findByUsername(userName);
		this.k=user.getKindergarten();
			
		
		return k;
	}
	public String DesAbonner(KinderGarten ki){
		System.err.println("*************************************"+ ki.getUserapp().getUsername());
			relationServices.DesabonnerAKinderGarten(userServices.currentUserJsf(), ki.getUserapp());
			this.abonnerK.remove(ki);
					return null;	
		}
	public String DesAbonnerF(Parent p){
		System.err.println("*************************************"+p.getUserApp().getUsername());
			relationServices.DesabonnerAKinderGarten(userServices.currentUserJsf(), p.getUserApp());
			this.abonnerP.remove(p);
		return null;	
		}
	public void setK(KinderGarten k) {
		
		this.k = k;
	}
	public List<Classe> getCl() {
		this.cl=classeServices.getClasseBykinder(this.getK());
		return cl;
	}
	public void setCl(List<Classe> cl) {
		this.cl = cl;
	}
	public List<Teacher> getTeachers() {
		this.teachers=teacherServices.getTeachers(this.getK());
		return teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		
		this.teachers = teachers;
	}
	
	
	
	public String Abonner(KinderGarten kinder){
		

		relationServices.abonnerAKinderGarten(userServices.currentUserJsf(), kinder.getUserapp());
		
		
	return null;	
	}
	public String getSearchAbonner() {
		return searchAbonner;
	}
	public void setSearchAbonner(String searchAbonner) {
		this.searchAbonner = searchAbonner;
	}
	
	
	public void SearchKinder(){
		abonnerP=null;
		
		comutateur=false;
	}
	
	public boolean imageuser(){
		KinderGarten k= userServices.currentUserJsf().getKindergarten();
		if(k.getImage()==null)
			return false;
		
		return true;
	}
	
	public void SearchParent(){
		
		if(!this.searchAbonner.equals("")){
			abonnerP=abonnerP.stream().filter(e->e.getFirstName().contains(this.searchAbonner) || e.getLastName().contains(this.searchAbonner)).collect(Collectors.toList());
			this.searchAbonner="";

		}
		else
		abonnerP=relationServices.myAbonneP(userServices.currentUserJsf());
		
		abonnerK=null;
		comutateur=true;
	}
	public List<Parent> getAbonnerP() {
		
		
		return abonnerP;
	}
	public void setAbonnerP(List<Parent> abonnerP) {
		this.abonnerP = abonnerP;
	}
	public List<KinderGarten> getAbonnerK() {
		if(comutateur==false){
		if(!this.searchAbonner.equals("")){
			System.out.println("**************************************************");
			abonnerK=abonnerK.stream().filter(e->e.getKinderGartenName().contains(this.searchAbonner)).collect(Collectors.toList());
			abonnerK.forEach(e->System.out.println(e.getKinderGartenName()));
			
			abonnerK=abonnerK.stream().filter(e->e.getId() != userServices.currentUserJsf().getKindergarten().getId()).collect(Collectors.toList());
			this.searchAbonner="";
			return abonnerK;
		}
		

		abonnerK=relationServices.myAbonne(userServices.currentUserJsf());
		abonnerK=abonnerK.stream().filter(e->e.getId() != userServices.currentUserJsf().getKindergarten().getId()).collect(Collectors.toList());
		}
		return abonnerK;
	}
	public void setAbonnerK(List<KinderGarten> abonnerK) {
		this.abonnerK = abonnerK;
	}
	public String getSearchAbonnerP() {
		return searchAbonnerP;
	}
	public void setSearchAbonnerP(String searchAbonnerP) {
		this.searchAbonnerP = searchAbonnerP;
	}

	public boolean isComutateur() {
		return comutateur;
	}

	public void setComutateur(boolean comutateur) {
		this.comutateur = comutateur;
	}

	public String getImage() {
		return this.getK().getImage();
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	
	
	
	
	
	
	
}
