package tn.esprit.spring.Service;

import java.util.ArrayList;
import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.UserApp;




@Service

public class UserDetailsServiceImpl implements UserDetailsService {
@Autowired
private AccountService accountService;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserApp user=accountService.findUserByUsername(username);
		if(user== null){
			throw new UsernameNotFoundException(username);
		}
		
		Collection<GrantedAuthority> authorities=new ArrayList<>();
		user.getRoles().forEach(r->{
			authorities.add(new SimpleGrantedAuthority( r.getRoleName()));
		});
		
		//authorities.add(new SimpleGrantedAuthority( user.getRoles()));
		return new User(user.getUsername(),user.getPassword(),authorities);
	}

}
