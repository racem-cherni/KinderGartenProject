package tn.esprit.spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.RoleApp;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.ParentRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class ParentServices {
@Autowired
private ParentRepository parentRepository;
@Autowired
private UserRepository userRepository;
@Autowired
private ChildRepository childRepository;
	
	public Parent addParent(Parent p,UserApp u)
	{boolean test=false;
	boolean test1=false;
		
		UserApp user=userRepository.getOne(u.getId());
		
		for(RoleApp r:u.getRoles())
		{
			if(r.getRoleName().equals("ROLE_PARENT"))
				test=true;
		}
					
		p.setUserApp(u);

	if(vrifParentuser(u))
		throw new RuntimeException("this user is realy used"); 
		
		return parentRepository.save(p);
	}
	
	public boolean vrifParentuser(UserApp u){
		
		
		List<Parent>	lp=parentRepository.findAll();	
		for(Parent p:lp){
			if(p.getUserApp().equals(u))
				return true;
		}
			
		return false;
	}
	
	
	
	
	public List<Child> getMyChild(Parent parent){
		
		return childRepository.findchildByparent(parent) ;
	}
	
	

	
	
	public void deleteParent(Parent p){
		 parentRepository.deleteById(p.getId());
		 UserApp u=userRepository.findUserByParent(p);
		 userRepository.delete(u);
		 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Parent addUserToParent(Parent p)
	{
		
		return parentRepository.save(p);
	}
	
	public void deleteParentById(Long pid)
	{
		
		 parentRepository.deleteById(pid);
	}
	public Parent update(Parent p)
	{
		return parentRepository.save(p);
	}
	
	
	
	
	
	
	
}
