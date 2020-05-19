package tn.esprit.spring.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.Service.RechercheKinderServices;
import tn.esprit.spring.entities.Adresse;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.RoleApp;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.UserRepository;

@RestController


public class KinderGartenController {
@Autowired
private KinderGartenRepository kinderGartenRepository;
@Autowired
private UserRepository userRepository;
@Autowired
private ParentRepository parentRepository;
@Autowired
private ChildRepository childRepository;
@Autowired
private RechercheKinderServices rechercheKinderServices;


@RequestMapping(value="/saveKinderGarten/{username}",method=RequestMethod.POST)
public KinderGarten saveKinderGarten(@RequestBody KinderGarten k,@PathVariable String username){
UserApp u=userRepository.findByUsernametest(username);
boolean test=false;
for(RoleApp r:u.getRoles())
{
	if(r.getRoleName().equals("ROLE_KINDERGARTEN"))
		test=true;
}
if(test==false)
	throw new RuntimeException("you are not a KINDERGARTEN");
	
k.setUserapp(u);
	return kinderGartenRepository.save(k);
}
@Secured(value={"ROLE_KINDERGARTEN"})
@RequestMapping(value="/addKidToKinderGarten/{idk}",method=RequestMethod.POST)
public KinderGarten addChildToParent(@RequestBody Child c,@PathVariable Long idk ){
	KinderGarten p1=kinderGartenRepository.getOne(idk);

if(p1==null)
	throw new RuntimeException("you are not a KINDERGARTEN");

p1.getKid().add(c);

	return kinderGartenRepository.save(p1);
}


//this methode show kindergarten kids 
@Secured(value={"ROLE_KINDERGARTEN"})
@RequestMapping(value="/showKindergartenKids/{idk}",method=RequestMethod.GET)
public List<Child> showKids(@PathVariable Long idk){
	KinderGarten k1=kinderGartenRepository.getOne(idk);
	List<Child> l=new ArrayList<>();
	k1.getKid().forEach(e->{
		l.add(e);
	});
	return l;
	
}
//   recherche kinder garten 
@RequestMapping(value="/recherche/{vrecherch}",method=RequestMethod.POST)
public List<KinderGarten> showKidsParents(@PathVariable String vrecherch){
	List<KinderGarten> lk=rechercheKinderServices.recherchKinderGarten(vrecherch);
	
	return lk;
	
}
//recherche by adresse

@RequestMapping(value="/rechercheAd",method=RequestMethod.POST)
public List<KinderGarten> showKidsParents(@RequestBody Adresse ad){
	List<KinderGarten> lk=rechercheKinderServices.getKinderByAdresse(ad);
	
	return lk;
	
}
//////


//this methode get my kids parents 
@Secured(value={"ROLE_KINDERGARTEN"})
@RequestMapping(value="/showKindergartenKidsParent/{idk}",method=RequestMethod.GET)
public List<Parent> showKidsParents(@PathVariable Long idk){
	KinderGarten k1=kinderGartenRepository.getOne(idk);
	List<Parent> l=childRepository.findParentKidsKindergarten(k1);
	
	return l;
	
}
//this methode get this kid parent 
@Secured(value={"ROLE_KINDERGARTEN"})
@RequestMapping(value="/showKindergartenKidParent/{id}",method=RequestMethod.GET)
public Parent getThisKidParent(@PathVariable Long id){
	
	Parent p=childRepository.findParentKidKindergarten(id);
	
	return p;
	
}

@Secured(value={"ROLE_KINDERGARTEN"})
@RequestMapping(value="/showMyProfileK")
public KinderGarten showMyProfileK(HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
if(user==null)
		throw new RuntimeException("user doesn t exist .");
Boolean test=false;
for(RoleApp r:user.getRoles())
	if(r.getRoleName().equals("ROLE_KINDERGARTEN"))
		test=true;

if(test==false)
	throw new RuntimeException("you are not a Parent");
else 
	return user.getKindergarten();

	
}



@Secured(value={"ROLE_KINDERGARTEN"})
@RequestMapping(value="/showKinderGarten")
public List<KinderGarten> listKinderGarten(){
	return kinderGartenRepository.findAll();
}
@Secured(value={"ROLE_KINDERGARTEN"})
@RequestMapping(value="/updateKinderGarten",method=RequestMethod.POST)
public KinderGarten updateParent(@RequestBody KinderGarten k){
	KinderGarten kin=kinderGartenRepository.getOne(k.getId());
	if(kin==null)
		throw new RuntimeException("this parent don't existe .");
	kin.setAdresse(k.getAdresse());
	kin.setEmail(k.getEmail());
	kin.setKinderGartenName(k.getKinderGartenName());
	kin.setTel(k.getTel());
	kin.setPrix(k.getPrix());
	return kinderGartenRepository.save(kin);
}
@Secured(value={"ROLE_ADMIN"})
@RequestMapping(value="/deleteKinderGarten",method=RequestMethod.DELETE)
public void deleteKinderGarten(@RequestBody KinderGarten k){
	KinderGarten kin=kinderGartenRepository.getOne(k.getId());
	if(kin==null)
		throw new RuntimeException("this parent don't existe .");
	kinderGartenRepository.delete(k);
}	
	



































}
