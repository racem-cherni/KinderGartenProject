package tn.esprit.spring.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.Service.ParentServices;
import tn.esprit.spring.Service.RechercheKinderServices;
import tn.esprit.spring.Service.RegisterUser;
import tn.esprit.spring.Service.VoteServecies;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.RechercheMenu;
import tn.esprit.spring.entities.RoleApp;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.entities.Vote;
import tn.esprit.spring.entities.VotePK;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.VoteRepository;

@RestController

public class ParentController {
	
@Autowired
private RechercheKinderServices rechercheKinderServices;
@Autowired
private ParentRepository parentRepository;
@Autowired
private ChildRepository childRepository;
@Autowired
private UserRepository userRepository;
@Autowired
private KinderGartenRepository kinderGartenRepository;
@Autowired
private VoteRepository voteRepository;
@Autowired
private  VoteServecies voteServecies;
@Autowired
private ParentServices parentServices;


@RequestMapping(value="/saveUser/saveParent/{username}",method=RequestMethod.POST)
public Parent saveParent(@RequestBody Parent p,@PathVariable String username){
UserApp u=userRepository.findByUsernametest(username);

	return parentServices.addParent(p,u);
}
@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/addChildToParent",method=RequestMethod.POST)
public Parent addChildToParent(@RequestBody Child c,HttpServletRequest request ){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	Parent p1=user.getParent();

if(p1==null)
	throw new RuntimeException("you are not a parent");

c.setParents(p1);
p1.getChilds().add(c);

	return parentRepository.save(p1);
}
@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/getMyChild",method=RequestMethod.GET)
public List<Child>  getMyChild(HttpServletRequest request ){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	Parent p1=user.getParent();
List<Child> l=new ArrayList<>();
if(p1==null)
	throw new RuntimeException("you are not a parent");
p1.getChilds().forEach(e->{
	l.add(e);
});


	return l;
}
@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/getMyKidKinderGarten",method=RequestMethod.GET)
public List<KinderGarten>  getMyKidKinderGarten( HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	Parent p1=user.getParent();
List<KinderGarten> l=new ArrayList<>();
if(p1==null)
	throw new RuntimeException("you are not a parent");
p1.getChilds().forEach(e->{
	l.add(e.getKindergarten());
});

return l;
}

//show my profile
@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/showMyProfileP")
public Parent showMyProfileP(HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
if(user==null)
		throw new RuntimeException("user doesn t exist .");
Boolean test=false;
for(RoleApp r:user.getRoles())
	if(r.getRoleName().equals("ROLE_PARENT"))
		test=true;

if(test==false)
	throw new RuntimeException("you are not a Parent");
else 
	return user.getParent();

	
}








@Secured(value={"ROLE_ADMIN"})
@RequestMapping(value="/showParent")
public List<Parent> listUser(){
	return parentRepository.findAll();
}

//achange
@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/updateParent",method=RequestMethod.POST)
public Parent updateParent(@RequestBody Parent p){
	Parent pr=parentRepository.getOne(p.getId());
	if(pr==null)
		throw new RuntimeException("this parent don't existe .");
	
	pr.setAdresse(p.getAdresse());
	pr.setDateNaissance(p.getDateNaissance());
	pr.setEmail(p.getEmail());
	pr.setFirstName(p.getFirstName());
	pr.setImage(p.getImage());
	pr.setTel(p.getTel());
	
	
	
	return parentRepository.save(pr);
}

/// secured admin 
@Secured(value={"ROLE_ADMIN"})
@RequestMapping(value="/deleteParent",method=RequestMethod.DELETE)
public void deleteParent(@RequestBody Parent p){
	Parent pr=parentRepository.getOne(p.getId());
	if(pr==null)
		throw new RuntimeException("this parent don't existe .");
	 parentRepository.delete(p);
}	

@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/addchildToParent",method=RequestMethod.POST)
public Parent addchildToParent(@RequestBody Child c,HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	Parent pr=user.getParent();
	if(pr==null)
		throw new RuntimeException("this parent don't existe .");
	c.setParents(pr);
	childRepository.save(c);
	pr.getChilds().add(c);
return	parentRepository.save(pr);
	
}


// a tester
@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/vote/{kusername}",method=RequestMethod.POST)
public Vote vote(HttpServletRequest request,@RequestBody Vote v,@PathVariable("kusername") String kusername){
	Principal principal = request.getUserPrincipal();
	UserApp Parent=userRepository.findByUsername(principal.getName());
	UserApp kinder=userRepository.findByUsername(kusername);
	KinderGarten k=kinderGartenRepository.getOne(kinder.getKindergarten().getId());
	
	boolean test =false;
	for (Child c :  Parent.getParent().getChilds()) {
		
		if(c.getKindergarten().equals(k))
			test=true;
		
	}
	if(test==false)
	 throw new RuntimeException("this kindergarten not for kids ");
	
	
	KinderGarten kinderV=kinderGartenRepository.findKindergartenNotVoted(Parent.getParent(),k);
	
	if (k.equals(kinderV))
	{
		throw new RuntimeException("you all ready vote !");
	}
	
	VotePK vpk=new VotePK(k.getId(),Parent.getParent().getId());
	Vote vf= new Vote(vpk,k,Parent.getParent(),v.getNote(),new Date());
	
	
	voteServecies.calculScore(k,v.getNote());
	
	return voteRepository.save(vf);
	
	
}



//verifier 
@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/chercheKinderGartenMenu",method=RequestMethod.POST)
public List<KinderGarten>  chercheKinderGartenMenu( HttpServletRequest request,@RequestBody RechercheMenu rm){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	List<KinderGarten> l=rechercheKinderServices.getKinderByRechercheMenu(rm,user);
return l;
}

// verifier 
@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/recomendedKinderGarten",method=RequestMethod.POST)
public List<KinderGarten>  recomendedKinderGarten( HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
List<KinderGarten> l=rechercheKinderServices.recomendedKinderGarten(user);
return l;
}

//verifier

@RequestMapping(value="/trieParPrix",method=RequestMethod.POST)
public List<KinderGarten>  trieParPrix( HttpServletRequest request){
	
List<KinderGarten> l=rechercheKinderServices.trierParPrix();
return l;
}


@Secured(value={"ROLE_PARENT"})
@RequestMapping(value="/recomendedfriend",method=RequestMethod.POST)
public List<Parent>  recomendedfriend( HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
List<Parent> l=rechercheKinderServices.recomendedParent(user);
return l;
}








}
