package tn.esprit.spring.Service;


import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.KinderGarten;
/*import tn.esprit.spring.entities.MENUOFTHEDAY;*/
import tn.esprit.spring.entities.MedicalRec;
import tn.esprit.spring.entities.callories;
import tn.esprit.spring.entities.foodmedrecwithgramage;
import tn.esprit.spring.entities.foodsandtheircallories;
import tn.esprit.spring.repository.CalloriesRepository;
import tn.esprit.spring.repository.CategoryRepository;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.FoodmedrecwithgramageRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.MedicalRecRepository;
import tn.esprit.spring.repository.MenucategoryRepository;

/*import tn.esprit.spring.repository.RestaurationRepository;*/
import tn.esprit.spring.repository.foodsandtheircalloriesRepository;

@Service
public class RestaurationService {


	
	@Autowired 
	KinderGartenRepository kinder;
	
	@Autowired
	MedicalrecordService medrecservice;
	
	@Autowired
	MedicalRecRepository medrec;

	@Autowired
	ChildRepository child;

	@Autowired
	CategoryRepository cat;
	
	@Autowired
	CalloriesRepository calory;
	
	@Autowired
	MenucategoryRepository menucat;
	
	@Autowired
	foodsandtheircalloriesRepository food;
	
    @Autowired
	FoodmedrecwithgramageRepository foodgrammage;
	
    @Autowired
    UserServices sc;
	public void affecterchildbyCategory(long id_mdrec)
	{
		
		
	}
	
	public void ajouterMenuinfo() {
		List<foodsandtheircallories> fd =food.findAll();
		for (foodsandtheircallories fb:fd)
		{
			
			food.delete(fb);
		}
		try 
		{
		 for(int i=1;i<=2;i++){
			Document doc=Jsoup.connect("https://www.myfitnesspal.com/nutrition-facts-calories/arabic-food/"+i)
					.header("Accept-Encoding", "utf-8").timeout(6000).get();

			 Elements elment = (Elements) doc.select("div.main-2ZMcp");

			for(Element info:elment )
			{
					
				Elements foodgroup= info.select("div.jss4");
			
				for(Element info2:foodgroup){
					String foodname = info2.select("div.jss8").text();
					
					Elements calories= info2.select("div.jss7");
					   {
						for(Element info3:calories){
							foodsandtheircallories foodgroupe=new foodsandtheircallories();
							String calorivalue=info3.getElementsByTag("h1").text();
							foodgroupe.setCallories(calorivalue);
			        		foodgroupe.setFoodgroup(foodname);
			        		food.save(foodgroupe);
			        		System.out.println(foodname +" "+calorivalue);
						}	
					}
				}
			}
			
		 } }catch (Exception ex){
			ex.printStackTrace();
		}
		}
	@Transactional
	public void affecterfoodtokindergarten(long idkinder){

		idkinder=sc.currentUserJsf().getKindergarten().getId();
		List<foodsandtheircallories> fd =food.findAll();
		KinderGarten kindertoaffect=kinder.findById(idkinder).get();
		for(foodsandtheircallories food:fd){

			if(food.getKinderGarten() == null){

				List<KinderGarten> kd = new ArrayList<>();
				kd.add(kindertoaffect);
				food.setKinderGarten(kd);
			}else
				food.getKinderGarten().add(kindertoaffect);
			
		}	
	}
	
	@Transactional
	public void affectmedicalrectofood(long idkinder,long idfood) throws ParseException{
		idkinder=sc.currentUserJsf().getKindergarten().getId();
		List<foodsandtheircallories> foods=food.foodbyidkinder(idkinder);
		List<MedicalRec> medicals=child.getAllmedicalrecdbykindergartene(idkinder);
		foodsandtheircallories onefood=food.findById(idfood).get();

			for(MedicalRec onemed:medicals)
			{	foodmedrecwithgramage fb=new foodmedrecwithgramage();
				
	           callories cal=onemed.getCallories();
	           float vluecaloriesofonemed=cal.getValue();
	         
	           float valueofcaloryofonefood=(float)Float.parseFloat(onefood.getCallories());
	           
	           float xvalue=vluecaloriesofonemed/valueofcaloryofonefood;
	           
	           long idofmenucategory=onemed.getMenucategory().getId();
	           	           
	           Date date = new Date();
	           String strDateFormat = "yyyy-MM-dd hh:mm:ss";
	           DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	           String formattedDate= dateFormat.format(date);
	           Date date1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(formattedDate);  

	           if(idofmenucategory==1)
	           
	           {
	        	if( xvalue==1)
	        	{
	        	fb.setFoodsandtheircallories(onefood);
	        	fb.setGramsneeded(10+100);
	        	fb.setMedicalRec(onemed);
	        	fb.setPublishedDate(date1);
	        	foodgrammage.save(fb);	
	        	}   
	         
	           else if(xvalue < 1)
	           {
	        	   fb.setFoodsandtheircallories(onefood);
		        	fb.setGramsneeded(100/xvalue+10);
		        	fb.setMedicalRec(onemed);
		        	fb.setPublishedDate(date1);
		        	foodgrammage.save(fb);	
	        	   
	           }
	           else if(xvalue >1)
	           {
	        	   fb.setFoodsandtheircallories(onefood);
		        	fb.setGramsneeded(100*xvalue+10);
		        	fb.setMedicalRec(onemed);
		        	fb.setPublishedDate(date1);
		        	foodgrammage.save(fb);	
	        	   
	           }
	           }
	           else if (idofmenucategory ==2)
	           {
	        	   if( xvalue==1)
		        	{
		        	fb.setFoodsandtheircallories(onefood);
		        	fb.setGramsneeded(100-10);
		        	fb.setMedicalRec(onemed);
		        	fb.setPublishedDate(date1);
		        	foodgrammage.save(fb);	
		        	}   
		         
		           else if(xvalue < 1)
		           {
		        	   fb.setFoodsandtheircallories(onefood);
			        	fb.setGramsneeded(100/xvalue-10);
			        	fb.setMedicalRec(onemed);
			        	fb.setPublishedDate(date1);
			        	foodgrammage.save(fb);	
		        	   
		           }
		           else if(xvalue >1)
		           {
		        	   fb.setFoodsandtheircallories(onefood);
			        	fb.setGramsneeded(100*xvalue-10);
			        	fb.setMedicalRec(onemed);
			        	fb.setPublishedDate(date1);
			        	foodgrammage.save(fb);	
		        	   
		           } 
	           }
	           else if (idofmenucategory ==3)
	           {
	        	   if( xvalue==1)
		        	{
		        	fb.setFoodsandtheircallories(onefood);
		        	fb.setGramsneeded(100);
		        	fb.setMedicalRec(onemed);
		        	fb.setPublishedDate(date1);
		        	foodgrammage.save(fb);	
		        	}   
		           else if(xvalue < 1)
		           {
		        	   fb.setFoodsandtheircallories(onefood);
			        	fb.setGramsneeded(100/xvalue);
			        	fb.setMedicalRec(onemed);
			        	fb.setPublishedDate(date1);
			        	foodgrammage.save(fb);	   
		           }
		           else if(xvalue >1)
		           {
		        	   fb.setFoodsandtheircallories(onefood);
			        	fb.setGramsneeded(100*xvalue);
			        	fb.setMedicalRec(onemed);
			        	fb.setPublishedDate(date1);
			        	foodgrammage.save(fb);	   
		           } 
	           }      
		}	
	}
	
	public List<foodsandtheircallories> fd(){
		
		long idkinder=sc.currentUserJsf().getKindergarten().getId();
		return food.foodbyidkinder(idkinder);
		
	}
	
	public List<foodmedrecwithgramage> listfoodsgrmmagebykinder()
	{
		long idkinder=sc.currentUserJsf().getKindergarten().getId();
		return foodgrammage.foodbyidkinder(idkinder);

	}
	
	public List<foodmedrecwithgramage> byidchild(long id){
		
		return foodgrammage.foodbychildid(id);
		
	}
	public List<foodsandtheircallories> foodcalorbyidchild2(long id){

		return foodgrammage.foodcalorbychildid(id);
	
	}
}
	
	
	
	
	
	

	
	

