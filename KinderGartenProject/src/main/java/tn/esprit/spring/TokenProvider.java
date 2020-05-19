package tn.esprit.spring;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
@Component
public class TokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
	public String createToken(
			Authentication authResult) throws IOException, ServletException {
	User springUser=(User)	authResult.getPrincipal();
	String jwtToken=Jwts.builder()
			.setSubject(springUser.getUsername())
			.setExpiration(
				new Date(	System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
			.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).
			claim("roles", springUser.getAuthorities()).compact();
	System.out.println("token : "+ jwtToken);
	return SecurityConstants.TOKEN_PREFIX+jwtToken;
			//response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+jwtToken);
			
		
	}
	 public boolean validateToken(String authToken) {
	        try {
	            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(authToken);
	            return true;
	        } catch (SignatureException ex) {
	            logger.error("Invalid JWT signature");
	        } catch (MalformedJwtException ex) {
	            logger.error("Invalid JWT token");
	        } catch (ExpiredJwtException ex) {
	            logger.error("Expired JWT token");
	        } catch (UnsupportedJwtException ex) {
	            logger.error("Unsupported JWT token");
	        } catch (IllegalArgumentException ex) {
	            logger.error("JWT claims string is empty.");
	        }
	        return false;
	    }
}
