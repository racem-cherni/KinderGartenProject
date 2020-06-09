package tn.esprit.spring.Controller;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.stereotype.Controller;

@Controller(value = "scrapper")
@ELBeanName(value = "scrapper")
@Join(path = "/scrapandlist", to = "/pages/kindergarten/scrapandlistfoods.jsf")
public class scrapandlistfoods {

}
