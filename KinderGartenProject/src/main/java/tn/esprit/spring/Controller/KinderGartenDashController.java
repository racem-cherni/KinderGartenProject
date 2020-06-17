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
import tn.esprit.spring.Service.UserServices;
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
	private String search="";
	private int remove1;
	
	private String kinderGartenNameSE;
	private String imageSE;
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
	 List<Competence> listCompe=new ArrayList<>();
	 
	//--------------------classe
	@Autowired
	UserServices userServices;
	 private  Long id;
		private String nomc;
		private int capacitiec;
		private int agec;
		
		private Teacher teacher;
		private Teacher teacherC;
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
	
	@PostConstruct
	public void init(){
	//Classe cl=classeServices.getClasseById(16L);
		/*System.err.println("classe "+cl.getNom());
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
		this.kidcl=classeServices.getKidByClasse(cl);
		System.err.println("**********************************************");*/
		
	}
	
	
	
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
		
		//System.err.println("classe "+c.getId());
	Classe cl=classeServices.getClasseById(16L);
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
	this.kidcl=classeServices.getKidByClasse(cl);
	System.err.println("**********************************************");
		
		return"/pages/kindergarten/showdetails.xhtml?faces-redirect=true";
		
	}
	public void supprimerchild(Child c){
		System.err.println("**********************************************");
		classeServices.retirerKidk(c);
		this.kids.remove(c);
	}
	
	
	public void remouveChild(Child child){
		this.remove1=1;
		Classe cl=classeServices.getClasseById(this.id);
		classeServices.retirerKid(child, cl);
		this.kidcl.remove(child);
		
	}
	public String showteacherCom(Teacher t){
		this.teacherC=t;
		this.listCompe.clear();
		this.lcom.clear();
		boolean test=true;
		List<Competence> lc =teacherServices.showCompetences();
		List<Competence> lc1=(List<Competence>) this.teacherC.getCompetences();		
		for (Competence competence : lc) {
			test=true;
		for (Competence competence1 : lc1) {
			if(competence1.getId()==competence.getId())
				test=false;
			
			
		}
		
		if(test==true)
			listCompe.add(competence);
			
		}
		
		
		lcom.addAll(lc1);
		return"/pages/kindergarten/Competence.xhtml?faces-redirect=true";
	}
	public void remplirComp(Competence c){
		lcom.add(c);
		this.listCompe.remove(c);
	}
	
	
	
	
	public String show(){
		
		teacherServices.saveTeacherCompetenceJsf(lcom, this.teacherC.getId());
		return"/pages/kindergarten/kindergartendash.xhtml?faces-redirect=true";
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
		
		
				UserApp user=userServices.currentUserJsf();
				this.kids.clear();
		
				if(search!=""){
					
					this.kids=classeServices.getKidByKinder(user.getKindergarten()).stream().filter(e->e.getChildName().contains(search)).collect(Collectors.toList());
					search="";
					return this.kids;
							
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
		
		
		
		
		return lcom;
	}
	public void setLcom(List<Competence> lcom) {
		this.lcom = lcom;
	}
	
	public List<Competence> getListCompe() {
		
		
	
		return listCompe;
	}
	public void setListCompe(List<Competence> listCompe) {
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
		if(c==null){
			hellomessage="";
			return hellomessage;
		}
		
		
				UserApp user=userServices.currentUserJsf();
		
		Classe cl=classeServices.getClasseByKidAge(c,user.getKindergarten());
		hellomessage=cl.getNom();
		return hellomessage;
	}
	public Teacher getTeacherC() {
		return teacherC;
	}
	public void setTeacherC(Teacher teacherC) {
		this.teacherC = teacherC;
	}
	public String getKinderGartenNameSE() {
		if(userServices.currentUserJsf().getKindergarten()==null )
		return userServices.currentUserJsf().getUsername();
			
		return userServices.currentUserJsf().getKindergarten().getKinderGartenName();
	}
	public void setKinderGartenNameSE(String kinderGartenNameSE) {
		this.kinderGartenNameSE = kinderGartenNameSE;
	}
	public String getImageSE() {
		String im="";
		if(userServices.currentUserJsf().getKindergarten()==null)
		{
			im="unknown.png";	
			imageSE=im;
			return imageSE;
		}
		if(userServices.currentUserJsf().getKindergarten().getImage()==null || userServices.currentUserJsf().getKindergarten().getImage()=="")
			im="unknown.png";
		else
		 im=userServices.currentUserJsf().getKindergarten().getImage();
		imageSE=im;
		return imageSE;
	}
	public void setImageSE(String imageSE) {
		this.imageSE = imageSE;
	}
	

	
	
	
	
}
