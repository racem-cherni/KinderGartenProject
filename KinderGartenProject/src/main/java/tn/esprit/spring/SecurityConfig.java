package tn.esprit.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		
	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable().exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilter(new JWTAuthenticationFilter(authenticationManager() ));
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/addUser").hasAuthority("ADMIN");	
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/saveUser/*").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/saveKinderGarten/*").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/saveUser/verif/*").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/saveUser/cfverif/*/*").permitAll();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/saveUser/saveParent/*").permitAll();
		
		http.authorizeRequests().antMatchers("/",
	                        "/favicon.ico",
	                        "/**/*.png",
	                        "/**/*.jpg",
	                        "/**/*.gif",
	                        "/**/*.svg",
	                        "/**/*.jpg",
	                        "/**/*.html",
	                        "/**/*.css",
	                        "/**/*.js",
	                        "/**/*.xml",
	                        "/javax.faces.resource/**",
	                        "/resources/img/**",
	                        "/resources/**"
	                        
	                       
	                        )
	                        .permitAll()
	                        .antMatchers("/login/logindesign.jsf")
	                        .permitAll()
	                        .antMatchers("/javax.faces.resource/**")
	                        .permitAll() .antMatchers("/servlet/api/auth/**")
	                        .permitAll()
	                        .antMatchers("/api/auth/**")
	                        .permitAll()
	                        .antMatchers("/login**")
	                        .permitAll() 
	                        .antMatchers("/login/***")
	                        .permitAll() .
	                        antMatchers("/api/user/checkUsernameAvailability", "/api/user/checkEmailAvailability")
	                        .permitAll()
	                        .antMatchers(HttpMethod.GET, "/api/polls/**", "/api/users/**")
	                            .permitAll()
	                        .anyRequest()
	                            .authenticated();
	                            
	                        
		
		http.addFilterBefore(new JWTAuthorizationFilter()  , UsernamePasswordAuthenticationFilter.class);
		
		
		
	}
	 @Override
	    public void configure(WebSecurity webSecurity) throws Exception {
	       webSecurity.ignoring().antMatchers("/servlet/jsf/**");
	    }
	
}
