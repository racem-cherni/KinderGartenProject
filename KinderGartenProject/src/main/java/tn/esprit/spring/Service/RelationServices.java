package tn.esprit.spring.Service;

import tn.esprit.spring.entities.Relation;
import tn.esprit.spring.entities.RoleApp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Advertissement;
import tn.esprit.spring.entities.AdvertissementPK;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.AdvertissementRepository;
import tn.esprit.spring.repository.UserRepository;
@Service

public class RelationServices {
	
	
	@Autowired
	private AdvertissementRepository advertissementRepository;
	@Autowired
	private UserRepository userRepository;
	
	
public  Advertissement AjoutFriend(UserApp sourceUser,UserApp targetUser){
		
	AdvertissementPK adpk=new AdvertissementPK(sourceUser.getId(),targetUser.getId());

	Advertissement ad=new Advertissement(adpk,Relation.FRIEND);

		return advertissementRepository.save(ad);
	
	}

	
	
	
	public  void retireFriend(UserApp sourceUser,UserApp targetUser){
	Advertissement ad=	advertissementRepository.findAdByTargetetSource(targetUser, sourceUser);
		advertissementRepository.delete(ad);
		
		
	}
	
	
	
public  Advertissement BloqueFriend(UserApp sourceUser,UserApp targetUser){
	Advertissement ad=	advertissementRepository.findAdByTargetetSource(targetUser, sourceUser);
	
	if(ad==null)
	{
		AdvertissementPK adpk=new AdvertissementPK(sourceUser.getId(),targetUser.getId());

		Advertissement adv=new Advertissement(adpk,Relation.BLOCK);

			return advertissementRepository.save(adv);
	}
	
		ad.setRelation(Relation.BLOCK);
		return advertissementRepository.save(ad);
	
		
	}


public  Advertissement abonnerAKinderGarten(UserApp sourceUser,UserApp targetUser){
	
	
boolean test =false;
/*for (RoleApp r : targetUser.getRoles() ) {
	if(r.getRoleName().equals("KINDERGARYEN"))
		test=true;
	}
	*/
	if(targetUser.getKindergarten()==null)
		throw new RuntimeException("target nest pas kindergarten !!"); 
	
	
	AdvertissementPK adpk=new AdvertissementPK(sourceUser.getId(),targetUser.getId());

	Advertissement adv=new Advertissement(adpk,Relation.ABONNE);
adv.setActive(true);
		return advertissementRepository.save(adv);
	
	
}
public  void DesabonnerAKinderGarten(UserApp sourceUser,UserApp targetUser){
	
	


	Advertissement adv=advertissementRepository.findAd(sourceUser, targetUser);

		advertissementRepository.delete(adv);
	
	
}


public List<KinderGarten> myAbonne(UserApp user){
	
	List<UserApp> l=advertissementRepository.findUserRelation(user).stream().filter( j ->
	j.getRelation().equals(Relation.ABONNE) && j.isActive()==true && !j.getTargetUser().equals(user)
	).map(e->
	e.getTargetUser()
	).collect(Collectors.toList());//.forEach(e->System.out.println("user : "+e.toString()));


	l.addAll(advertissementRepository.findUserRelation(user).stream().filter( j ->
	j.getRelation().equals(Relation.ABONNE) && j.isActive()==true && !j.getSourceUser().equals(user) 
	).map(e->
	e.getSourceUser()
	).collect(Collectors.toList()));


	return	l.stream().map(e->e.getKindergarten()).collect(Collectors.toList());
	
	}
public Boolean testAbonner(UserApp user,UserApp target){
	List<UserApp> l=advertissementRepository.findUserRelation(user).stream().filter( j ->
	j.getRelation().equals(Relation.ABONNE) && j.isActive()==true && !j.getTargetUser().equals(user)
	).map(e->
	e.getTargetUser()
	).collect(Collectors.toList());//.forEach(e->System.out.println("user : "+e.toString()));


	l.addAll(advertissementRepository.findUserRelation(user).stream().filter( j ->
	j.getRelation().equals(Relation.ABONNE) && j.isActive()==true && !j.getSourceUser().equals(user) 
	).map(e->
	e.getSourceUser()
	).collect(Collectors.toList()));
	
	for (UserApp u : l) {
		if(u.getId()==target.getId())
			return true;
	}
	return false;
}


public List<UserApp> Myfriend(UserApp user){
	
	List<UserApp> l=advertissementRepository.findUserRelation(user).stream().filter( j ->
	j.getRelation().equals(Relation.FRIEND) && j.isActive()==true && !j.getTargetUser().equals(user)
	).map(e->
	e.getTargetUser()
	).collect(Collectors.toList());//.forEach(e->System.out.println("user : "+e.toString()));


	l.addAll(advertissementRepository.findUserRelation(user).stream().filter( j ->
	j.getRelation().equals(Relation.FRIEND) && j.isActive()==true && !j.getSourceUser().equals(user) 
	).map(e->
	e.getSourceUser()
	).collect(Collectors.toList()));


	return	l;
	
	}







}
