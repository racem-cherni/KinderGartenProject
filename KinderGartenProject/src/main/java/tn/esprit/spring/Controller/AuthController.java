package tn.esprit.spring.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Data;
import tn.esprit.spring.SecurityConstants;
import tn.esprit.spring.TokenProvider;
import tn.esprit.spring.Service.KinderGartenServices;
import tn.esprit.spring.Service.ParentServices;
import tn.esprit.spring.Service.RegisterUser;
import tn.esprit.spring.Service.RelationServices;
import tn.esprit.spring.Service.UserServices;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.ModifierPassword;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.UserRepository;


@Controller(value = "AuthController")
@ELBeanName(value = "AuthController")
@Join(path = "/login2", to = "/login/NewFile.jsf")

public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	TokenProvider tokenProvider;
	@Autowired
	ParentServices  parentServices;
	@Autowired
	KinderGartenServices kinderGartenServices;
	@Autowired
	UserServices  userServices;
	@Autowired
	private UserRepository  userRepository;
	private String username;
	private String password;
	private String usernamer;
	private String passwordr;
	private String cfpassword;
    private String role;
	private String firstNameR;
	private String lastNameR;
	private String emailR;
	private String adresseR;
	private int telR;
	private String code;
	private int a;
    //----------------------------------kinder--------------------
	
	private String kinderGartenName;
	private String adresse;
	private String email;
	private int capacite ;
	private int tel;
	private Float prix;
	private  int maxRdv;
	
	//-----------------------------------change password
	private String changePasswordusername;
	private String changePassworpassword;
	private String changePassworcfpassword;
	//-----------------------------------------------
	//-----------------------------------forgetPassword
		private String forgetPasswordusername;
		private String forgetPasswordpassword;
		private String forgetPasswordCfpassword;
		private String forgetPasswordCode;
		//-----------------------------------------------
	
	@Autowired
	RelationServices relationServices;
	
	
	

	
	@Autowired
	ChildRepository childRepository;

public	static  String token;
	
	public String LOGOUT() throws IOException {
		//
		// HttpServletRequest response =response. ;
		//
		// response.logout();
		//
		ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ex.getRequest();

		HttpServletResponse response = (HttpServletResponse) ex.getResponse();

		Cookie[] cookies = request.getCookies();
		if (cookies == null)
			System.err.println("true");
		Optional<Cookie> token = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("token")).findFirst();

		token.get().setValue(null);

		response.addCookie(token.get());

		// response.sendRedirect("http://localhost:8081/login");

		return "/login/logindesign.xhtml?faces-redirect=true";
	}
	
	
	
	
	
	
	
	public String signin() throws IOException, ServletException {

		System.err.println("token l gdim " + this.token);

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.createToken(authentication);

		// System.err.println(jwt);

		this.token = jwt;

		System.err.println("dff");

		System.err.println("token jdid " + jwt);
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		System.err.println(userName);
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}
		
		
		

		String url = "";
		UserApp user=userRepository.findByUsername(userName);
		if (roles.contains("ROLE_PARENT")){
			
			if(!user.isActived())
			{

				if(userServices.SendVerificationEmail(userName))
					return "/login/sendEmail.xhtml?faces-redirect=false";
				
			}
			url = "/pages/parent/parentprofil.xhtml?faces-redirect=true";
		}
		if (roles.contains("ROLE_KINDERGARTEN")){
			if(!user.isActived())
				url="/pages/kindergarten/wait.xhtml?faces-redirect=true";
			
		url = "/pages/kindergarten/redirect.xhtml?faces-redirect=true";
			
			
		}
		
		if (roles.contains("ROLE_ADMIN")){
			return"/pages/admin/dashcontroll.xhtml?faces-redirect=true";
		}
		
		
		
		System.err.println(roles + " : roles");

		ExternalContext ex = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ex.getRequest();
//		HttpServletResponse response= (HttpServletResponse) ex.getResponse();
//		response.addHeader(SecurityConstants.HEADER_STRING,jwt);
//		System.err.println(response.getHeader(SecurityConstants.HEADER_STRING) + " : header");
		//response.sendRedirect("http://localhost:8082/SpringMVC/"+url);
		return url;
	}
	public String register1() throws IOException, ServletException{
		RegisterUser ru=new RegisterUser();
		System.err.println(cfpassword+" passw "+ passwordr+" username "+usernamer +"role"+role);
		ru.setCfpassword(cfpassword);
		ru.setPassword(passwordr);
		ru.setUsername(usernamer);
		
		UserApp user=userServices.saveUserEtape1(ru, "ROLE_PARENT");
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(usernamer, passwordr));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.createToken(authentication);
		this.token = jwt;

		System.err.println("dff");

		System.err.println("token jdid " + jwt);

		
		
			return "/login/registerparent.xhtml?faces-redirect=true";

		
		
		
	}
	public String register2() throws IOException, ServletException{
		RegisterUser ru=new RegisterUser();
		System.err.println(cfpassword+" passw "+ passwordr+" username "+usernamer +"role"+role);
		ru.setCfpassword(cfpassword);
		ru.setPassword(passwordr);
		ru.setUsername(usernamer);
		
		UserApp user=userServices.saveUserEtape1(ru, "ROLE_KINDERGARTEN");
		
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(usernamer, passwordr));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.createToken(authentication);
		this.token = jwt;

		System.err.println("dff");

		System.err.println("token jdid " + jwt);

		
			return  "/login/registerkinder.xhtml?faces-redirect=true";
		
		
		
	}
	
	
	public String registerparent(){
		if(this.usernamer==null || this.passwordr==null)
			return "/login/NewFile.xhtml?faces-redirect=false";
		Parent p=new Parent();
		p.setFirstName(firstNameR);
		p.setLastName(lastNameR);
		p.setEmail(emailR);
		p.setAdresse(adresseR);
		UserApp user=userRepository.findByUsername(usernamer);
		Parent app= parentServices.addParent(p, user);
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		
		
		
		
		return null ;
	}
	
	public String registerkinder(){
		if(this.usernamer==null || this.passwordr==null)
			return "/login/NewFile.xhtml?faces-redirect=false";
		
		System.err.println("i am her");
		KinderGarten kin=new KinderGarten();
		kin.setAdresse(adresse);
		kin.setCapacite(0);
		kin.setEmail(email);
		kin.setMaxRdv(maxRdv);
		kin.setPrix(prix);
		kin.setTel(tel);
		kin.setKinderGartenName(kinderGartenName);
		UserApp user=userRepository.findByUsername(this.usernamer);

		
		KinderGarten k= kinderGartenServices.saveKinderGarten(kin,user );
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return "/pages/kindergarten/wait.xhtml?faces-redirect=true" ;
	}
	
	
	
	public void sendEmail(){
		System.err.println("********************re send**********************");
		userServices.SendVerificationEmailMdp(username);
		
		
	}
	public String valideCode(){
		
		if(!userServices.cfVerification(username, code))
			return null;
			
		
		
		
		return "/login/NewFile.xhtml?faces-redirect=false";
		
		
	}
	
	public String ForgetPassWord1(){

		if(userServices.SendVerificationEmailMdpJSF(this.username))
			return "/login/FsendEmail.xhtml?faces-redirect=false";
		
		
		
		return null;
	}
	public String ForgetPassWord2(){

		ModifierPassword m=new ModifierPassword();
		m.setUsername(this.username);
		if(userServices.verifEmailMdp(m, this.forgetPasswordCode))
			return "/login/FchangePassword.xhtml?faces-redirect=true";
		
		System.out.println(this.username);
		System.out.println("test value : "+ userServices.cfVerification(this.username, this.forgetPasswordCode));
		return null;
	}
	
	
	
	public String ForgetPassWord3(){
		
		
		
		
		if(this.forgetPasswordpassword.equals(this.forgetPasswordCfpassword)){
			ModifierPassword m=new ModifierPassword();
			m.setCfpassword(this.forgetPasswordCfpassword);
			m.setPassword(this.forgetPasswordpassword);
			m.setUsername(this.username);
			userServices.ChangePwdByPassword(m);
			return"/login/NewFile.xhtml?faces-redirect=true";
		}
			
		return null;
	}
	
	
	public String changepPassword(){
		if(this.changePassworcfpassword.equals(this.changePassworpassword)){
			ModifierPassword m=new ModifierPassword();
			m.setCfpassword(this.changePassworcfpassword);
			m.setPassword(this.changePassworpassword);
			m.setUsername(userServices.currentUserJsf().getUsername());
			userServices.ChangePwdByPassword(m);
			return"/login/NewFile.xhtml?faces-redirect=true";
		}
			
		return null;	
			
	}
	

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public TokenProvider getTokenProvider() {
		return tokenProvider;
	}

	public void setTokenProvider(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	

	public KinderGartenServices getKinderGartenServices() {
		return kinderGartenServices;
	}

	public void setKinderGartenServices(KinderGartenServices kinderGartenServices) {
		this.kinderGartenServices = kinderGartenServices;
	}

	public UserServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UserServices userServices) {
		this.userServices = userServices;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsernamer() {
		return usernamer;
	}

	public void setUsernamer(String usernamer) {
		this.usernamer = usernamer;
	}

	public String getPasswordr() {
		return passwordr;
	}

	public void setPasswordr(String passwordr) {
		this.passwordr = passwordr;
	}

	public String getCfpassword() {
		return cfpassword;
	}

	public void setCfpassword(String cfpassword) {
		this.cfpassword = cfpassword;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		AuthController.token = token;
	}

	public String getFirstNameR() {
		return firstNameR;
	}

	public void setFirstNameR(String firstNameR) {
		this.firstNameR = firstNameR;
	}

	public String getLastNameR() {
		return lastNameR;
	}

	public void setLastNameR(String lastNameR) {
		this.lastNameR = lastNameR;
	}

	public String getEmailR() {
		return emailR;
	}

	public void setEmailR(String emailR) {
		this.emailR = emailR;
	}

	public String getAdresseR() {
		return adresseR;
	}

	public void setAdresseR(String adresseR) {
		this.adresseR = adresseR;
	}

	public int getTelR() {
		return telR;
	}

	public void setTelR(int telR) {
		this.telR = telR;
	}







	public String getCode() {
		return code;
	}







	public void setCode(String code) {
		this.code = code;
	}







	public ParentServices getParentServices() {
		return parentServices;
	}







	public void setParentServices(ParentServices parentServices) {
		this.parentServices = parentServices;
	}







	public UserRepository getUserRepository() {
		return userRepository;
	}







	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}







	public int getA() {
		return a;
	}







	public void setA(int a) {
		this.a = a;
	}







	public String getKinderGartenName() {
		return this.kinderGartenName;
	}







	public void setKinderGartenName(String kinderGartenName) {
		this.kinderGartenName = kinderGartenName;
	}







	public String getAdresse() {
		return adresse;
	}







	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}







	public String getEmail() {
		return this.email;
	}







	public void setEmail(String email) {
		this.email = email;
	}







	public int getCapacite() {
		return capacite;
	}







	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}







	public int getTel() {
		return tel;
	}







	public void setTel(int tel) {
		this.tel = tel;
	}







	public Float getPrix() {
		return prix;
	}







	public void setPrix(Float prix) {
		this.prix = prix;
	}







	public int getMaxRdv() {
		return maxRdv;
	}







	public void setMaxRdv(int maxRdv) {
		this.maxRdv = maxRdv;
	}







	public RelationServices getRelationServices() {
		return relationServices;
	}







	public void setRelationServices(RelationServices relationServices) {
		this.relationServices = relationServices;
	}







	public ChildRepository getChildRepository() {
		return childRepository;
	}







	public void setChildRepository(ChildRepository childRepository) {
		this.childRepository = childRepository;
	}







	public String getChangePasswordusername() {
		return changePasswordusername;
	}







	public void setChangePasswordusername(String changePasswordusername) {
		this.changePasswordusername = changePasswordusername;
	}







	public String getChangePassworpassword() {
		return changePassworpassword;
	}







	public void setChangePassworpassword(String changePassworpassword) {
		this.changePassworpassword = changePassworpassword;
	}







	public String getChangePassworcfpassword() {
		return changePassworcfpassword;
	}







	public void setChangePassworcfpassword(String changePassworcfpassword) {
		this.changePassworcfpassword = changePassworcfpassword;
	}







	public String getForgetPasswordusername() {
		return forgetPasswordusername;
	}







	public void setForgetPasswordusername(String forgetPasswordusername) {
		this.forgetPasswordusername = forgetPasswordusername;
	}







	public String getForgetPasswordpassword() {
		return forgetPasswordpassword;
	}







	public void setForgetPasswordpassword(String forgetPasswordpassword) {
		this.forgetPasswordpassword = forgetPasswordpassword;
	}







	public String getForgetPasswordCfpassword() {
		return forgetPasswordCfpassword;
	}







	public void setForgetPasswordCfpassword(String forgetPasswordCfpassword) {
		this.forgetPasswordCfpassword = forgetPasswordCfpassword;
	}







	public String getForgetPasswordCode() {
		return forgetPasswordCode;
	}







	public void setForgetPasswordCode(String forgetPasswordCode) {
		this.forgetPasswordCode = forgetPasswordCode;
	}
	
	
	
	
	
	
	

}
