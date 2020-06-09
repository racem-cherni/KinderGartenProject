package tn.esprit.spring.Controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.Service.MedicalrecordService;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.foodmedrecwithgramage;

@Controller(value = "chart")
@ELBeanName(value = "chart")
@Join(path = "/chartanalyse", to = "/pages/kindergarten/version3.jsf")
@ViewScoped
public class Chart {
	@Autowired
	private MedicalrecordService medrec;
	private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;
	private PieChartModel pieModel2;
    private PieChartModel pieModel1;
   
	private List<Child> lch;
	List<foodmedrecwithgramage> ch2;
    @PostConstruct
    public void init() {
       
        createAnimatedModels();
        createPieModels();
    
    }
    
    private void createAnimatedModels() {
        animatedModel1 = initLinearModel();
        animatedModel1.setTitle("Toal childs ggrams consomed by day");
        animatedModel1.setAnimate(true);
        animatedModel1.setLegendPosition("se");
        animatedModel1.setZoom(true);
        Axis yAxis = animatedModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(5000);
        DateAxis axis = new DateAxis("Dates");
        axis.setTickAngle(-50);
        axis.setMax("2020-06-16");
        axis.setTickFormat("%b %#d, %y");
        animatedModel1.getAxes().put(AxisType.X, axis);       
        
        animatedModel2 = initBarModel();
        animatedModel2.setTitle("needs of callories by child ");
        animatedModel2.setSeriesColors("CDCDCD");
       animatedModel2.setBarWidth(15);
        animatedModel2.setAnimate(true);
        animatedModel2.setLegendPosition("ne");
        yAxis = animatedModel2.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(500);
        
    }
   
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
          List <Child> ch =medrec.getallchildbykindergarten(1);
        ChartSeries boys = new ChartSeries();
        boys.setLabel("allergy gluten");
        
        for(Child ch1:ch){
        	
 boys.set(ch1.getChildName(), ch1.getMedicalRec().getCallories().getValue());
        
    }
        model.addSeries(boys);
        return model;
    }
    
    private void createPieModel1() {
        pieModel1 = new PieChartModel();
 
        pieModel1.set("total child having gluten allergy ", medrec.calcnbrchildbyallergy(1, "gluten"));
        pieModel1.set("total childs having skin infection", medrec.calcnbrinfct(1, "Skin infection"));

 
        pieModel1.setTitle("stats medical problems");
        pieModel1.setLegendPosition("w");
        pieModel1.setShadow(false);
    }
    private void createPieModel2() {
        pieModel2 = new PieChartModel();
 
        pieModel2.set("total child having O+ ", medrec.calcnbrblood(1, "O+"));
        pieModel2.set("total child having O- ", medrec.calcnbrblood(1, "O-"));
        pieModel2.set("total child having A+ ", medrec.calcnbrblood(1, "A+"));
        pieModel2.set("total child having A- ", medrec.calcnbrblood(1, "A-"));
        pieModel2.set("total child having B+ ", medrec.calcnbrblood(1, "B+"));
        pieModel2.set("total child having B- ", medrec.calcnbrblood(1, "B-"));
        pieModel2.set("total child having AB+", medrec.calcnbrblood(1, "AB+"));
        pieModel2.set("total child having AB-", medrec.calcnbrblood(1, "AB-"));
 System.out.println(medrec.calcnbrblood(1, "O+"));
        pieModel2.setTitle("nbre of childs having determined type of blood group");
        pieModel2.setLegendPosition("w");
        pieModel2.setShadow(false);
    }
    
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
       
        // getsumgrams
        //getallbydate
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
        List<foodmedrecwithgramage> ch=medrec.getallbydate(1);
        
        for(foodmedrecwithgramage food:ch){
        	
        	series1.set(food.getPublishedDate().toString(),medrec.getsumgrams(food.getPublishedDate()));
        	
        }
        
      
        model.addSeries(series1);
     
        return model;
    
    }
    
    public LineChartModel getAnimatedModel1() {
		return animatedModel1;
	}
	public void setAnimatedModel1(LineChartModel animatedModel1) {
		this.animatedModel1 = animatedModel1;
	}
	public BarChartModel getAnimatedModel2() {
		return animatedModel2;
	}
	public void setAnimatedModel2(BarChartModel animatedModel2) {
		this.animatedModel2 = animatedModel2;
	}

	public MedicalrecordService getMedrec() {
		return medrec;
	}

	public void setMedrec(MedicalrecordService medrec) {
		this.medrec = medrec;
	}

	public List<Child> getLch() {
		return lch;
	}

	public void setLch(List<Child> lch) {
		this.lch = lch;
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}
    
	  private void createPieModels() {
	        createPieModel1();
	        createPieModel2();
	        } 
	  public void itemSelect(ItemSelectEvent event) {
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
	                "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
	 
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }

	public List<foodmedrecwithgramage> getCh2() {
		return ch2;
	}

	public void setCh2(List<foodmedrecwithgramage> ch2) {
		this.ch2 = ch2;
	}

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
	}
	  
}
