package tn.esprit.spring.Service;


import java.security.Principal;  
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.management.relation.Relation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import tn.esprit.spring.entities.Advertissement;
import tn.esprit.spring.entities.EmailPwd;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.ModifierPassword;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.RoleApp;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.entities.VerificationToken;
import tn.esprit.spring.repository.AdvertissementRepository;
import tn.esprit.spring.repository.EmailPwdRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.RoleRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.VerificationTokenRepository;
@Service
@Transactional
public class UserServices implements AccountService{
	@Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder ;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
    private VerificationTokenRepository tokenRepository; 
	
	@Autowired
	private JavaMailSender mailSender;
@Autowired
private AdvertissementRepository advertissementRepository;
@Autowired

private EmailPwdRepository emailPwdRepository;
@Autowired
KinderGartenRepository kinderGartenRepository;
@Autowired
ParentRepository parentRepository;
	
public UserApp saveUserEtape1( RegisterUser user, String role){
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
		u.setPriority(1);
		return saveUser(u);
	}
	else if(role.equals("ROLE_PARENT")){
		
		roles.add(r);
		UserApp u=new UserApp(user.getUsername(),user.getPassword(),roles,false,user.getScore(),3);
		u.setPriority(100);
		return saveUser(u);
		}
	else
		throw new RuntimeException("! this role don't exist !!! .");
	
	
	
	
}









	@Override
	public UserApp saveUser(UserApp user) {
		String hash=bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hash);
	
		return userRepository.save(user);
	}
	

	@Override
	public UserApp findUserByUsername(String username) {
		
		return userRepository.findByUsername(username) ;
	}

	@Override
	public UserApp addRoleToUser(String username, String role) {
		UserApp u=userRepository.findByUsername(username) ;
		RoleApp r=roleRepository.findByRoleName(role);
		u.getRoles().add(r);
		
		//userRepository.save(u);
		return userRepository.save(u);
	}


	@Override
	public RoleApp saveRole(RoleApp role) {
		
		return roleRepository.save(role);
	}

	
	public boolean SendVerificationEmail(String username){
		
		UserApp uS=userRepository.findByUsernametest(username);
		 VerificationToken vi= tokenRepository.findByUser(uS);
		 if(vi!=null)
			 tokenRepository.delete(vi);
		System.out.println(uS.getParent().getEmail());
		Random rand = new Random();
		int n = rand.nextInt(10000) + 1;
		SimpleMailMessage email = new SimpleMailMessage();
	    email.setTo(uS.getParent().getEmail());
	    email.setSubject("verification code ");
	    email.setText("lecode de activation de compte :"+n);
	    mailSender.send(email);
	    VerificationToken v=new VerificationToken();
	    v.setToken(""+n);
	    v.setUser(uS);
	    v.setExpiryDate(v.calculateExpiryDate(60));
	    tokenRepository.save(v);
	return true;

	}
	
public boolean SendVerificationEmailMdp(String username){
		
		UserApp uS=userRepository.findByUsername(username);
		if(uS.isActived())
			throw new RuntimeException("this user is all ready actived !!!");
		
		if(uS.getPoint()<=0)
			throw new RuntimeException("this user is blocked !!!");
		Random rand = new Random();
		int n = rand.nextInt(100000)+ 1;
		String hash=bCryptPasswordEncoder.encode(""+n);
		EmailPwd em=new EmailPwd(null,username,hash,null);
		em.setDate(em.calculateExpiryDate(60));
		
		emailPwdRepository.save(em);
		
		SimpleMailMessage email = new SimpleMailMessage();
	    email.setTo(uS.getParent().getEmail());
	    email.setSubject("verification code ");
	    email.setText("change my pwd :"+"http://localhost:8081/modifierPasswordEmail/"+hash);
	    mailSender.send(email);
	   
	return true;

	}
	
public boolean verifEmailMdp(ModifierPassword Md,String code){
		
EmailPwd em=emailPwdRepository.getEmailPwdByCode(code);
		if(!Md.getUsername().equals(em.getUsername()))
			throw new RuntimeException("this not the same user");
		if(!code.equals(em.getCode()))
			return false;
		
		 Calendar cal = Calendar.getInstance();
		    if ((em.getDate().getTime() - cal.getTime().getTime()) <= 0) {
		      return false;
		    } 
		
		
		emailPwdRepository.delete(em);
		return true;
				
				
	}
	
	
	
public boolean cfVerification(String username,String code){
		
		UserApp uS=userRepository.findByUsernametest(username);
		 VerificationToken v= tokenRepository.findByUser(uS);
		 
		 
		 Calendar cal = Calendar.getInstance();
		    if ((v.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
		      return false;
		    } 
		    
		     if(!code.equals(v.getToken()))
		    	 if(!username.equals(v.getUser().getUsername()))
		    	 return false;
		     
		    uS.setActived(true); 
		    userRepository.save(uS); 
		    tokenRepository.delete(v);
	return true;

	}


public Advertissement getUserAdvertissement(UserApp target,UserApp source) {
	
Advertissement a =advertissementRepository.findtargetAdID(target.getId(),source.getId(),tn.esprit.spring.entities.Relation.FRIEND);
a.setActive(true);

return advertissementRepository.save(a);

	
}


public boolean ChangePwdByPassword(ModifierPassword Md){
	
	
	UserApp uS=userRepository.findByUsername(Md.getUsername());
	String hash=bCryptPasswordEncoder.encode(Md.getPassword());
	uS.setPassword(hash);
	userRepository.save(uS);
	return true;
	
	
	
}


public UserApp getUserBykinder(KinderGarten kinder){
	return userRepository.findByKinder(kinder);
}





public UserApp currentUserJsf(){
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	String userName;

			if (principal instanceof UserDetails) {
				userName = ((UserDetails) principal).getUsername();
			} else {
				userName = principal.toString();
			}
			System.err.println(userName);
			UserApp user=userRepository.findByUsername(userName);
			return user;
}






public List<UserApp> getAllUser(){
	return userRepository.findAll();
}


public List<KinderGarten> getAllkinder(){
	
	return kinderGartenRepository.recherchKinder();
}

public List<Parent> getAllParent(){
	return parentRepository.findAll();
}





public boolean SendVerificationEmailMdpJSF(String username){
	
	UserApp uS=userRepository.findByUsername(username);
	if(!uS.isActived())
		throw new RuntimeException("this user is all ready actived !!!");
	
	if(uS.getPoint()<=0)
		throw new RuntimeException("this user is blocked !!!");
	Random rand = new Random();
	int n = rand.nextInt(100000)+ 1;
	String hash=bCryptPasswordEncoder.encode(""+n);
	EmailPwd em=new EmailPwd(null,username,hash,null);
	em.setDate(em.calculateExpiryDate(60));
	
	emailPwdRepository.save(em);
	
	SimpleMailMessage email = new SimpleMailMessage();
    email.setTo(uS.getParent().getEmail());
    email.setSubject("verification code ");
    email.setText("change my pwd Code :"+hash);
    mailSender.send(email);
   
return true;

}








}
