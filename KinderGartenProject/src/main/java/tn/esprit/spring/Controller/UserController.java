package tn.esprit.spring.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import tn.esprit.spring.CurrentUser;
import tn.esprit.spring.Service.AccountService;
import tn.esprit.spring.Service.RegisterUser;
import tn.esprit.spring.Service.RelationServices;
import tn.esprit.spring.Service.Session;
import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.Advertissement;
import tn.esprit.spring.entities.AdvertissementPK;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.ModifierPassword;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Relation;
import tn.esprit.spring.entities.RoleApp;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.entities.VerificationToken;
import tn.esprit.spring.repository.AdvertissementRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.RoleRepository;
import tn.esprit.spring.repository.UserRepository;

@RestController

public class UserController {
@Autowired
private UserRepository userRepository;
@Autowired
private RoleRepository roleRepository;
@Autowired
private ParentRepository parentRepository;
@Autowired
private AccountService accountService;
@Autowired
private AdvertissementRepository advertissementRepository;
@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder ;
@Autowired
private UserServices userServices;
@Autowired
private KinderGartenRepository kinderGartenRepository;
@Autowired
private RelationServices relationServices;
@Autowired
private Session session;

@RequestMapping(value="/saveUser/{role}",method=RequestMethod.POST)
public UserApp saveUser(@RequestBody RegisterUser user,@PathVariable String role){
	if(!user.getCfpassword().equals(user.getPassword()))
		throw new RuntimeException("! you must confirm your password");
	UserApp usertest=userRepository.findByUsername(user.getUsername());
	if(usertest!=null)
		throw new RuntimeException("! this username is alReady used.");
	
	RoleApp r=roleRepository.findByRoleName(role);
	Collection<RoleApp> roles= new ArrayList<>();
	if(role.equals("ROLE_KINDERGARTEN")){
		
		UserApp u=new UserApp(user.getUsername(),user.getPassword(),roles,false,user.getScore(),3);
		roles.add(r);
		return accountService.saveUser(u);
	}
	else if(role.equals("ROLE_PARENT")){
		
		roles.add(r);
		UserApp u=new UserApp(user.getUsername(),user.getPassword(),roles,false,user.getScore(),3);
		
		return accountService.saveUser(u);
		}
	else
		throw new RuntimeException("! this role don't exist !!! .");
	/*else{
		UserApp u=new UserApp(user.getUsername(),user.getPassword(),roles,false,user.getScore(),3);
		roles.add(r);
		return accountService.saveUser(u);
	}*/


}

//activer kindergarten 
@RequestMapping(value="/showKinderGartenAdmin")
public List<KinderGarten> listKinderGartenAdmin(){
	
return (List<KinderGarten>) kinderGartenRepository.getkinderforAdmin();
}
///// en panne 
@RequestMapping(value="/activerKinderGartenAdmin")
public String listKinderGartenAdmin(@RequestBody KinderGarten k){
	KinderGarten kinder=kinderGartenRepository.getOne(k.getId());
	System.out.println("username"+kinder.getKinderGartenName());
	
	kinder.getUserapp().setActived(true);
	
	userRepository.save(kinder.getUserapp());
	return "modification avec sucess" ;
	
}







/////////////////////////////
@Secured(value={"ROLE_ADMIN"})
@RequestMapping(value="/showUser")
public List<UserApp> listUser(){
	return userRepository.findAll();
}
// mes amies
@RequestMapping(value="/showFriends")
public List<UserApp> listuserFriends(HttpServletRequest request){
	
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
if(user==null)
		throw new RuntimeException("user doesn t exist .");


return	relationServices.Myfriend(user);
	
}
// mes abonnes
@RequestMapping(value="/myAbonne")
public List<KinderGarten> listuserabonnes(HttpServletRequest request){
	
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
if(user==null)
		throw new RuntimeException("user doesn t exist .");


return	relationServices.myAbonne(user);
	
}



@RequestMapping(value = "/username", method = RequestMethod.GET)
@ResponseBody
public String currentUserNameSimple(HttpServletRequest request) {
   UserApp user=session.session(request);
    return user.getUsername();
}



@Secured(value={"ROLE_ADMIN"})

@RequestMapping(value="/getSession")
public Map<String,Object> getLogedUser(HttpServletRequest httpServletRequest){
	HttpSession httpSession=httpServletRequest.getSession();
	SecurityContext securityContext=(SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
	String username=securityContext.getAuthentication().getName();
	List<String> role=new ArrayList<>();
		for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()){
			role.add(ga.getAuthority());
		}
		
		Map<String,Object> param=new HashMap<>();
		param.put("username",username);
		param.put("role",role);
		return param;
		
}

//modifier password

@RequestMapping(value="/modifierPassword",method=RequestMethod.POST)
public String  ModifyPassword(HttpServletRequest request,@RequestBody ModifierPassword Md){
	Principal principal = request.getUserPrincipal();
	
	if(!principal.getName().equals(Md.getUsername()))
		throw new RuntimeException("you are not the same username !!  ");
	if(!Md.getPassword().equals(Md.getCfpassword()))
		throw new RuntimeException("the password and confirme one not the same  !!  ");
	
	UserApp uS=userRepository.findByUsername(principal.getName());
	String hash=bCryptPasswordEncoder.encode(Md.getPassword());
	uS.setPassword(hash);
	userRepository.save(uS);
	
return"  MODIFICATION SUCCESS ";
}

// modpass oublier 



//ajouter friend 


@RequestMapping(value="/ajoutFriend/{usernameTarget}",method=RequestMethod.POST)
public Advertissement ajoutFriend(HttpServletRequest request,@PathVariable("usernameTarget") String usernameTarget ){
	Principal principal = request.getUserPrincipal();
	
UserApp uS=userRepository.findByUsername(principal.getName());
UserApp uT=userRepository.findByUsername(usernameTarget);
if(uS==null || uT == null|| uT.getClass()!= UserApp.class)
	throw new RuntimeException("this user  doesn t existe  ");


	return relationServices.AjoutFriend(uS, uT) ;
}




@RequestMapping(value="/abonnerAKinderGarten/{usernameTarget}",method=RequestMethod.POST)
public Advertissement abonnerAKinderGarten(HttpServletRequest request,@PathVariable("usernameTarget") String usernameTarget ){
	Principal principal = request.getUserPrincipal();
	
UserApp uS=userRepository.findByUsername(principal.getName());
UserApp uT=userRepository.findByUsername(usernameTarget);
if(uS==null || uT == null|| uT.getClass()!= UserApp.class)
	throw new RuntimeException("this user  doesn t existe  ");


	return relationServices.abonnerAKinderGarten(uS, uT) ;
}

@RequestMapping(value="/BloqueFriend/{usernameTarget}",method=RequestMethod.POST)
public Advertissement BloqueFriend(HttpServletRequest request,@PathVariable("usernameTarget") String usernameTarget ){
	Principal principal = request.getUserPrincipal();
	
UserApp uS=userRepository.findByUsername(principal.getName());
UserApp uT=userRepository.findByUsername(usernameTarget);
if(uS==null || uT == null|| uT.getClass()!= UserApp.class)
	throw new RuntimeException("this user  doesn t existe  ");


	return relationServices.BloqueFriend(uS, uT) ;
}

@RequestMapping(value="/retireFriend/{usernameTarget}",method=RequestMethod.POST)
public String retireFriend(HttpServletRequest request,@PathVariable("usernameTarget") String usernameTarget ){
	Principal principal = request.getUserPrincipal();
	
UserApp uS=userRepository.findByUsername(principal.getName());
UserApp uT=userRepository.findByUsername(usernameTarget);
if(uS==null || uT == null|| uT.getClass()!= UserApp.class)
	throw new RuntimeException("this user  doesn t existe  ");


	 relationServices.retireFriend(uS, uT) ;
	 return "ona retire avec succ";
}




@RequestMapping(value="/showinvitation")
public List<UserApp> listinvitation(HttpServletRequest request){
	
	Principal principal = request.getUserPrincipal();
	UserApp user=userRepository.findByUsername(principal.getName());
	if(!user.isActived())
		throw new RuntimeException("your account is not actived .");
if(user==null)
		throw new RuntimeException("user doesn t exist .");

	List<UserApp> l=advertissementRepository.findInvit(user ,Relation.FRIEND);
	
	

return l;
	
}



@RequestMapping(value="/accepteFriend/{fname}")
public String accepterFriend(HttpServletRequest request,@PathVariable("fname") String fname){
	
	Principal principal = request.getUserPrincipal();
	UserApp target=userRepository.findByUsername(principal.getName());
	UserApp source=userRepository.findByUsername(fname);
	
	if(target==null || source == null)
		throw new RuntimeException("this user  doesn t existe  ");
	
	if(advertissementRepository.findtargetAd(target, source)==null)
		throw new RuntimeException("this invitation is not registred   ");
	
	
	
	Advertissement av=userServices.getUserAdvertissement(target,source);
	
	
	return "acceptation de invitation";
	
}


///////////////////fin ajouterfrind


@Autowired
private JavaMailSender mailSender;


@RequestMapping(value="/saveUser/verif/{username}",method=RequestMethod.POST)
public String verificationEmail(HttpServletRequest request,@PathVariable("username") String username ){
	
	if(userServices.SendVerificationEmail(username))
		return "Success email send.";
		
return "some thing went wrong";

}


@RequestMapping(value="/saveUser/cfverif/{username}/{code}",method=RequestMethod.POST)
public String cfverificationEmail(HttpServletRequest request,@PathVariable("code") String code ,@PathVariable("username") String username){
	if(!userServices.cfVerification(username, code))
		throw new RuntimeException("this code may it be expired or wrong !!");
	
	
		
return "activated account avec sucess !!";

}

@RequestMapping(value="/oblierPdw/{username}",method=RequestMethod.POST)
public String oblierPwdEmail(@PathVariable String username ){
	
	if(userServices.SendVerificationEmailMdp(username))
		return "Success email send.";
		
return "some thing went wrong";

}

@RequestMapping(value="/modifierPasswordEmail/{code}",method=RequestMethod.POST)
public String  ModifyPasswordByEmail(@RequestBody ModifierPassword Md,@PathVariable String code){
	
	
	if(!Md.getPassword().equals(Md.getCfpassword()))
		throw new RuntimeException("the password and confirme one not the same  !!  ");
	
	if(!userServices.verifEmailMdp(Md, code))
		throw new RuntimeException("date de modification is expire  !!  ");
		
	userServices.ChangePwdByPassword(Md);
return"  MODIFICATION SUCCESS ";
}

























}
