package tn.esprit.spring.Service;

import java.time.Period;
import java.util.Date;
import java.util.List;

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
	
	
	if(cl!=null)
	return cl.get(0);
	
	throw new RuntimeException("we dont ha ve classes");
	
	
}


public Classe ajoutKidToClasse(Child c,Classe cl)
{
	
	if(cl.getCapacitie()<=0)
		throw new RuntimeException("capaciti is max ");
	System.out.println(c.getId());
	System.out.println(c.getChildName());
	cl.getKid().forEach(e->System.out.println(e.getChildName()));
	
	if(cl.getId()==c.getClasse().getId())
		throw new RuntimeException("this kid is all ready exist");
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
	c.setClasse(cl);
	childRepository.save(c);
	
	return classeRepository.save(cl);
	
}


}
