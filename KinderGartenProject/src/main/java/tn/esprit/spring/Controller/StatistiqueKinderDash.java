package tn.esprit.spring.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.AnalysticServices;
import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.UserRepository;

@Controller(value = "StatistiqueKinderDash")
@ELBeanName(value = "StatistiqueKinderDash")
@ViewScoped
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
	private BarChartModel barModel;
	private BarChartModel barModel1;
	    
	@Autowired
	UserServices userServices;
	
	
	
	    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries boys = new ChartSeries();
    	KinderGarten k=userServices.currentUserJsf().getKindergarten();
		System.err.println("iama herrr");
	
		
        boys.setLabel(k.getKinderGartenName());
        boys.set("Classes", analysticServices.calculerNbredeClasseParJardin(k));
        boys.set("Kids", analysticServices.calculerNbredeKidsParJardin(k));
        boys.set("Teachers", analysticServices.calculerNbredeTeacherParJardin(k));
        
 
       
 
        model.addSeries(boys);
        
 
        return model;
    }
    private void createBarModel() {
        barModel = initBarModel();
        KinderGarten k=userServices.currentUserJsf().getKindergarten();
        barModel.setTitle("calcul kinder");
        barModel.setLegendPosition("ne");
 barModel.setAnimate(true);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("number");
        yAxis.setMin(0);
        yAxis.setMax(analysticServices.calculerNbredeKidsParJardin(k));
    }
   
 
	
    private BarChartModel initBarModel1() {
        BarChartModel model = new BarChartModel();
        Map<Long,List<Double>> mp=new HashMap<>();
        
        ChartSeries boys = new ChartSeries();
    	KinderGarten k=userServices.currentUserJsf().getKindergarten();
		System.err.println("iama herrr");
	mp=analysticServices.CalculeKinderV(k);
	List<Double> ld=mp.get(k.getId());
	System.out.println();
	
	
   boys.setLabel(k.getKinderGartenName());  
   boys.set("NumbreClasseNocharger", analysticServices.NumbreClasseNocharger(k));
 boys.set("NumbreClasseNoPlein", analysticServices.NumbreClasseNoPlein(k));
   boys.set("NumbreClasseStable", analysticServices.NumbreClasseStable(k));
  
       
        model.addSeries(boys);
        
 
        return model;
    }
    private void createBarModel1() {
    	barModel1 = initBarModel1();
        KinderGarten k=userServices.currentUserJsf().getKindergarten();
        barModel1.setTitle("calcul kinder");
        barModel1.setLegendPosition("ne");
        barModel1.setAnimate(true);
        Axis xAxis = barModel1.getAxis(AxisType.X);
        xAxis.setLabel("");
 
        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setLabel("number");
        yAxis.setMin(0);
        yAxis.setMax(analysticServices.calculerNbredeClasseParJardin(k));
    }
    
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



	public BarChartModel getBarModel() {
		createBarModel();
		return barModel;
	}


	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
	public BarChartModel getBarModel1() {
		createBarModel1();
		return barModel1;
	}
	public void setBarModel1(BarChartModel barModel1) {
		this.barModel1 = barModel1;
	}
	
	
	
	
	

}
