package tn.esprit.spring.Controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.AnalysticServices;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.UserRepository;

@Controller(value = "StatistiqueKinderDash")
@ELBeanName(value = "StatistiqueKinderDash")
public class StatistiqueKinderDash implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	AnalysticServices analysticServices;
	@Autowired
	UserRepository userRepository;
	
	
	Map<String, Object> mapCla=new HashMap<>();
	Map<String, Object> mapClasCap=new HashMap<>();
	Map<String, Object> mapClasManT=new HashMap<>();
	Map<String, Object> mapteacherComM=new HashMap<>();
	Map<String, Object> mapNbreHealthKid=new HashMap<>();
	Map<String, String> mapClasseStable=new HashMap<>();
	Map<String, String> mapRecoClForCh=new HashMap<>();
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	


	public Map<String, String> getMapRecoClForCh() {
		System.err.println("iama herrr");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		KinderGarten k=user.getKindergarten();
		System.err.println("iama herrr");
	this.mapRecoClForCh.putAll(analysticServices.getClasseForkidswithoutForJsf(k));
		return mapRecoClForCh;
	}



	public void setMapRecoClForCh(Map<String, String> mapRecoClForCh) {
		this.mapRecoClForCh = mapRecoClForCh;
	}



	public Map<String, String> getMapClasseStable() {
		System.err.println("iama herrr");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		KinderGarten k=user.getKindergarten();
		System.err.println("iama herrr");
	this.mapClasseStable.putAll(analysticServices.NbreClasseStableForJsf(k));
		return mapClasseStable;
	}



	public void setMapClasseStable(Map<String, String> mapClasseStable) {
		this.mapClasseStable = mapClasseStable;
	}



	public Map<String, Object> getMapNbreHealthKid() {
		System.err.println("iama herrr");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		KinderGarten k=user.getKindergarten();
		System.err.println("iama herrr");
	this.mapNbreHealthKid.putAll(analysticServices.estimerNombreHealthproblem(k));
		
		
		
		
		return mapNbreHealthKid;
	}



	public void setMapNbreHealthKid(Map<String, Object> mapNbreHealthKid) {
		this.mapNbreHealthKid = mapNbreHealthKid;
	}



	public Map<String, Object> getMapteacherComM() {
		System.err.println("iama herrr");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		KinderGarten k=user.getKindergarten();
		System.err.println("iama herrr");
	this.mapteacherComM.putAll(analysticServices.estimerDeTeacherCompetenceValable(k));
		
		return mapteacherComM;
	}



	public void setMapteacherComM(Map<String, Object> mapteacherComM) {
		this.mapteacherComM = mapteacherComM;
	}



	public Map<String, Object> getMapClasManT() {
		System.err.println("iama herrr");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		KinderGarten k=user.getKindergarten();
		System.err.println("iama herrr");
	this.mapClasManT.putAll(analysticServices.estimerMonqueDeTeacher(k));
		
		return mapClasManT;
	}



	public void setMapClasManT(Map<String, Object> mapClasManT) {
		this.mapClasManT = mapClasManT;
	}



	public Map<String, Object> getMapClasCap() {
		System.err.println("iama herrr");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		KinderGarten k=user.getKindergarten();
		System.err.println("iama herrr");
	this.mapClasCap.putAll(analysticServices.estimerMonqueDeClasseParNbreparCapacite(k));
		
		
		return mapClasCap;
	}



	public void setMapClasCap(Map<String, Object> mapClasCap) {
		this.mapClasCap = mapClasCap;
	}



	public Map<String, Object> getMapCla() {
		System.err.println("iama herrr");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userName;

				if (principal instanceof UserDetails) {
					userName = ((UserDetails) principal).getUsername();
				} else {
					userName = principal.toString();
				}
				System.err.println(userName);
				UserApp user=userRepository.findByUsername(userName);
		KinderGarten k=user.getKindergarten();
		System.err.println("iama herrr");
	this.mapCla.put("Classes",analysticServices.calculerNbredeClasseParJardin(k));
	
	
	
	
	this.mapCla.put("Kids",analysticServices.calculerNbredeKidsParJardin(k));
	this.mapCla.put("Teachers",analysticServices.calculerNbredeTeacherParJardin(k));
		
		
		
		return this.mapCla;
	}



	public void setMapCla(Map<String, Object> mapCla) {
		this.mapCla = mapCla;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
