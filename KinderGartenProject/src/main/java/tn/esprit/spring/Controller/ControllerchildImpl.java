package tn.esprit.spring.Controller;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.entities.MedicalRec;

@Controller(value = "childcontroller")
@ELBeanName(value = "childcontroller")
@Join(path = "/child", to = "/pages/parent/ChildDeashboard.jsf")
public class ControllerchildImpl {
@Autowired
ControllerMedRecImpl medrec;


}
