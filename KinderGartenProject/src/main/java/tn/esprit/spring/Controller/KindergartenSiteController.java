package tn.esprit.spring.Controller;

import java.util.ArrayList;
import java.util.List;

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
import tn.esprit.spring.entities.Teacher;
import tn.esprit.spring.entities.UserApp;
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
private Long id;
	int i=0;
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
		kinderGartenName = kinderGartenName;
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
		

	//	relationServices.abonnerAKinderGarten(userServices.currentUserJsf(), kinder.getUserapp());
		
		
	return null;	
	}
	
	
	
	
	
	
	
	
	
	
}
