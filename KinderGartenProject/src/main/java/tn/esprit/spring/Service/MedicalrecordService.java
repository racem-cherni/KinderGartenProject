package tn.esprit.spring.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.MedicalRec;
import tn.esprit.spring.entities.callories;
import tn.esprit.spring.entities.categories;
import tn.esprit.spring.entities.foodmedrecwithgramage;
import tn.esprit.spring.entities.foodsandtheircallories;
import tn.esprit.spring.entities.menucategory;
import tn.esprit.spring.repository.CalloriesRepository;
import tn.esprit.spring.repository.CategoryRepository;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.FoodmedrecwithgramageRepository;
import tn.esprit.spring.repository.MedicalRecRepository;
import tn.esprit.spring.repository.MenucategoryRepository;
import tn.esprit.spring.repository.foodsandtheircalloriesRepository;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.KinderGarten;

@Service
public class MedicalrecordService {

	@Autowired
	private MedicalRecRepository medrec;

	@Autowired
	private ChildRepository child;

	@Autowired
	CategoryRepository cat;
	
	@Autowired
	CalloriesRepository calory;
	
	@Autowired
	MenucategoryRepository menucat;
	
	@Autowired
	foodsandtheircalloriesRepository food;
	
	@Autowired
	MedicalRecRepository merep;
	
	@Autowired
	FoodmedrecwithgramageRepository foodgrammage;
	
	@Autowired 
	UserServices sc;

	public List<MedicalRec> getAllMedrec() {
		return (List<MedicalRec>) medrec.findAll();
	}

	public List<MedicalRec> getAllMedrec1() {
		long id=sc.currentUserJsf().getKindergarten().getId();
		return (List<MedicalRec>) child.getAllmedicalrecdbykindergartene(id);
	}

	public long ajouterMedicalrecord(MedicalRec medicalRec) {
		medrec.save(medicalRec);
		return medicalRec.getId();
	}
	
	public void ajouterfoodsandtheircallory(foodsandtheircallories fd) {
		food.save(fd);
		
	}

	public void affecterMedtoChild(long idchild, long idmedrec) {
		List<MedicalRec> list=medrec.findAll();
		for(MedicalRec meden:list){
			if(meden.getChild()==child.findById(idchild).get()){
				medrec.delete(meden);
				
			}
		}
		Child ch = child.findById(idchild).get();
		MedicalRec med = medrec.findById(idmedrec).get();

		ch.setMedicalRec(med);
		child.save(ch);
	}
	public List<Child> getallbyidparent(){

		long idparent=sc.currentUserJsf().getParent().getId();
		return child.getAllchildbyparentid(idparent);
		
	}
	/*
	 * public List<Child> getAllMedrecbyweight(KinderGarten idkinder ) { return
	 * (List<Child>) medrec.getAllchildbywieghtandkindergarten(idkinder); }
	 */

	public void deletlMedRec(long idmedrec) {
		MedicalRec medrec1 = medrec.findById(idmedrec).get();
		medrec.delete(medrec1);
	}

	public long updatemederec(long id,String allergy, String prob, String Medicaltreat,
			String blood_groups,float weight,float height){
		MedicalRec medrec1 = medrec.findById(id).get();
		if(medrec1==null) {
			ajouterMedicalrecord(medrec1);
			medrec.save(medrec1);
			return medrec1.getId();

		}
	//	
		else{
		medrec1.setAllergy(allergy);
		medrec1.setMedicalprob(prob);
		medrec1.setMedicaltreat(Medicaltreat);
		medrec1.setBlood_groups(blood_groups);
		medrec1.setWeight(weight);
		medrec1.setHeight(height);

		medrec.save(medrec1);
		return medrec1.getId();
		}
	}

	public List<Integer> getallchildbyallergy(String allergy) {
		return child.getAllchildbyallergy(allergy);

	}
public MedicalRec gtmedrcbyidchild(long idchild){
	return merep.foodbyidkinder(idchild);
}
	public List<Child> getallchildbykindergarten() 
	{   long id=sc.currentUserJsf().getKindergarten().getId();
		return child.getAllchildbykindergarten(id);

	}

	
	public void medicalCategory() {
		long idkindergarten=sc.currentUserJsf().getKindergarten().getId();
		List<MedicalRec> listofmedicalrec = getAllMedrec1();
			
		categories cat1 =cat.findById((long)1).get();
		categories cat2 =cat.findById((long)2).get();
		categories cat3 =cat.findById((long)3).get();
		categories cat4 =cat.findById((long)4).get();
		categories cat5 =cat.findById((long)5).get();
		
		float idealpoids;

		float IMC;
	
		for (MedicalRec med : listofmedicalrec) {
			/* La formule de Lorentz */
			idealpoids = med.getHeight() - 100 - ((med.getHeight() - 150) / 4);
			/* calcul avec indice de masse corporelle */
			 IMC = (float) (med.getWeight() / (Math.pow(med.getHeight()/100, 2)));
			if (med.getAllergy().equals("Gluten")) {
				/* "gluten"*/ med.setCategroy(cat1);
			}
			else if (IMC > 18.5 && IMC < 25) {
				 /*"poids normal"*/;
				 med.setCategroy(cat2);
			} else if (IMC < 18.5) {
				med.setCategroy(cat3);
				 /*"insuffisance pondérale"*/;
			} else if (IMC > 25 && IMC < 29.9) {
			 /*"surpoids"*/
				med.setCategroy(cat4);
			} else if (IMC > 30) {
				/* "obésité";*/
				med.setCategroy(cat5);
			}
			medrec.save(med);
		}
		
	}
	
	public void setcalloriesbychildrenofkindergarten()
	
	{
		long idkindergarten=sc.currentUserJsf().getKindergarten().getId();
		List <callories> gt =calory.findAll();
		for(callories ct:gt)
		{
			
		calory.delete(ct);
		}
		/*Calcul de ses besoins caloriques journaliers (Déjeuner 40% des besoins formule en tenant compte 
		 * que tout les enfants du jardin d'enfant sont de sexualité masculine car c relier avec la base qu'on a oublié de 
		 * specifier le sexe de l'enfant dans les attributs)*/
		List<MedicalRec> listofmedicalrec = getAllMedrec1();
		
		float calories ;
		for (MedicalRec med : listofmedicalrec) 
		
		{ 
			callories callor = new callories();
			
			calories=(float)(((13.7516 * med.getWeight())+(500.33 * (med.getHeight())/100))-(6.7550 * 4)+66.473)*40/100;
			callor.setValue(calories);
			callor.setMedicalRec(med);
			calory.save(callor);
		}
		
	}
	
/*add class named (affectchildbyhiscalloriesandcategory to a 1 of the 3 categories of +-10% calori or stable calory regime*/ 
/**/	@Transactional
	public void setmedicalrecordbycaloriesandcategory()
	{
	long idkindergarten=sc.currentUserJsf().getKindergarten().getId();
		List<MedicalRec> listofmedicalrec = getAllMedrec1();
		categories cat1 =cat.findById((long)1).get();
		categories cat2 =cat.findById((long)2).get();
		categories cat3 =cat.findById((long)3).get();
		categories cat4 =cat.findById((long)4).get();
		categories cat5 =cat.findById((long)5).get();
		
		menucategory menucat1=menucat.findById((long) 1).get();
		menucategory menucat2=menucat.findById((long) 2).get();
		menucategory menucat3=menucat.findById((long) 3).get();
		menucategory menucat4=menucat.findById((long) 4).get();
		
		for (MedicalRec med : listofmedicalrec) 

		{ 

		  if(med.getCategroy() == cat1 )
		  {
			 
			  med.setMenucategory(menucat4);
			  
		  }
		  else if(med.getCategroy() == cat2 ){
			  
			  med.setMenucategory(menucat3);
		  }
		  else if(med.getCategroy() == cat3 )
		  {
			  med.setMenucategory(menucat1);
  
		  }
		  else if ((med.getCategroy() == cat4) || (med.getCategroy() == cat5)){
			  med.setMenucategory(menucat2);
			  
		  }
		  medrec.save(med);
		}
		
		
	}
	
	/*public void setbycalloriesandgroup(String url)
	{
		 try 
	        {
	        	Document doc=Jsoup.connect("http://166.62.103.147/~ashesics/class2017/ayeley_commodore/webtech%20practicals/projim1/uploaded_docs/"+url).userAgent("mozila/17.0").get();
	        	/*http://166.62.103.147/~ashesics/class2017/ayeley_commodore/webtech%20practicals/projim1/uploaded_docs/firo.html
	        	
	        	Elements elment = (Elements) doc.select("table tr");
	        	
	        	
	        	for(Element info:elment ){
	        		
	        		foodsandtheircallories foodgroupe=new foodsandtheircallories();

	        		String foodgroup= info.select("td:nth-of-type(1)").text();
	        		String calories=info.select("td:nth-of-type(3)").text();
	        		
	        		foodgroupe.setCallories(calories);
	        		foodgroupe.setFoodgroup(foodgroup);
	        		food.save(foodgroupe);
	        		
	        		System.out.println(foodgroup + " "+ calories);

	        		}
	        	
	        }catch (Exception ex){
	        	ex.printStackTrace();
	        }	
		 }
	*/
	
	/*public void setbycalloriesandgroup(){

		try 
		{
		 for(int i=1;i<10;i++){
			Document doc=Jsoup.connect("https://www.myfitnesspal.com/nutrition-facts-calories/arabic-food/"+i).
					timeout(6000).get();
			Elements elment = (Elements) doc.select("div.main-2ZMcp");
			
			
			
			for(Element info:elment ){
				
					
				Elements foodgroup= info.select("div.jss4");
			
				for(Element info2:foodgroup){
					String foodname = info2.select("div.jss8").text();
					byte[] foodname2 = foodname.getBytes();
					String asciiEncodedString = new String(foodname2, StandardCharsets.UTF_8);
					Elements calories= info2.select("div.jss7");
					{
						for(Element info3:calories){
							foodsandtheircallories foodgroupe=new foodsandtheircallories();
							String calorivalue=info3.getElementsByTag("h1").text();
							foodgroupe.setCallories(calorivalue);
			        		foodgroupe.setFoodgroup(asciiEncodedString);
			        		food.save(foodgroupe);
			        		System.out.println(foodname +" "+calorivalue);
						}
						
					}
				}
				}
			
		 } }catch (Exception ex){
			ex.printStackTrace();
		}
		}*/
public int calcnbrchildbyallergy(String glut){
	long idkinder=sc.currentUserJsf().getKindergarten().getId();
	return medrec.calculnbrallegrybychild(idkinder,glut);
	
	
}
public int calcnbrinfct(String glut){

	long idkinder=sc.currentUserJsf().getKindergarten().getId();
	return medrec.calculnbrinfection(idkinder,glut);
	
	
}
public int calcnbrblood(String blood){

	long idkinder=sc.currentUserJsf().getKindergarten().getId();
	return medrec.calculnbralblood(idkinder,blood);
	
	
}


public float getsumgrams(Date date){
	return foodgrammage.calculgramsforthisday(date);		
	
}
public List<foodmedrecwithgramage> getallbydate(){
	

	long idkinder=sc.currentUserJsf().getKindergarten().getId();
	return foodgrammage.foodbyidkinder(idkinder);
}


}


