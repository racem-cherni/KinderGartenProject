package tn.esprit.spring.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.RoleApp;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class KinderGartenServices {
	public UserRepository   userRepository;
	public KinderGartenRepository kinderGartenRepository;
	
	public KinderGarten saveKinderGarten( KinderGarten k, String username){
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
}
