package tn.esprit.spring;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import tn.esprit.spring.entities.UserApp;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	
	
public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}


	
@Override
public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {
	UserApp userapp=null;
	
	try {
		userapp=new ObjectMapper().readValue(request.getInputStream(), UserApp.class);
	}  catch (IOException e) {
		throw new RuntimeException(e);
	}
	
	System.out.println("*************************************");
	System.out.println("username : "+userapp.getUsername());
	System.out.println("password : "+ userapp.getPassword());
	return authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(userapp.getUsername(), userapp.getPassword())
			);
}
@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
	User springUser=(User)	authResult.getPrincipal();
	String jwtToken=Jwts.builder()
			.setSubject(springUser.getUsername())
			.setExpiration(
				new Date(	System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
			.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).
			claim("roles", springUser.getAuthorities()).compact();
	System.out.println("token : "+ jwtToken);
			response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+jwtToken);
			
		
	}
}
