package tn.esprit.spring.Controller;



import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.AnalysticServices;
import tn.esprit.spring.Service.KinderGartenServices;
import tn.esprit.spring.Service.ParentServices;
import tn.esprit.spring.Service.RechercheKinderServices;
import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.UserApp;

@Controller(value = "AdminController")
@ELBeanName(value = "AdminController")
@EnableScheduling
public class AdminController {
	
	@Autowired
	KinderGartenServices kinderGartenServices;
	private String searchParent;
	private String searchkinderNo;
	private String searchUser;
	private String searchkinder;
	private String password;
	
	
	private Long nbreUser;
	private Long nbreParent;
	private Long nbrekinder;
	
	Map<String,Long> map=new HashMap<>();
	List<UserApp> listUser=new ArrayList<>();
	List<KinderGarten> listKinder=new ArrayList<>();
	List<KinderGarten> listKinderNoAC=new ArrayList<>();
	List<Parent> listParent=new ArrayList<>();
	@Autowired
	UserServices userServices;
	@Autowired
	RechercheKinderServices rechercheKinderServices;
	@Autowired
	ParentServices parentServices;
	@Autowired
	AnalysticServices analysticServices;
	
	
	
	
	
	
	@PostConstruct
	public void init(){
	this.listUser=userServices.	getAllUser();
	this.listKinder=userServices.getAllkinder();
	this.listParent=userServices.getAllParent();
	this.listKinderNoAC=kinderGartenServices.getallKinderNonActived();
this.map.put("Users", nbreUser());
this.map.put("Parent", nbreParent());	
this.map.put("KinderGarten", nbrekinder());	
	
	}
	
	@Scheduled(cron="0 */10 * ? * *")
    public void Scheduledevryhour() {
		this.listUser=userServices.	getAllUser();
		this.listKinder=userServices.getAllkinder();
		this.listParent=userServices.getAllParent();
		this.listKinderNoAC=kinderGartenServices.getallKinderNonActived();
		this.map.put("Users", nbreUser());
		this.map.put("Parent", nbreParent());	
		this.map.put("KinderGarten", nbrekinder());	
    }
	
	public String ActiverKinder(KinderGarten kinder){
		kinderGartenServices.activerKinder(kinder);
		System.err.println("***********************************************");
		this.listKinderNoAC.remove(kinder);
		this.listKinder.add(kinder);
		return null;
		
		
		
		
		
	}
	
	public String showpassword(UserApp u){
		System.err.println("****************************************"+u.getPassword());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
		String value="";
		encoder.matches(value, u.getPassword()); 
		System.err.println(value);
		return value;
	}
	
	
	
	public String deleteParent(Parent p){
		parentServices.deleteParent(p);	
		this.listParent.remove(p);
		return null;
	}
	
	public String deleteKinder(KinderGarten kinder){
		kinderGartenServices.deleteKinder(kinder);
this.listKinder.remove(kinder);
this.listKinderNoAC.add(kinder);
		return null;
		
		
		
		
	}

	public void searchparent(){
		if(this.searchParent.equals("")){
			this.listParent=userServices.getAllParent();
			
		}
		
		
		else
			this.listParent=this.listParent.stream().filter(e->e.getFirstName().contains(searchParent) || e.getLastName().contains(searchParent)).collect(Collectors.toList());
		
		
	}
	
	public void searchKinderNoA(){
		if(this.searchkinderNo.equals("")){
			this.listKinderNoAC=kinderGartenServices.getallKinderNonActived();

			
		}
		
		
		else
			this.listKinderNoAC=kinderGartenServices.getallKinderNonActived().stream().filter(e->e.getKinderGartenName().contains(this.searchkinderNo)).collect(Collectors.toList());;

			
			
		
		
	}
	
	
	
	public void searchKinder(){
		if(this.searchkinder.equals("")){
			this.listKinder=userServices.getAllkinder();
			
		}
		
		
		else
			this.listKinder=rechercheKinderServices.recherchKinderGarten(this.searchkinder);
		
		
	}
	public void searchUser(){
		if(this.searchUser.equals("")){
			this.listUser=userServices.getAllUser();
			
		}
		
		
		else
			this.listUser=this.listUser.stream().filter(e->e.getUsername().contains(this.searchUser)).collect(Collectors.toList());
		
		
	}

	public List<UserApp> getListUser() {
		return listUser;
	}



	public void setListUser(List<UserApp> listUser) {
		this.listUser = listUser;
	}



	public List<KinderGarten> getListKinder() {
		return listKinder;
	}



	public void setListKinder(List<KinderGarten> listKinder) {
		this.listKinder = listKinder;
	}



	public List<Parent> getListParent() {
		return listParent;
	}



	public void setListParent(List<Parent> listParent) {
		this.listParent = listParent;
	}
	public String getSearchParent() {
		return searchParent;
	}
	public void setSearchParent(String searchParent) {
		this.searchParent = searchParent;
	}
	public String getSearchUser() {
		return searchUser;
	}
	public void setSearchUser(String searchUser) {
		this.searchUser = searchUser;
	}
	public String getSearchkinder() {
		
		return searchkinder;
	}
	public void setSearchkinder(String searchkinder) {
		this.searchkinder = searchkinder;
	}
	public List<KinderGarten> getListKinderNoAC() {
		return listKinderNoAC;
	}
	public void setListKinderNoAC(List<KinderGarten> listKinderNoAC) {
		this.listKinderNoAC = listKinderNoAC;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getNbreUser() {
		return nbreUser;
	}

	public void setNbreUser(Long nbreUser) {
		this.nbreUser = nbreUser;
	}

	public Long getNbreParent() {
		return nbreParent;
	}

	public void setNbreParent(Long nbreParent) {
		this.nbreParent = nbreParent;
	}

	public Long getNbrekinder() {
		return nbrekinder;
	}

	public void setNbrekinder(Long nbrekinder) {
		this.nbrekinder = nbrekinder;
	}
	
	
	
	
	
	public Long nbreUser(){
		return (long) userServices.getAllUser().size();
	}
		
		
	public Long nbreParent(){
		return (long) userServices.getAllParent().size();
		}
		
		
		
		
	public Long nbrekinder(){
		return (long) userServices.getAllkinder().size();	
	}

		
		
	public Long NbrechildParkinder(KinderGarten k){

		return analysticServices.NbrechildParkinder(k);
		
	}

	public Long NbrechildNoAffecter(KinderGarten k){

		return analysticServices.NbrechildNoAffecter(k);
		
	}

	public String getSearchkinderNo() {
		return searchkinderNo;
	}

	public void setSearchkinderNo(String searchkinderNo) {
		this.searchkinderNo = searchkinderNo;
	}

	public Map<String, Long> getMap() {
		return map;
	}

	public void setMap(Map<String, Long> map) {
		this.map = map;
	}
	
	
	
	
	

}
