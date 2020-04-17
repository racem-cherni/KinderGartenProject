package tn.esprit.spring.Service;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.UserRepository;

@Service
public class Session {
	@Autowired
	private UserRepository userRepository;
	public static UserApp user;
	public UserApp session(HttpServletRequest request){
		
		Principal principal = request.getUserPrincipal();
		 user=userRepository.findByUsername(principal.getName());
		
		return user;
		
	}
	
	

}
