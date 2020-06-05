package tn.esprit.spring;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.List;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;

import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tn.esprit.spring.Service.AccountService;
import tn.esprit.spring.Service.ClasseServices;
import tn.esprit.spring.Service.TeacherServices;
import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.EmailPwd;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Teacher;
import tn.esprit.spring.repository.EmailPwdRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.VoteRepository;



@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class KinderGartenProjectApplication implements CommandLineRunner {
	@Autowired
	private TeacherServices teacherServices;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserServices userServices;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private KinderGartenRepository kinderGartenRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailPwdRepository emailPwdRepository;
	@Autowired
	private ClasseServices classeServices;
	@Bean
	 public BCryptPasswordEncoder getBCPE(){
		 return new BCryptPasswordEncoder();
	 }
	public static void main(String[] args) {
		SpringApplication.run(KinderGartenProjectApplication.class, args);
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
	FacesServlet servlet = new FacesServlet();
	return new ServletRegistrationBean(servlet, "*.jsf"); }
	
	@Bean
	public FilterRegistrationBean rewriteFilter() {
	FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
	rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
	DispatcherType.ASYNC, DispatcherType.ERROR));
	rwFilter.addUrlPatterns("/*");
	return rwFilter;
	}
	@Override
	public void run(String... args) throws Exception {
		
		/*accountService.saveUser(new UserApp(12,"admin","123",null,true,12));
		accountService.saveUser(new UserApp(12,"user","123",null,true,12));
		accountService.saveUser(new UserApp(12,"lina","123",null,true,12));
		accountService.saveUser(new UserApp(12,"koussay","123",null,true,12));
		accountService.saveUser(new UserApp(12,"racem","123",null,true,12));

		accountService.saveRole(new RoleApp(null,"ADMIN"));
		accountService.saveRole(new RoleApp(null,"PARENT"));
		accountService.saveRole(new RoleApp(null,"KINDERGARTEN"));
		accountService.addRoleToUser("lina", "PARENT");
		accountService.addRoleToUser("koussay", "PARENT");
		accountService.addRoleToUser("racem", "KINDERGARTEN");
		KinderGartenRepository.save(new KinderGarten(null,"newKids","arianna","nice",12,12,null,null));
		KinderGartenRepository.save(new KinderGarten(null,"lifeEasy","nefta","nice",12,12,null,null));
		KinderGartenRepository.save(new KinderGarten(null,"happyKids","nefta","nice",12,12,null,null));*/
		//userServices.saveUser(new UserApp(12,"salah",passwordEncoder.encode("123"),Role.ADMIN,true,12));
		//userServices.saveUser(new UserApp(12,"louay",passwordEncoder.encode("123"),Role.ADMIN,true,12));
	}
	  // @Scheduled(cron="0 0 * ? * *")
    public void Scheduledevryhour() {
    	
    	List<EmailPwd> l=emailPwdRepository.findAll();
    	 Calendar cal = Calendar.getInstance();
		   
    	l.forEach(e->{
    		 if ((e.getDate().getTime() - cal.getTime().getTime()) <= 0) {
    			 emailPwdRepository.delete(e);
			    } 
    	});
    	
    	
     
    }
   // @Scheduled(cron="0 * * ? * *")
    public void teacher() {
    	List<KinderGarten> lk=kinderGartenRepository.findAll();
    	List<Classe> lc=new ArrayList<>();
    	List<Teacher> lt=new ArrayList<>();
    	for (KinderGarten kinderGarten : lk) {
			lc=classeServices.getClasseBykinder(kinderGarten);
					
    		for (Classe classe : lc) {
    			lt=teacherServices.getTeacherTheMostAdecttied(kinderGarten,classe);
    			for (Teacher teacher : lt) {
					if(teacher.getClasse()==null){
						teacherServices.affecttacherToClasse(teacher, classe);
						break;
					}
				}
    			
				
			}
    		
    		
    		
		}
    	
    }
    
    
    
  //  @Scheduled(cron="0 * * ? * *")
    public void Scheduledevrysecond() {
System.err.println("*********************************schedual");
    	classeServices.affectationGlobale();
     
    }
    //0 * * ? * * evry minute
   // 
    @Scheduled(cron="0 0 12 1 * ?")
    public void ScheduledeveryMounth() {
    	
   List<KinderGarten> lk=kinderGartenRepository.recherchKinder();
    	
   lk.forEach(k->{
	  // VoteServecies.updateScoreEvent(k);
   }); 	
     
    }

}
