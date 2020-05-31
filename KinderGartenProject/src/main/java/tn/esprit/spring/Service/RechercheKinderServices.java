package tn.esprit.spring.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Adresse;
import tn.esprit.spring.entities.Advertissement;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.RechercheMenu;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.AdvertissementRepository;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.ClasseRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.RechercheMenuRepository;
import tn.esprit.spring.repository.UserRepository;
@Service
public class RechercheKinderServices {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private KinderGartenRepository kinderGartenRepository;
	@Autowired
	private ClasseRepository classeRepository;
	@Autowired
	private ChildRepository childRepository;
	@Autowired
	private AdvertissementRepository advertissementRepository;
	@Autowired
	private RechercheMenuRepository rechercheMenuRepository;
	public List<KinderGarten> getKinderByAdresse(Adresse ad)
	{
		
		List<KinderGarten> lk =kinderGartenRepository.findAll();
		
		if(ad.getCountry()!=null)
		 lk=	(List<KinderGarten>) lk.stream()
				.filter(k->k.getAdresse().contains(ad.getCountry())).collect(Collectors.toList());
		if(ad.getVille()!=null)
			 lk=	(List<KinderGarten>) lk.stream()
				.filter(k->k.getAdresse().contains(ad.getVille())).collect(Collectors.toList());
		if(ad.getVillage()!=null)
					 lk=	(List<KinderGarten>) lk.stream()		
				.filter(k->k.getAdresse().contains(ad.getVillage())).collect(Collectors.toList());
		if(ad.getRue()!=null)
					 lk=	(List<KinderGarten>) lk.stream()		
				.filter(k->k.getAdresse().contains(ad.getRue())).collect(Collectors.toList());
		
		return lk;
		
		
	}
	
	
	public List<KinderGarten> findall(){

		List<KinderGarten> lk =kinderGartenRepository.recherchKinder();
		return lk;
	}
	
	
	public List<KinderGarten> recherchKinderGarten(String kinder)
	{
		
		List<KinderGarten> lk =kinderGartenRepository.recherchKinder();
		
		
		
		
	return lk.stream().filter(k->k.getKinderGartenName().contains(kinder)).collect(Collectors.toList());
		
		
	}
	
	
	public List<KinderGarten> getKinderByRechercheMenu(RechercheMenu rm,UserApp user)
	{
		
		List<KinderGarten> lk =kinderGartenRepository.findAll();
		
		Adresse ad =new Adresse();
		
		
	
		if(rm.getCountry()!=null)
		 lk=	(List<KinderGarten>) lk.stream()
				.filter(k->k.getAdresse().contains(rm.getCountry())).collect(Collectors.toList());
		if(rm.getVille()!=null)
			 lk=	(List<KinderGarten>) lk.stream()
				.filter(k->k.getAdresse().contains(rm.getVille())).collect(Collectors.toList());
		if(rm.getVillage()!=null)
					 lk=	(List<KinderGarten>) lk.stream()		
				.filter(k->k.getAdresse().contains(rm.getVillage())).collect(Collectors.toList());
		if(rm.getRue()!=null)
					 lk=	(List<KinderGarten>) lk.stream()		
				.filter(k->k.getAdresse().contains(rm.getRue())).collect(Collectors.toList());
		
		
		if(rm.getMaxprix()!=null)
			lk=	(List<KinderGarten>) lk.stream()		
			.filter(k->k.getPrix()<rm.getMaxprix()).collect(Collectors.toList());
		if(rm.getMinprix()!=null)
			lk=	(List<KinderGarten>) lk.stream()		
			.filter(k->k.getPrix()>rm.getMinprix()).collect(Collectors.toList());
		
		if(rm.isTrieByscore())
			lk=	(List<KinderGarten>) lk.stream().sorted((e1,e2)->e2.getUserapp().getScore()-e1.getUserapp().getScore())		
			.collect(Collectors.toList());
		
		rm.setUserapp(user);
		lk.forEach(a->		System.err.println(a.getKinderGartenName()));
		rechercheMenuRepository.save(rm);
		return lk;
		
		
	}
	public List<KinderGarten> recomendedKinderGarten(UserApp user){
		
		List<KinderGarten> lk=kinderGartenRepository.recherchKinder();
		lk=	lk.stream().filter(e->parentEstimatedScore(user.getParent())<=(KindergartenScore(e, user.getParent()) )).sorted((k1,k2)->k1.getUserapp().getScore()-k2.getUserapp().getScore()).collect(Collectors.toList());
		
		
		
		
		
		return lk;
	}
	
	
	
	public float parentEstimatedScore(Parent p){
		
		int childscore=0;
		int adresseScore=0;
		int prixScore=0;
		
		for(Child c:p.getChilds()){
			childscore++;
			if(c.getKindergarten()!=null)
			prixScore++;
		}
		
		String[] adlP=p.getAdresse().split(" ");	
		for(String wp:adlP)	{
			
			adresseScore++;
		}
		
		
		System.out.println("parent :"+p.getFirstName()+" score :"+(((10*childscore+3*prixScore+5*adresseScore)*0.7)));
		
		return  (float) (((10*childscore+3*prixScore+5*adresseScore)*0.7));	
		
	}
	
	
	
	
	
	public float KindergartenScore(KinderGarten k,Parent p){
		int childscore=0;
		int adresseScore=0;
		int prixScore=0;
		int kidage=0;
		Date today= new Date();
		Long l=null;
		Long total=null;
		Long diff=null;
		List<Classe> classeList=new ArrayList<>();
		int age=0;
		
		
		for (Child c : p.getChilds()) {
			l=today.getTime()-c.getDateNaissance().getTime();
			 total=(long) (1000*60*60*24);
				diff=l/total;
				age= (int) (diff/365);
				if(c.getKindergarten()!=null)
					if(c.getKindergarten().getPrix()-k.getPrix()>=0)
						prixScore++;
				System.out.println("age :"+age);
				classeList=classeRepository.findclasseByKinder(k);
				for (Classe cl : classeList) {
					System.out.println("classe age :"+cl.getAge());
					
					
					if(cl.getAge()==age && cl.getCapacitie()>0)
						childscore++;
				}
					}
		
		
		
String[] adlP=p.getAdresse().split(" ");
String[] adlK=k.getAdresse().split(" ");
		
		
for(String wp:adlP)	{
	for(String wk:adlK)	{
		if(wp.contains(wk))
			adresseScore++;
	}
}

System.out.println("kinder :"+k.getKinderGartenName()+" adresseScore :"+adresseScore);

System.out.println("kinder :"+k.getKinderGartenName()+" prixScore :"+prixScore);
System.out.println("kinder :"+k.getKinderGartenName()+" childscore :"+childscore);



System.out.println("kinder :"+k.getKinderGartenName()+" score :"+(10*childscore+3*prixScore+5*adresseScore));
	return 	(10*childscore+3*prixScore+5*adresseScore);
	
		
	}
	public List<KinderGarten> trierParPrix(){
	
		List<KinderGarten> lk=kinderGartenRepository.findAll();
		lk=	(List<KinderGarten>) lk.stream().sorted((e1,e2)->(int) (e2.getPrix()-e1.getPrix()))		
				.collect(Collectors.toList());
		
		return lk;
	}
	
	
	
	
	
	
	public List<Parent> recomendedParent(UserApp u){
//		System.err.println("main parent"+u.getParent().getFirstName());
		List<Parent> lp=new ArrayList<>();
		List<Classe> lc=childRepository.findchildClasseByparent(u.getParent());
		lc.forEach(c->{
			
			lp.addAll(childRepository.findchildParentByClasse(c));
		});
//	lp.forEach(c->{
//		System.err.println("show parents 1 : "+c.getFirstName());});
//	
		while(lp.contains(u.getParent()))
	lp.remove(u.getParent());
		

		Iterator<Parent> iterator2 = lp.iterator();
		while (iterator2.hasNext()) {
			
			if(iterator2.next().getId()==u.getParent().getId())
			iterator2.remove(); // On supprime l'élément courant
		}
	
		Iterator<Parent> iterator = lp.iterator();
		while (iterator.hasNext()) {
			while(!verifDuplex(lp, iterator.next()))

			iterator.remove(); // On supprime l'élément courant
		}
	
//	lp.forEach(c->{
//		System.err.println("show parents 2 : "+c.getFirstName());});
//	
	lp.forEach(e->{
		System.err.println("test 4 "+e.getFirstName());
	});
	return lp ;	
	}
	
	
	public boolean verifDuplex(List<Parent> lp,Parent p)
	{
		int i=0;
		
		for (Parent parent : lp) {
			if(parent.getId()== p.getId())
				i++;
		}
		
		if(i>1)
			return false;
		
		return true;
	}
	
/*	
public List<KinderGarten>	 getRecomendedSearch(UserApp user){
	
	List<RechercheMenu> lr=rechercheMenuRepository.findByUser(user);
	
	RechercheMenu r=new RechercheMenu();
	for (RechercheMenu rechercheMenu : lr) {
		
	}
	
	
}
	
public 	RechercheMenu getRechercheMenumostUserd(List<RechercheMenu> lr){
	
	
	
	
}
	
	
	
	*/
	
	public List<Parent> findallPARENTS(){
		List<Parent> pl=userRepository.findAll().stream().filter(e->
			e.getParent() != null && e.isActived()
		).map(k->k.getParent()).collect(Collectors.toList());
	return pl;	
	}
	
}
