package tn.esprit.spring.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.Service.AnalysticServices;
import tn.esprit.spring.Service.ClasseServices;
import tn.esprit.spring.Service.RegisterUser;
import tn.esprit.spring.Service.TeacherServices;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Competence;
import tn.esprit.spring.entities.Teacher;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.ClasseRepository;
import tn.esprit.spring.repository.TeacherRepository;
import tn.esprit.spring.repository.UserRepository;

@RestController
@Secured(value={"ROLE_KINDERGARTEN"})

public class GestionClasseController {
@Autowired


private ClasseServices classeServices;
@Autowired
private TeacherServices teacherServices;
@Autowired 
private UserRepository userRepository;
@Autowired
private ClasseRepository classeRepository;
@Autowired
private ChildRepository childRepository;
@Autowired
private AnalysticServices analysticServices;

@RequestMapping(value="/ajoutclasse",method=RequestMethod.POST)

public Classe ajoutclasse(@RequestBody Classe cl,HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	if(user.getKindergarten()==null)
		throw new RuntimeException("your are not a kindergarten .");
	cl.setKinderGarten(user.getKindergarten());
	
	return classeServices.saveClasse(cl,user.getKindergarten());
	
	}


@RequestMapping(value="/modifierClasse",method=RequestMethod.POST)
public Classe modifierclasse(@RequestBody Classe cl,HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	if(!user.getKindergarten().getClasses().contains(cl))
		throw new RuntimeException("this classe is note yours");
	Classe cle=new Classe();
	for(Classe cli : user.getKindergarten().getClasses())
	{
		if(cli.getId().equals(cl.getId()))
			cle=cli;
	}
		
	return classeServices.updateClasse(cl,cle,user.getKindergarten());
	
}

@RequestMapping(value="/suppclasse/{idK}",method=RequestMethod.POST)
public String suppclasse(@RequestBody Classe cl,HttpServletRequest request,@PathVariable Long idK){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	UserApp target=userRepository.getOne(idK);
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	if(!user.getKindergarten().equals(target.getKindergarten()))
		throw new RuntimeException("your are not a kindergarten .");
	
	 classeServices.SuppClasse(cl);
	 return"Suppression avec success";
	
	}

@RequestMapping(value="/affectKid",method=RequestMethod.POST)
public Classe affectKid(@RequestBody Child c,HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	
	Child  child=childRepository.getOne(c.getId());
	
	Classe cl=classeServices.getClasseByKidAge(child, user.getKindergarten());
	if(cl==null)
		throw new RuntimeException("we dont have classe for kid.");
	
	return classeServices.ajoutKidToClasse(child, cl);
	}

@RequestMapping(value="/saveTeacher",method=RequestMethod.POST)
public Teacher saveTeacher(@RequestBody Teacher t,HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	
	
	
	t.setKinderGarten(user.getKindergarten());
	return teacherServices.saveTeacher(t);
	
	
	}
@RequestMapping(value="/showMyTeachers",method=RequestMethod.POST)
public List<Teacher> showMyTeachers(HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	
	
	
	
	return teacherServices.showteaches(user.getKindergarten());
	
	
	}



@RequestMapping(value="/PutteacherInClasse/{idC}",method=RequestMethod.POST)
public Teacher PutteacherInClasse(@RequestBody Teacher t,HttpServletRequest request,@PathVariable Long idC){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	if(user.getKindergarten()==null)
		throw new RuntimeException("your are not a kindergarten .");
	Classe cl=classeRepository.getOne(idC);
	if(user.getKindergarten().getClasses().contains(cl))
		throw new RuntimeException("your dont have  this class .");
	
	
	if( classeRepository.findTeacher(idC)!=null)
		throw new RuntimeException("this class have teacher .");
	
	System.out.println("cl :"+cl.getNom());
	
	
	return teacherServices.affecttacherToClasse(t, cl);
	
	
	}


@RequestMapping(value="/updateTeacher",method=RequestMethod.POST)
public Teacher updateTeacher(@RequestBody Teacher t,HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	if(user.getKindergarten()==null)
		throw new RuntimeException("your are not a kindergarten .");
	
	
	
	
	return teacherServices.updateTeacher(t);
	
	
	}
@RequestMapping(value="/deleteTeacher",method=RequestMethod.POST)
public String deleteTeacher(@RequestBody Teacher t,HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	if(user.getKindergarten()==null)
		throw new RuntimeException("your are not a kindergarten .");
	
	
	
	
	 teacherServices.delateTeacher(t);
	return "Suppression teacher !!";
	
	}







@RequestMapping(value="/recomendedeTeacher/{idc}",method=RequestMethod.POST)
public List<Teacher> recomendedeTeacher(HttpServletRequest request,@PathVariable Long idc){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	
	//System.out.println(user.getUsername());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	if(user.getKindergarten()==null)
		throw new RuntimeException("your are not a kindergarten .");
	
	
	Classe cl=classeRepository.findByid(idc);
	//System.out.println(cl.getKinderGarten().getKinderGartenName());
	if(!user.getKindergarten().equals(cl.getKinderGarten()))
		throw new RuntimeException("this classe does not saved in your kinderGarten");
	//System.out.println(cl.getKinderGarten().getKinderGartenName());
	user.getKindergarten().getTeachers().forEach(e->System.out.println("teachername :"+e.getNom()));
	return teacherServices.getTeacherTheMostAdecttied(user.getKindergarten(), cl);
	
	
	}

@RequestMapping(value="/saveTeacherCompetence/{idt}",method=RequestMethod.POST)
public Teacher saveTeacher(@RequestBody Competence c,@PathVariable Long idt,HttpServletRequest request){
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
	
	return teacherServices.saveTeacherCompetence(c, idt);
	
	
	}




@RequestMapping(value="/showCompetence",method=RequestMethod.POST)
public List<Competence> showCompetence(){
	
	
	return teacherServices.showCompetences();
	
	
	}

@RequestMapping(value="/test123",method=RequestMethod.POST)
public Map<Long, String> test(HttpServletRequest request){
	
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	return analysticServices.NbreClasseStable(user.getKindergarten());
	
	
	}










}
