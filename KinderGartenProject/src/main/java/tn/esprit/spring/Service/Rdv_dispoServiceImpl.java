package tn.esprit.spring.Service;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Lesjours;
import tn.esprit.spring.entities.Rdv_dispo;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.Rdv_dispoRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class Rdv_dispoServiceImpl implements IRdv_dispoService{
	@Autowired
	UserRepository userrepository;
	@Autowired
	Rdv_dispoRepository rdvDispoSrepository;
	@Autowired
	private Session sessionservice;
	
	public int ajouterRdv_dispo(Rdv_dispo dispo,HttpServletRequest request)  {
		UserApp connecteduser = sessionservice.session(request);
		KinderGarten jardin = connecteduser.getKindergarten();
	

		
		dispo.setJardin(jardin);
			
			rdvDispoSrepository.save(dispo);
		
					
		
		return dispo.getId();
	}
}
