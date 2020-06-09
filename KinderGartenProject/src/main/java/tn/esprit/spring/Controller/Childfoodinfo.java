package tn.esprit.spring.Controller;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.stereotype.Controller;

@Controller(value = "childfood")
@ELBeanName(value = "childfood")
@Join(path = "/childfood", to = "/pages/parent/Listfoodbychild.jsf")
public class Childfoodinfo {

}
