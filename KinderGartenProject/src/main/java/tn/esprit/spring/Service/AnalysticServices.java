package tn.esprit.spring.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jdt.internal.compiler.SourceElementNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Advertissement;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Competence;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Relation;
import tn.esprit.spring.entities.Teacher;
import tn.esprit.spring.repository.AdvertissementRepository;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.ClasseRepository;
import tn.esprit.spring.repository.CompetenceRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.TeacherRepository;
import tn.esprit.spring.repository.UserRepository;
@Service
public class AnalysticServices implements IanalysticServices {
	@Autowired
	private KinderGartenRepository kinderGartenRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private  ParentRepository parentRepository;
	
	@Autowired
	private  TeacherRepository teacherRepository ;
	
	@Autowired
	private  ClasseRepository classeRepository ;
	@Autowired
	private  AdvertissementRepository advertissementRepository ;
	@Autowired
	private CompetenceRepository competenceRepository;
	@Autowired
	private ChildRepository childRepository;
	@Autowired
	private TeacherServices teacherServices;
	@Autowired
	private ClasseServices classeServices;
	@Autowired
	UserServices userServices;
	@Override
	public int calculerNbredeClasseParJardin(KinderGarten k) {
		
		List<Classe> cl=classeRepository.findclasseByKinder(k);
		
		
		System.err.println("classe"+cl.size());
		return cl.size();
	}

	@Override
	public int calculerNbredeKidsParJardin(KinderGarten k) {
		
		
		
return  classeServices.getKidByKinder(k).size();
		
	}

	@Override
	public int calculerNbredeTeacherParJardin(KinderGarten k) {
		
		return teacherServices.getTeachers(k).size();
		
	}

	@Override
	public Map<Long, Object> calculerNbreabonneParJardin(KinderGarten k) {
	List<Advertissement> adl=advertissementRepository.findRelation(k.getUserapp(),Relation.ABONNE);
		Map<Long, Object> map=new HashMap<>();;
		map.put(k.getId(),adl.size());
		
		return map;
	}

	@Override
	public Map<Long, Object> estimerNbreDeInscription(KinderGarten k) {
		
		return null;
	}

	@Override
	public Map<String, Object> estimerMonqueDeClasseParNbreparCapacite(KinderGarten k) {
     List<Classe> cl=classeRepository.findclasseByKinder(k);
     Map<String, Object> map=new HashMap<>();;
		
     for(Classe c:cl){
    	 if(c.getKid().size()>10)
    		 map.put(c.getNom(),c.getKid().size());
     }
     
     
		return map ;
	}

	@Override
	public Map<String, Object> estimerMonqueDeTeacher(KinderGarten k) {
		 List<Classe> cl=classeRepository.findclasseByKinder(k);
		 Map<String, Object> map=new HashMap<>();;
			
	     for(Classe c:cl){
	    	 if(c.getTeacher()==null)
	    		 map.put(c.getNom(),"NO TEACHER");
	     }
	     
	     
			return map ;
		 
		 
		 
	
	}
	
	
	
	public Map<Long, Object> estimerMonqueDeTeacherCompetence(KinderGarten k) {
		 List<Classe> cl=classeRepository.findclasseByKinder(k);
		 Map<Long, Object> map=new HashMap<>();
		 Long i=0L;
			List<Child> lch=new ArrayList<Child>();
			Set<String> set=new HashSet<>();
					for(Classe c:cl){
						System.out.println(c.getNom());
	    	 if(c.getTeacher()==null){
	    		 lch=childRepository.findchildByClasse(c);
	    		 for(Child ch :lch){
	    			 System.out.println(ch.getChildName());
	    			 if(!ch.getHealth().equals("GOOD")){
	    				 set.add(ch.getHealth());
	    				 System.out.println(ch.getHealth());
	    			 }
	    		 }
	    		 for(String w:set){
	    			i++;
					 map.put(i,w);
	    		 }
	    		 set.clear();
	    	 }
	     }
	     
					
					
	     
			return map ;
		 
		 
		 
	
	}
	
	public Map<String, Object> estimerNombreHealthproblem(KinderGarten k) {
		 int nbre=0;
		 Map<Long, Object> map=estimerMonqueDeTeacherCompetence(k);
		 Set<String> ws=new HashSet<>();
			Map<String, Object> maper=new HashMap<>();;

		 for (Map.Entry mapentry : map.entrySet()) {
			 if(!ws.contains((String) mapentry.getValue()))
				 for (Map.Entry mapentry1 : map.entrySet()) {
					 if(mapentry.getValue()==mapentry1.getValue())
			              nbre++; 
					 }
			 
			 
			 maper.put( (String) mapentry.getValue(), nbre);
			 ws.add((String) mapentry.getValue());
			 nbre=0;
	    
		 }
	     
			return maper ;
	
	}
	
	
	public Map<String, Object> estimerDeTeacherCompetenceValable(KinderGarten k) {
		 List<Teacher> tl=teacherServices.showteaches(k);
		 List<Competence> Cl=competenceRepository.findAll();
		 Map<String, Object> map=new HashMap<>();
			Set<String> set=new HashSet<>();
			int nbreC=0;
			for(Competence c:Cl){
                for(Teacher t:tl){
                	if(t.getCompetences().contains(c))
                		nbreC++;
													
					}
	    	
					map.put(c.getCompetenceName(), nbreC);
			}
			return map ;
	
	}
	
	
	
	
	

	@Override
	public Map<Long, Object> estimerkidshealth(KinderGarten k) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Long, Object> estimerScoreParJardin(KinderGarten k) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public Map<String, String> NbreClasseStableForJsf(KinderGarten k) {
		Map<String, String> map=new HashMap<>();
		Map<Long, String> mapt=NbreClasseStable(k);
		Classe cl=new Classe();
		for (Map.Entry mapentryC : mapt.entrySet()) {
			cl=classeRepository.findByid( (Long) mapentryC.getKey());
			
			
			map.put(cl.getNom(), (String) mapentryC.getValue());
			
		}
		
	return map;	
	}
	
	
	
	
	
	
	
	public Map<Long, String> NbreClasseStable(KinderGarten k) {
		Map<Long, String> map=new HashMap<>();
		List<Classe> lc=classeRepository.findclasseByKinder(k);
		Map<Long, Object> mapt=new HashMap<>();
		Teacher t=new Teacher();
		for (Classe classe : lc) {
			mapt.putAll(verifiClasseStable(classe));	
		}
		for (Map.Entry mapentryC : mapt.entrySet()) {
			if((Long) mapentryC.getValue()==0L)
			{
				map.put( (Long) mapentryC.getKey(), "No de teacher ");}
			else if((Long) mapentryC.getValue()==1L)
			{
				map.put( (Long) mapentryC.getKey(), "Stable");}
			else if((Long) mapentryC.getValue()==2L)
			{
				map.put( (Long) mapentryC.getKey(), "non stable your teacher need formation ");}
			else
			{
				t=teacherRepository.findById((Long) mapentryC.getValue()).get();
				map.put( (Long) mapentryC.getKey(), "Non stable recomended  teacher : "+t.getNom()+" phone Number :"+ t.getNumtel());
			}
				
		}
		
		
		return map;
	}
	
	
	public Map<String, Boolean> techerCompetence(Teacher t) {
		Map<String, Boolean> map=new HashMap<>();
		List<Competence> lt=competenceRepository.findAll();
		
		
		for (Competence competence : lt) {
			if(t.getCompetences().contains(competence))
				map.put(competence.getCompetenceName(), true);
			else
				map.put(competence.getCompetenceName(), false);
		}
		
		return map;
	}
	
	public Map<String, Boolean> ClasseCompetence(Classe c) {
		Map<String, Boolean> map=new HashMap<>();
		List<Child> lt=childRepository.findchildByClasse(c);
		
		for (Child ch : lt) {
			if(ch.getHealth().equals("AUTISME"))
				map.put("AUTISME", true);
			if(ch.getHealth().equals("MEDICALECARE"))
				map.put("MEDICALECARE", true);
			
		}
		if(c.getAge()<=2)
			map.put("BABYCARE", true);
		return map;
	}

	public Map<String, Boolean> CompaireClasseToteacher(Teacher t,Classe c){
		Map<String, Boolean> mapt=techerCompetence( t);
		Map<String, Boolean> mapC=ClasseCompetence( c);
		Map<String, Boolean> map=new HashMap<>();
		
		 for (Map.Entry mapentryC : mapC.entrySet()) {
			 for (Map.Entry mapentryT : mapt.entrySet()) {
				 if(mapentryC.getKey().equals(mapentryT.getKey()))
					 if(!(boolean) mapentryT.getValue())
						 map.put((String) mapentryT.getKey(), false);
					 
			 } 
		 }
		
		 return map;
		
	}
	
public 	boolean verif(Teacher t,Classe c){
	Map<String, Boolean> map=CompaireClasseToteacher(t,c);
	 if(map.isEmpty())
		 return true;
	 
	 return false;
	}


	public Map<Long, Object> verifiClasseStable(Classe c){
		Map<Long, Object> mapt=new HashMap<>();
		Long i=0L;
		if(c.getTeacher()==null){
			List<Teacher> lt=teacherRepository.findByKinder(c.getKinderGarten());
			lt=(List<Teacher>) lt.stream().filter(e->e.getClasse()==null).collect(Collectors.toList());
		 //while((!verif( lt.get(i), c))|| i<lt.size())
		    //   i++;
			
			for (Teacher teacher : lt) {
				if(verif( teacher, c))
                      i=teacher.getId();
			}
			
		 
		 if(i>lt.size())
			 mapt.put(c.getId(),0L);
		 
		 mapt.put(c.getId(),i);
			 
		}
		
		
		else
		{
			if(verif( c.getTeacher(), c))
		mapt.put(c.getId(),1L);
			
			mapt.put(c.getId(),2L);
		}
		 return mapt;
		
	}
	
	
	public Classe getRecomendedClasse(Child c ,KinderGarten k){
		Map<String, String> map=new HashMap<>();
		Map<Long, Object> mapt=getClasseForkidswithout(k);
		Classe cl=new Classe();
		
		for (Map.Entry mapentryC : mapt.entrySet()) {
			if((Long) mapentryC.getKey()==c.getId())
				cl=classeRepository.findByid( (Long) mapentryC.getValue());
			
		}
		
		return cl;
		
	}
	

	
	
	public Map<String, String> getClasseForkidswithoutForJsf(KinderGarten k) {
		Map<String, String> map=new HashMap<>();
		Map<Long, Object> mapt=getClasseForkidswithout(k);
		Classe cl=new Classe();
		Child c=new Child();
		for (Map.Entry mapentryC : mapt.entrySet()) {
			cl=classeRepository.findByid( (Long) mapentryC.getValue());
			c= childRepository.findById((Long) mapentryC.getKey()).get();
			
			map.put(c.getChildName(), cl.getNom());
			
		}
		
	return map;	
	}
	
	// a implementer
	public Map<Long, Object> getClasseForkidswithout(KinderGarten k) {
		Map<Long, Object> map=new HashMap<>();

		List<Child> lc=childRepository.findchildByKinder(k);
		lc=(List<Child>) lc.stream()
				.filter(c->c.getKindergarten()==null)
				.collect(Collectors.toList());
		List<Classe> listClasse= classeRepository.findclasseByKinder(k);
		Classe clas=new Classe();
		for (Child child : lc) {
			for(Classe classe:listClasse)
			{
				if(verifClasseForChild(child,classe))
					clas=classe;
			}
			map.put(child.getId(), clas.getId());
			
		}
		
		return map;
	}
	
	public boolean verifClasseForChild(Child c, Classe classe)
	{Date today= new Date();
		Long l=today.getTime()-c.getDateNaissance().getTime();
		Long total=(long) (1000*60*60*24);
		Long	diff=l/total;
		int	age= (int) (diff/365);
		if(age==classe.getAge())
		{
			if(classe.getCapacitie()>0)
				return true;
		}
		
		return false;
		
	}
	
	//main one 
	public Map<Long, Map<String, List<String>>> MykinderTeacherCompetenceMonque(KinderGarten kinder){
		Map<Long,Map<String, List<String>>> map=new HashMap<>();
		List<Classe> listClasse=classeRepository.findclasseByKinder(kinder);
		for (Classe classe : listClasse) {
			if(classe.getTeacher()!=null)
				map.put(classe.getId(),MyteacherMonqueCompetence(classe) );
		}
		
		return map;
		
		
	}
	
	
	
	
	public Map<String, List<String>> MyteacherMonqueCompetence(Classe c) {
		Map<String,List<String>> map=new HashMap<>();	
		List<String> lc=new ArrayList<>();
		if(c.getTeacher()==null){
			map.put("No teacher", lc);
			 return map;
			
		}
			
		
		Map<String, Boolean> mapt=techerCompetence( c.getTeacher());
		Map<String, Boolean> mapC=ClasseCompetence( c);
		for (Map.Entry mapentryC : mapC.entrySet()) {
			 for (Map.Entry mapentryT : mapt.entrySet()) {
				 if(mapentryC.getKey().equals(mapentryT.getKey()))
					 if(!(boolean) mapentryT.getValue())
						 lc.add((String) mapentryC.getKey());
					 
			 } 
			 
			 
			 
		 }
		System.err.println();
		lc.forEach(e-> 		System.err.println("competence "+e)
);
		map.put(c.getTeacher().getNom(), lc);
		
		 return map;
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

	return (long) childRepository.findchildByKinder(k).size();
	
}

public Long NbrechildNoAffecter(KinderGarten k){

	return (long) childRepository.findAll().stream().filter(e->e.getKindergarten()==null).collect(Collectors.toList()).size();
	
}
	





















public List<KinderGarten> topKinder(){
	List<KinderGarten> lk=kinderGartenRepository.findAll().stream().sorted((e1 , e2)->
		verif(e1)-verif(e2)).collect(Collectors.toList());
	
	return lk  ;
}

	
	
public int verif(KinderGarten k)	{
	List<Classe> lc=classeServices.getClasseBykinder(k);
	int nubreclasse=lc.size();
	int note=0;
	if(NumbreClasseNocharger(k)>(nubreclasse*0.7))
		note=note+5;
	if(NumbreClasseNoPlein(k)>(nubreclasse*0.7))
		note=note+2;
	if(Numbredeteacher(k))
		note=note+3;
	if(numberDeClasseParAge(k))
		note=note+5;
	if(NumbreClasseStable(k)>(nubreclasse*0.7))
		note=note+5;
	
	return 0;
}
	
public int NumbreClasseNocharger(KinderGarten k)	{
	List<Classe> lc=classeServices.getClasseBykinder(k);
	int i=0;
	for (Classe classe : lc) {
		if(childRepository.findchildByClasse(classe).size()<10){
			i++;
		}
			
	}
	return i;	
	
	
}	
public int NumbreClasseNoPlein(KinderGarten k)	{
	List<Classe> lc=classeServices.getClasseBykinder(k);
	int i=0;
	for (Classe classe : lc) {
		if(classe.getCapacitie()>0)
			i++;
			
	}
	return i;	
	
	
}
public boolean Numbredeteacher(KinderGarten k)	{

List<Teacher> lt=teacherRepository.findByKinder(k);
List<Classe> lc=classeServices.getClasseBykinder(k);

if(lt.size()>lc.size())
	return true;
	
return false;	
	
}
	
	
public boolean numberDeClasseParAge(KinderGarten k)	{
	List<Classe> lc=classeServices.getClasseBykinder(k);
	int i=0;
	Set<Integer> setAge=new HashSet<>();
	
	for (Classe classe : lc) {
		
		setAge.add(classe.getAge());
	}
	for(i=0;i<6;i++)
	{
		if(!setAge.contains(i))
			return false;
	}
	
	
	return true;	
	
	
}
	
public int NumbreClasseStable(KinderGarten k)	{
	int i=0;
	List<Classe> lc=classeRepository.findclasseByKinder(k);
	Map<Long, Object> mapt=new HashMap<>();
	Teacher t=new Teacher();
	for (Classe classe : lc) {
		mapt.putAll(verifiClasseStable(classe));	
	}
	for (Map.Entry mapentryC : mapt.entrySet()) {
		
		 if((Long) mapentryC.getValue()==1L)
		{
		i++	 ;
		}
		}
	
return i;	
}

}
