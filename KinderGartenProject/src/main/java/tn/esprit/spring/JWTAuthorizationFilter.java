package tn.esprit.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import tn.esprit.spring.Controller.AuthController;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
	@Autowired
 AuthController  AuthController;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With,"
				+ "Content-Type,"
				+ " Access-Control-Request-Method,"
				+ " Access-Control-Request-Headers,"
				+ " Authorization");
		
		
		response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,"
				+ " Access-Control-Allow-Credentials, Authorization ");
		
		
		
		
		
		
		//String jwt=request.getHeader(SecurityConstants.HEADER_STRING);
		
		String jwt=getJwtFromRequest(request,response);
		if(jwt==null )//|| !jwt.startsWith(SecurityConstants.TOKEN_PREFIX))
		{
			filterChain.doFilter(request, response);
			return;
		}
		Claims claims=Jwts.parser().
				setSigningKey(SecurityConstants.SECRET)
				.parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX,""))
				.getBody();
		
		String username=claims.getSubject();
		ArrayList<Map<String,String>> roles =(ArrayList<Map<String,String>>) claims.get("roles");
		Collection<GrantedAuthority> authorities=new  ArrayList<>();
		roles.forEach(r->{
			authorities.add(new SimpleGrantedAuthority(r.get("authority")))	;
			
		});
		
		UsernamePasswordAuthenticationToken authenticatedUser=
				new UsernamePasswordAuthenticationToken(username, null,authorities);
		
	SecurityContextHolder.getContext().setAuthentication(authenticatedUser);	
	filterChain.doFilter(request, response);	
	}
	
	
	
	 private Cookie createCookie(final String content, final int expirationTimeSeconds) {
	        final Cookie cookie = new Cookie("token", content);
	        cookie.setMaxAge(expirationTimeSeconds);
	        cookie.setHttpOnly(true);
	        cookie.setPath("/");
	        return cookie;
	      }
	    
	 
	 private String getJwtFromRequest(HttpServletRequest request,HttpServletResponse response) {
		
		 String url = request.getRequestURL().toString();
	 System.err.println("hatha path :"+url);
      		 if(url.equals("http://localhost:8082/SpringMVC/login2") 
      				 || url.startsWith("http://localhost:8082/SpringMVC/javax.faces.resource/")
      				 || url.startsWith("http://localhost:8082/SpringMVC/resources/js/testjs.js")
      				 || url.startsWith("http://localhost:8082/SpringMVC/login/registerparent.jsf")
      				||url.startsWith("http://localhost:8082/SpringMVC/resources/")
      				||url.startsWith("http://localhost:8082/SpringMVC/login/FsendEmail")
      				||url.startsWith("http://localhost:8082/SpringMVC/login/FchangePassword")
      						||url.startsWith("http://localhost:8082/SpringMVC/login/NewFile"))
      				 
      			
      			 return null;
	    	
   	  String bearerToken = null ;
   	
   	
   	 //System.err.println("gettoken");
   	 
   	 
   	 
   	 if (AuthController.token!= null)
      	 {
//   			 System.err.println("i3ammer fe cookies");
//      		 
//      		 System.err.println("aaa "+AuthController.token);
//      		 System.err.println("supp token "+AuthController.token.substring(7));
      	final Cookie cookieToken = createCookie(AuthController.token.substring(7), 1500);
       response.addCookie(cookieToken);
       bearerToken = AuthController.token;
       

       AuthController.token=null;
       
      		    		
      	 }
   	 else{
   		 
   		 
   			
       	 Cookie[] cookies = request.getCookies(); 
       //	 if (cookies == null)
       			//System.err.println("true");
       String token =	 Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals("token"))
            .findFirst()
            .map(Cookie::getValue).orElse(null);

       bearerToken =SecurityConstants.TOKEN_PREFIX+token ; 
      
      // System.err.println("hetha lcookies : " + token );
    
   		 
   	 }
       	
   	 
   	 
   	 

 
       if (StringUtils.hasText(bearerToken) /*&& bearerToken.startsWith("Bearer ")*/) {
       	
//       	 System.err.println("lga token");
       	
           return bearerToken;}
//       System.err.println("malgech token");
       return null;
   }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
