package tn.esprit.spring.Service;

import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.ClasseRepository;
import tn.esprit.spring.repository.KinderGartenRepository;

@Service
public class ClasseServices {
	
	
	
	@Autowired
	private ClasseRepository classeRepository;
	@Autowired
	private ChildRepository  childRepository;
	@Autowired
	private KinderGartenRepository kinderGartenRepository;
	
public Classe saveClasse(Classe cl,KinderGarten kinder){
	System.err.println("*******************************************"+kinder.getId());
	cl.setKinderGarten(kinder);
	int cap=kinder.getCapacite();
	kinder.setCapacite(cap+cl.getCapacitie());
	kinderGartenRepository.save(kinder);
	
		return classeRepository.save(cl);
	}

public Classe updateClasse(Classe clu,Classe cl,KinderGarten kinder){
	
	
		int cap=kinder.getCapacite();
		int newcap=clu.getCapacitie()-cl.getCapacitie();
		kinder.setCapacite(cap+newcap);
		kinderGartenRepository.save(kinder);
	
	
	return classeRepository.save(cl);
}


public void SuppClasse(Classe cl)
{
	classeRepository.delete(cl);
}


public Classe getClasseByKidAge(Child c,KinderGarten kinder){
	Classe clf=new Classe();
Date today= new Date();
	Long l=today.getTime()- c.getDateNaissance().getTime();
	Long total=(long) (1000*60*60*24);
	Long diff=l/total;
	
	int age= (int) (diff/365);
	List<Classe> cl=classeRepository.findclasseForkid(kinder, age);
	List<Classe> clfi=cl.stream().filter(e->e.getCapacitie()>0).collect(Collectors.toList());
	
	if(cl.size()>0)
	return clfi.get(0);
	
	
	//throw new RuntimeException("we dont ha ve classes");
	
	return null;
}

public List<Child> filterChild(KinderGarten kinder){
	List<Child> lch=getKidByKinder(kinder);
	
	return lch.stream().filter(e->e.getClasse()==null).collect(Collectors.toList());
}

public List<Classe> filterClasse(KinderGarten kinder){
	List<Classe> lc=getClasseBykinder(kinder);
	return lc.stream().filter(e->e.getCapacitie()>0).collect(Collectors.toList());
}


public void affectationGlobale(){
	
	List<KinderGarten> lk=kinderGartenRepository.findAll();
	List<Child> lch=new ArrayList<>();
	List<Classe> lc=new ArrayList<>();
	Classe cl =new Classe();
	for (KinderGarten kinder : lk) {
		lch=filterChild(kinder);
		for (Child child : lch) {
			
			cl=getClasseByKidAge(child,kinder);
			if(cl!=null)
			{
				ajoutKidToClasse(child,cl)	;
				
				
				
			}
		
			
		
			
			
		}
		
		
		
		
		
		
	}
	
	
	
}




















public Classe ajoutKidToClasse(Child c,Classe cl)
{
	
	if(cl.getCapacitie()<=0)
		throw new RuntimeException("capaciti is max ");
	System.out.println(c.getId());
	System.out.println(c.getChildName());
	cl.getKid().forEach(e->System.out.println(e.getChildName()));
	
	cl.getKid().add(c);
	int cp=cl.getCapacitie();
	
	cl.setCapacitie(cp-1);
	c.setClasse(cl);
	childRepository.save(c);
	
	return classeRepository.save(cl);
	
}

public Classe retirerKid(Child c,Classe cl)
{
	cl.getKid().remove(c);
	int cp=cl.getCapacitie();
	
	cl.setCapacitie(cp+1);
	c.setClasse(null);
	childRepository.save(c);
	
	return classeRepository.save(cl);
	
}

public List<Classe> getClasseBykinder(KinderGarten k){
	return classeRepository.findclasseByKinder(k);
}

public List<Child> getKidByKinder(KinderGarten k){
	return childRepository.findchildByKinder(k);
}


public List<Child> getKidByClasse(Classe c){
	return childRepository.findchildByClasse(c);
}

public Classe getClasseById(Long id){
	return classeRepository.findByid(id);
}


}
