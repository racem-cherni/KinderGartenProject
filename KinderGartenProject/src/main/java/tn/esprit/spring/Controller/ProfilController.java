package tn.esprit.spring.Controller;

import java.io.Serializable;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;

import org.eclipse.jdt.internal.compiler.SourceElementNotifier;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

import lombok.Data;
import tn.esprit.spring.TokenProvider;
import tn.esprit.spring.Service.KinderGartenServices;
import tn.esprit.spring.Service.ParentServices;
import tn.esprit.spring.Service.RechercheKinderServices;
import tn.esprit.spring.Service.RelationServices;
import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.Advertissement;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Relation;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.AdvertissementRepository;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.UserRepository;


@Controller(value = "ProfilController")
@ELBeanName(value = "ProfilController")
public class ProfilController  {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RelationServices relationServices;
	private Parent p;
	private String firstName;
	private String lastName;
	private String image;
	private String email;
	private String adresse;
	private int tel;
	private Date dateNaissance;
	private String childName;
	private Date dateNaissanceChild;
	private String health;
	private String imageChild;
	private String search;
	private DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

	
	private String firstNameO;
	private String lastNameO;
	private String imageO;
	private String emailO;
	private String adresseO;
	private int numf;
	private Parent pO;
	List<Parent> lpO=new ArrayList<>();
	
	private int testO;
	
	
	

	
	
	private Long idChildToupdate;
	private String searchString="";

	
	List<Child> childs=new ArrayList<>();
	List<Parent> friends=new ArrayList<>();
	List<Parent> recomendedFriend=new ArrayList<>();
	
	List<Parent> parents=new ArrayList<>();
	List<String> healths=new ArrayList<>();
	List<Parent> inv=new ArrayList<>();
	List<KinderGarten> abonner=new ArrayList<>();
	List<KinderGarten> recomendidKinder=new ArrayList<>();
@Autowired
private AdvertissementRepository advertissementRepository;
	@Autowired
	private UserServices userServices;
	@Autowired
	ParentServices parentServices;
	@Autowired
	ChildRepository childRepository;
	@Autowired
	RechercheKinderServices rechercheKinderServices;
	@PostConstruct
	public void init(){
	this.parents=	 rechercheKinderServices.findallPARENTS();
	}
	
/*	public String show(Parent p){
		this.adresseO=p.getAdresse();
		this.emailO=p.getEmail();
		this.firstNameO=p.getFirstName();
		this.lastNameO=p.getLastName();
		this.imageO=p.getImage();
		this.lpO=relationServices.Myfriend(p.getUserApp()).stream().map(e->e.getParent()).collect(Collectors.toList());
		this.numf=this.lpO.size();
		this.pO=p;
		this.testO=relationServices.testfriend(userServices.currentUserJsf(), p.getUserApp());
		
	return "/pages/parent/showProfileToOther.xhtml?faces-redirect=true";	
	}
	
	public String ajouter(){
		relationServices.AjoutFriend(userServices.currentUserJsf(), this.pO.getUserApp());
		return null;
	}
	
	public String retirer(){
		relationServices.retireFriend(userServices.currentUserJsf(), this.pO.getUserApp());
		return null;
	}
	
	public String verif(){
		System.err.println("1223456");
		return "?faces-redirect=true";
		
	}
	public String recomen(){
		this.parents=rechercheKinderServices.recomendedParent(userServices.currentUserJsf());
		return null;
	}
	
	
	public void  update(){
		

		UserApp user=userServices.currentUserJsf();
		Parent pa=user.getParent();
		pa.setFirstName(firstName);
		pa.setLastName(lastName);
		pa.setTel(tel);
		pa.setEmail(email);
		pa.setAdresse(adresse);
		pa.setDateNaissance(dateNaissance);
		parentServices.update(pa);
		

	}
	
	public String AddChild(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userName;
		System.err.println("add child");
				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		Parent p=user.getParent();
		Child c=new Child();
		c.setChildName(childName);
		this.childName="";
		c.setDateNaissance(dateNaissanceChild);
		this.dateNaissanceChild=null;
		c.setImage(imageChild);
		this.imageChild="";
		c.setHealth(health);
		this.health="";
		c.setParents(p);
		childRepository.save(c);
		this.childs.add(c);
		return "/pages/parent/parentprofil.xhtml?faces-redirect=true";
		
		
	}
	
	
	public String recherch(){
		if(search==""){
			this.parents=	 rechercheKinderServices.findallPARENTS();
			return null;
		}
		this.parents=this.parents.stream().filter(e->e.getFirstName().contains(this.search) || e.getLastName().contains(this.search)).collect(Collectors.toList());
		//System.out.println();
		this.parents.forEach(c->System.out.println(c.getFirstName()));
		return null;
	}
	
	public List<Parent> getFriends() {
		
		
		List<UserApp>ls=relationServices.Myfriend(userServices.currentUserJsf());
		friends=ls.stream().map(e->e.getParent()).
				collect(Collectors.toList());
		System.err.println("friends");
		friends.forEach(f->System.err.println(f.getFirstName()));
	return	friends;

	}
	public void setFriends(List<Parent> friends) {
		this.friends = friends;
	}
	
	public String addFriend(Parent p){
		
				UserApp uS=userServices.currentUserJsf();
		UserApp uT=userRepository.findUserByParent(p);
		relationServices.AjoutFriend(uS, uT) ;
		System.err.println("addddddddddddddddddddddddddd friend");
		return "/pages/parent/showfriend.xhtml?faces-redirect=true";
	}
	
	
	public String imageUser(){
		
		UserApp user = userServices.currentUserJsf()	;	;	
		

return user.getParent().getImage();
		
	}
	public boolean imageuser(){
		
		
		

		
		UserApp user = userServices.currentUserJsf()	;
		

		if (user.getParent().getImage()== null){
			
			return false;
		}
		return true;
		
		
	}
	public String retireFriend(Parent p){
		
		
				UserApp uS=userServices.currentUserJsf();
		UserApp uT=userRepository.findUserByParent(p);
		
		 relationServices.retireFriend(uS, uT) ;
		 
		return "/pages/parent/showInvi.xhtml?faces-redirect=true"; 
	}
	
	public String accepteInvi(Parent p){
		
				UserApp uS=userServices.currentUserJsf();
		UserApp uT=userRepository.findUserByParent(p);
		if(advertissementRepository.findtargetAd(uT, uS)==null)
			throw new RuntimeException("this invitation is not registred   ");
		Advertissement av=userServices.getUserAdvertissement(uT,uS);
		
		return"/pages/parent/showInvi.xhtml?faces-redirect=true";
		
	}
	
	
	
	
	
	
	
	
	
	
	public String dispatchChild(Child c){
		this.idChildToupdate=c.getId();
		this.childName=c.getChildName();
		this.health=c.getHealth();
		this.dateNaissance=c.getDateNaissance();
		this.imageChild=c.getImage();
		
	return	"/pages/parent/addChild.xhtml?faces-redirect=true";
		
	}
	public String updateChild(){
		
		Child c=childRepository.getOne(idChildToupdate);
		
		c.setChildName(childName);
		c.setDateNaissance(dateNaissance);
		c.setImage(imageChild);
		c.setHealth(health);
		
		
		childRepository.save(c);
		
		return "/pages/parent/parentprofil.xhtml?faces-redirect=true";
		
		
	}
	
	public List<Parent> shearch(String ch){
	 this.parents=this.getParents().stream().filter(e->e.getFirstName().contains(ch) || e.getLastName().contains(ch)).collect(Collectors.toList());
	 return parents;
	}
	
	
	
	public void searchStringValueChanged(ValueChangeEvent vce)
	{
		shearch((String) vce.getNewValue());
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	

	

	

	public String getFirstName() {
	
		return this.getP().getFirstName();
	}

	public Parent getP() {
	
		UserApp user=userServices.currentUserJsf();
		Parent p=user.getParent();
		return p;
	}

	public void setP(Parent p) {
		this.p = p;
	}

	public void setFirstName(String firstName) {
		
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.getP().getLastName();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getImage() {
		return  this.getP().getImage();
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmail() {
		return  this.getP().getEmail();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresse() {
		return  this.getP().getAdresse();
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getTel() {
		return  this.getP().getTel();
	}

	public void setTel(int tel) {
		this.tel = tel;
	}

	public Date getDateNaissance() {
		return  this.getP().getDateNaissance();
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public Date getDateNaissanceChild() {
		return dateNaissanceChild;
	}

	public void setDateNaissanceChild(Date dateNaissanceChild) {
		this.dateNaissanceChild = dateNaissanceChild;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public String getImageChild() {
		return imageChild;
	}

	public void setImageChild(String imageChild) {
		this.imageChild = imageChild;
	}

	public List<Child> getChilds() {
		
		return parentServices.getMyChild(this.getP());
	}

	public void setChilds(List<Child> childs) {
		this.childs = childs;
	}

	public ParentServices getParentServices() {
		return parentServices;
	}

	public void setParentServices(ParentServices parentServices) {
		this.parentServices = parentServices;
	}

	public ChildRepository getChildRepository() {
		return childRepository;
	}

	public void setChildRepository(ChildRepository childRepository) {
		this.childRepository = childRepository;
	}
	public List<Parent> getParents() {
		return this.parents;
	}
	public void setParents(List<Parent> parents) {
		this.parents = parents;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	public List<Parent> getRecomendedFriend() {
		
				
				
				
			return	this.recomendedFriend;
	}
	public void setRecomendedFriend(List<Parent> recomendedFriend) {
		this.recomendedFriend = recomendedFriend;
	}
	public List<String> getHealths() {
		this.healths.add("MEDICALECARE");
		this.healths.add("AUTISME");
		this.healths.add("GOOD");
		
		
		return healths;
	}
	public void setHealths(List<String> healths) {
		this.healths = healths;
	}
	public Long getIdChildToupdate() {
		return idChildToupdate;
	}
	public void setIdChildToupdate(Long idChildToupdate) {
		this.idChildToupdate = idChildToupdate;
	}
	public RelationServices getRelationServices() {
		return relationServices;
	}
	public void setRelationServices(RelationServices relationServices) {
		this.relationServices = relationServices;
	}
	public List<Parent> getInv() {
		
		List<UserApp> l=advertissementRepository.findInvit(userServices.currentUserJsf() ,Relation.FRIEND);
		inv=l.stream().map(e->e.getParent()).
				collect(Collectors.toList());	
		return inv;
	}
	public void setInv(List<Parent> inv) {
		this.inv = inv;
	}
	public RechercheKinderServices getRechercheKinderServices() {
		return rechercheKinderServices;
	}
	public void setRechercheKinderServices(RechercheKinderServices rechercheKinderServices) {
		this.rechercheKinderServices = rechercheKinderServices;
	}
	public List<KinderGarten> getAbonner() {
		return relationServices.myAbonne(userServices.currentUserJsf());
	}
	public void setAbonner(List<KinderGarten> abonner) {
		this.abonner = abonner;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}

	public DateFormat getFormatter() {
		return formatter;
	}

	public void setFormatter(DateFormat formatter) {
		this.formatter = formatter;
	}

	public String getFirstNameO() {
		return firstNameO;
	}

	public void setFirstNameO(String firstNameO) {
		this.firstNameO = firstNameO;
	}

	public String getLastNameO() {
		return lastNameO;
	}

	public void setLastNameO(String lastNameO) {
		this.lastNameO = lastNameO;
	}

	public String getImageO() {
		return imageO;
	}

	public void setImageO(String imageO) {
		this.imageO = imageO;
	}

	public String getEmailO() {
		return emailO;
	}

	public void setEmailO(String emailO) {
		this.emailO = emailO;
	}

	public String getAdresseO() {
		return adresseO;
	}

	public void setAdresseO(String adresseO) {
		this.adresseO = adresseO;
	}

	
	public AdvertissementRepository getAdvertissementRepository() {
		return advertissementRepository;
	}

	public void setAdvertissementRepository(AdvertissementRepository advertissementRepository) {
		this.advertissementRepository = advertissementRepository;
	}

	public UserServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	public int getNumf() {
		return numf;
	}

	public void setNumf(int numf) {
		this.numf = numf;
	}

	public List<Parent> getLpO() {
		return lpO;
	}

	public void setLpO(List<Parent> lpO) {
		this.lpO = lpO;
	}

	public Parent getpO() {
		return pO;
	}

	public void setpO(Parent pO) {
		this.pO = pO;
	}

	public int getTestO() {
		return testO;
	}

	public void setTestO(int testO) {
		this.testO = testO;
	}

	
	public List<KinderGarten> getRecomendidKinder() {
		
		recomendidKinder=rechercheKinderServices.recomendedKinderGarten(userServices.currentUserJsf());
		return recomendidKinder;
	}

	public void setRecomendidKinder(List<KinderGarten> recomendidKinder) {
		this.recomendidKinder = recomendidKinder;
	}
	
	*/
	
	
	

}
