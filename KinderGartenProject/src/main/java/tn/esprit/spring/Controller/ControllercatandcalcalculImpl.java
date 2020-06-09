package tn.esprit.spring.Controller;

import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.MedicalrecordService;
import tn.esprit.spring.entities.MedicalRec;
import tn.esprit.spring.entities.Child;

@Controller(value = "catcontrol")
@ELBeanName(value = "catcontrol")
@Join(path = "/childdashboard1", to = "/pages/parent/login.jsf")

public class ControllercatandcalcalculImpl {
	@Autowired
	MedicalrecordService medrec;
	private MedicalRec medrecentity;
	private Child childentity;
	private List<MedicalRec> medreclist;

	public String settallmedrecandtheircategories()
	{
	long idkinder=1;
		medrec.medicalCategory(idkinder);
		medrec.setcalloriesbychildrenofkindergarten(idkinder);
		medrec.setmedicalrecordbycaloriesandcategory(idkinder);
		return "?faces-redirect=false";
	}
public String medinstruction(){
	
	return("/pages/kindergarten/KinderGarten.xhtml?faces-redirect=true");
}

public String returntofood(){
	return("/pages/kindergarten/scrapandlistfoods.xhtml?faces-redirect=false");
	          
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

	public Child getChildentity() {
		return childentity;
	}

	public void setChildentity(Child childentity) {
		this.childentity = childentity;
	}

	public List<MedicalRec> getMedreclist() {
		return medreclist;
	}

	public void setMedreclist(List<MedicalRec> medreclist) {
		this.medreclist = medreclist;
	}

	
	
	

}
