package tn.esprit.spring.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;

import org.eclipse.jdt.internal.compiler.SourceElementNotifier;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.AnalysticServices;
import tn.esprit.spring.Service.ClasseServices;
import tn.esprit.spring.Service.TeacherServices;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Competence;
import tn.esprit.spring.entities.Teacher;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.UserRepository;

@Controller(value = "KinderGartenDashController")
@ELBeanName(value = "KinderGartenDashController")

public class KinderGartenDashController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String KinderGartenName;
	private String adresse;
	private String email;
	private int capacite ;
	private int tel;
	private Float prix;
	private  int maxRdv;
	private String hellomessage;
	private String search;
	private int remove1;
	public int getRemove1() {
		return remove1;
	}
	public void setRemove1(int remove1) {
		this.remove1 = remove1;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	//----------------teacher
	private String nom;
private String prenom;
	private int age;
	private int numtel;
	private String emailt;
	List<Competence> lcom=new ArrayList<>();
	 private String[] selectedCompetens;
	 List<String> listCompe=new ArrayList<>();
	 
	//--------------------classe
	
	 private  Long id;
		private String nomc;
		private int capacitiec;
		private int agec;
		
		private Teacher teacher;
	List<Child> kidcl=new ArrayList<Child>();
	Map<String,List<String>> map=new HashMap<>();
	
	//--------------------------
	List<Teacher> teachers=new ArrayList<Teacher>();
	List<Classe> classes=new ArrayList<Classe>();
	List<Child> kids=new ArrayList<Child>();
	@Autowired 
	AnalysticServices analysticServices;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TeacherServices teacherServices;
	@Autowired
	ClasseServices classeServices;
public String deleteTeacher(Teacher t){
	teacherServices.delateTeacher(t);
	
	return null;
}
	public String displayTeacher(Teacher t){
	this.nom=t.getNom();
	this.age=t.getAge();
	this.numtel=t.getNumtel();
	this.emailt=t.getEmail();
	
	
	return null;
	}
public String addTeacher(){
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	String userName;

			if (principal instanceof UserDetails) {
				userName = ((UserDetails) principal).getUsername();
			} else {
				userName = principal.toString();
			}
			System.err.println(userName);
			UserApp user=userRepository.findByUsername(userName);
	System.err.println("******************************************************");
	Teacher t=new Teacher();
	t.setAge(this.age);
	t.setEmail(this.emailt);
	t.setNom(this.nom+" "+this.prenom);
	t.setNumtel(this.numtel);
	t.setKinderGarten(user.getKindergarten());
	teacherServices.saveTeacher(t);
	this.nom="";
	this.prenom="";
	this.age=0;
	this.numtel=0;
	this.emailt="";
	return null;
}
	public String addClasse(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println("********************************************************");
				UserApp user=userRepository.findByUsername(userName);
		Classe c=new Classe();
		c.setAge(agec);
		c.setCapacitie(capacitiec);
		c.setNom(nomc);
		
		classeServices.saveClasse(c, user.getKindergarten());
		
		this.agec=0;
		
		this.capacitiec=0;
		this.nomc="";
		return null;
	}
	
	public String DisplayClasse(Classe cl){
this.id=cl.getId();		
this.agec=cl.getAge();
this.capacitiec=cl.getCapacitie();
this.nomc=cl.getNom();
		
		
		return null;
	}
	
public String updateClasse()	{
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	String userName;

			if (principal instanceof UserDetails) {
				userName = ((UserDetails) principal).getUsername();
			} else {
				userName = principal.toString();
			}
			System.err.println("**************************************************");
			UserApp user=userRepository.findByUsername(userName);
	Classe cl=classeServices.getClasseById(id);
	Classe clu=classeServices.getClasseById(id);
	cl.setAge(agec);
	cl.setCapacitie(capacitiec);
	cl.setNom(nomc);
	classeServices.updateClasse(clu,cl, user.getKindergarten());
	
	return null;
}
	
	
	
	
	
	public List<Teacher> getTeachers() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		
		this.teachers=teacherServices.getTeachers(user.getKindergarten());
		return this.teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	public List<Classe> getClasses() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		
		
		
		
		return this.classes=classeServices.getClasseBykinder(user.getKindergarten());
	}
	
	public String displayClasse(Classe c){
		System.err.println("classe "+c.getId());
	Classe cl=classeServices.getClasseById(c.getId());
	System.err.println("classe "+cl.getNom());
	this.agec=cl.getAge();
	this.id=cl.getId();
	this.capacitiec=cl.getCapacitie();
	this.nomc=cl.getNom();
	
	if(cl.getTeacher()!=null)
	this.teacher=cl.getTeacher();
	System.err.println("classe "+this.nomc);
	this.map.clear();
	this.map.putAll(analysticServices.MyteacherMonqueCompetence(cl));
	this.kidcl.clear();
	this.kidcl=classeServices.getKidByClasse(c);
	
		
		return"/pages/kindergarten/showdetails.xhtml?faces-redirect=true";
		
	}
	
	
	public void remouveChild(Child child){
		this.remove1=1;
		Classe cl=classeServices.getClasseById(this.id);
		classeServices.retirerKid(child, cl);
		this.kidcl.remove(child);
		
	}
	
	
	public void setClasses(List<Classe> classes) {
		this.classes = classes;
	}
	public String getKinderGartenName() {
		return KinderGartenName;
	}
	public void setKinderGartenName(String kinderGartenName) {
		KinderGartenName = kinderGartenName;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getEmail() {
		return this.email;
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
	public List<Child> getKids() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
				this.kids.clear();
		
				if(search!=null){
					return this.kids=classeServices.getKidByKinder(user.getKindergarten()).stream().filter(e->e.getChildName().contains(search)).collect(Collectors.toList());
				}
		
		return this.kids=classeServices.getKidByKinder(user.getKindergarten());
		
			}
	public void setKids(List<Child> kids) {
		this.kids = kids;
	}
	
	public List<Child> getKidcl() {
		this.remove1++;
		System.err.println("**************************"+this.remove1);
		return kidcl;
	}
	public void setKidcl(List<Child> kidcl) {
		this.kidcl = kidcl;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getNumtel() {
		return numtel;
	}
	public void setNumtel(int numtel) {
		this.numtel = numtel;
	}
	public String getEmailt() {
		return emailt;
	}
	public void setEmailt(String emailt) {
		this.emailt = emailt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomc() {
		return nomc;
	}
	public void setNomc(String nomc) {
		this.nomc = nomc;
	}
	
	public int getAgec() {
		return agec;
	}
	public void setAgec(int agec) {
		this.agec = agec;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public int getCapacitiec() {
		return capacitiec;
	}
	public void setCapacitiec(int capacitiec) {
		this.capacitiec = capacitiec;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public List<Competence> getLcom() {
		
		
		
		
		return teacherServices.showCompetences();
	}
	public void setLcom(List<Competence> lcom) {
		this.lcom = lcom;
	}
	public String[] getSelectedCompetens() {
		return selectedCompetens;
	}
	public void setSelectedCompetens(String[] selectedCpmpetens) {
		this.selectedCompetens = selectedCpmpetens;
	}
	public List<String> getListCompe() {
		teacherServices.showCompetences().forEach(c->listCompe.add(c.getCompetenceName()));
		
		listCompe.forEach(c->System.err.println(c));
		return listCompe;
	}
	public void setListCompe(List<String> listCompe) {
		this.listCompe = listCompe;
	}
	
	
	public void onItemUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
         
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
         
        context.addMessage(null, msg);
    }
	public Map<String, List<String>> getMap() {
		return map;
	}
	public void setMap(Map<String, List<String>> map) {
		this.map = map;
	}
	public String getHellomessage() {
		return hellomessage;
	}
	public void setHellomessage(String hellomessage) {
		this.hellomessage = hellomessage;
	}

	public String getHellomessageajax(Child c) {
		System.err.println("***********************************************ajax1");
		if(c==null){
			hellomessage="";
			return hellomessage;
		}
		if(c.getClasse()==null){
			hellomessage="";
			return hellomessage;
		}
		if(c.getClasse()==null){
			hellomessage="";
			return hellomessage;
		}
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println("***********************************************ajax2");
				UserApp user=userRepository.findByUsername(userName);
		
		Classe cl=classeServices.getClasseByKidAge(c,user.getKindergarten());
		hellomessage=cl.getNom();
		return hellomessage;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
