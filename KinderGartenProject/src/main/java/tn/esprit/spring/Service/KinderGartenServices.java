package tn.esprit.spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	public UserRepository   userRepository;
	@Autowired
	public KinderGartenRepository kinderGartenRepository;
	
	public KinderGarten saveKinderGarten( KinderGarten k, UserApp u){
		
	boolean test=false;
	for(RoleApp r:u.getRoles())
	{
		if(r.getRoleName().equals("ROLE_KINDERGARTEN"))
			test=true;
	}

			
		k.setUserapp(u);
		System.err.println(k.getKinderGartenName());
			return kinderGartenRepository.save(k);
		}
	
	

	public List<KinderGarten> getallKinderNonActived(){
return		kinderGartenRepository.getkinderforAdmin();
		
		
		
	}
	
	public void deleteKinder(KinderGarten  k ){
		KinderGarten kinder=kinderGartenRepository.findById(k.getId()) .get();
		
		
		UserApp u=userRepository.findByKinder(kinder);
		u.setActived(false);
		userRepository.save(u);
	}
	
	public void updateKinder(KinderGarten  k ){
		
		
		
		kinderGartenRepository.save(k);
	}
	
	
	public KinderGarten activerKinder(KinderGarten  k){
		KinderGarten kinder=		 kinderGartenRepository.findById(k.getId()) .get();
		UserApp u=userRepository.findByKinder(kinder);
		u.setActived(true);
		userRepository.save(u);
		return kinder;
	 }
}
