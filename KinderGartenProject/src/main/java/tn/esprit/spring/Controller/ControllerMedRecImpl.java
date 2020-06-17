package tn.esprit.spring.Controller;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.Column;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.MedicalrecordService;
import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.MedicalRecRepository;
@Controller(value = "medreccontroller")
@ELBeanName(value = "medreccontroller")
@Join(path = "/medicalrecord1", to = "/pages/kindergarten/KinderGarten.jsf")
public class ControllerMedRecImpl {
	@Autowired
    MedicalrecordService medrec;
	@Autowired
	UserServices sc;
	MedicalRecRepository medrecrep;
	private Child childentity;
	private MedicalRec medrecentity;
	private long childid;
	private String childName;
	private String health;
	private String Allergy;
    private String Medicalprob;
	private String Medicaltreat;
	private String blood_groups;
	private float weight;
	private float height;
    private List<Child> Child;
	private List<MedicalRec> MedicalRec;
	private long idkinder;
	
	public String addMedRec(long idchild) 
	
	{String navigateTo ="/pages/parent/ChildDeashboard.xhtml?faces-redirect=true";
	
	/*long idmedrec=medrec.ajouterMedicalrecord(new MedicalRec(Allergy, Medicalprob, Medicaltreat,
			blood_groups, weight, height));*/
	if(getmedrecbyidchild(idchild)==null){
		long idmedrec=medrec.ajouterMedicalrecord(new MedicalRec(Allergy, Medicalprob, Medicaltreat,
			blood_groups, weight, height));
		medrec.affecterMedtoChild(idchild, idmedrec);}
	else{ long idmedrec=medrec.updatemederec(getmedrecbyidchild(idchild).getId(), Allergy, Medicalprob, Medicaltreat, blood_groups, weight, height);
	medrec.affecterMedtoChild(idchild, idmedrec);
	}
	return navigateTo;
	}
	public void updateText(AjaxBehaviorEvent event)
             { 
		String input =(String) ((UIOutput) event.getSource()).toString(); 
             }
	
	
	public String validate(float weight){
		if (weight>=100)
			return "do not troll";
		return null;	
	}
public String navigatetopstats(long idKinder){
		
		this.idkinder=sc.currentUserJsf().getId();
		return "/pages/kindergarten/version3.xhtml?faces-redirect=false";
	}
	public long ajouterMedicalrecord(MedicalRec medicalRec) {
		medrec.ajouterMedicalrecord(medicalRec);
		return medicalRec.getId();
	}
	
	public String navigatetopageychildid(long idchild){
		
		this.childid=idchild;
		this.childentity=afficherchildbyid(idchild);
		getmedrecbyidchild(idchild);
		return "/pages/parent/login.xhtml?faces-redirect=false";
	
	}
	public String navigatetopagefoodychildid(long idchild){
		
		this.childid=idchild;
		this.childentity=afficherchildbyid(idchild);
		getmedrecbyidchild(idchild);
		return "/pages/parent/Listfoodbychild?faces-redirect=false";
	}
	public List<MedicalRec> medrecshowall() {
		
	//	sc.currentUserJsf().getId()
		return medrec.getAllMedrec1();	
	}
	public List<Child> getall(){
		
		return medrec.getallbyidparent();

	}
	
	public MedicalRec getmedrecbyidchild(long id)
	{
		this.medrecentity=medrec.gtmedrcbyidchild(id);
		return medrecentity;
	}
	
	public long selectedchild(Child fd)
	{
	return fd.getId();
	}
	
	public Child afficherchildbyid(long idchild){
		List <Child> ch =getall();
	
		for(Child ch1:ch)
		{
			if(ch1.getId()==idchild)			
				return ch1;
		}
		return null;
	}
	public long getidchild(Child ch){
		
		return ch.getId();	
	}
	
	public long setchildid(Child ch){
		return getidchild(ch);

	}
	public MedicalrecordService getMedrec() {
		return medrec;
	}
	public void setMedrec(MedicalrecordService medrec) {
		this.medrec = medrec;
	}
	public MedicalRec getMedrecentity() {
		return medrecentity;
	}
	public void setMedrecentity(MedicalRec medrecentity) {
		this.medrecentity = medrecentity;
	}
	public String getAllergy() {
		return Allergy;
	}
	public void setAllergy(String allergy) {
		Allergy = allergy;
	}
	public String getMedicalprob() {
		return Medicalprob;
	}
	public void setMedicalprob(String medicalprob) {
		Medicalprob = medicalprob;
	}
	public String getMedicaltreat() {
		return Medicaltreat;
	}
	public void setMedicaltreat(String medicaltreat) {
		Medicaltreat = medicaltreat;
	}
	public String getBlood_groups() {
		return blood_groups;
	}
	public void setBlood_groups(String blood_groups) {
		this.blood_groups = blood_groups;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	
	/*public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}*/

	public List<MedicalRec> getMedicalRec() {
		return MedicalRec;
	}
	public void setMedicalRec(List<MedicalRec> medicalRec) {
		MedicalRec = medicalRec;
	}
	
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public void setChild(List<Child> child) {
		Child = child;
	}
	public Child getChildentity() {
		return childentity;
	}
	public void setChildentity(Child childentity) {
		this.childentity = childentity;
	}
	public List<Child> getChild() {
		return Child;
	}

	public MedicalRecRepository getMedrecrep() {
		return medrecrep;
	}

	public void setMedrecrep(MedicalRecRepository medrecrep) {
		this.medrecrep = medrecrep;
	}

	public long getChildid() {
		return childid;
	}

	public void setChildid(long childid) {
		this.childid = childid;
	}
	
	
}
