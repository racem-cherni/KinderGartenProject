package tn.esprit.spring.Service;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Advertissement;
import tn.esprit.spring.entities.EmailPwd;
import tn.esprit.spring.entities.ModifierPassword;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.RoleApp;
import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.entities.VerificationToken;
import tn.esprit.spring.repository.AdvertissementRepository;
import tn.esprit.spring.repository.EmailPwdRepository;
import tn.esprit.spring.repository.RoleRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.VerificationTokenRepository;
@Service
@Transactional
public class UserServices implements AccountService{
	@Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder ;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
    private VerificationTokenRepository tokenRepository;
	@Autowired
	private JavaMailSender mailSender;
@Autowired
private AdvertissementRepository advertissementRepository;
@Autowired
private EmailPwdRepository emailPwdRepository;
	
	@Override
	public UserApp saveUser(UserApp user) {
		String hash=bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(hash);
	
		return userRepository.save(user);
	}
	

	@Override
	public UserApp findUserByUsername(String username) {
		
		return userRepository.findByUsername(username) ;
	}

	@Override
	public UserApp addRoleToUser(String username, String role) {
		UserApp u=userRepository.findByUsername(username) ;
		RoleApp r=roleRepository.findByRoleName(role);
		u.getRoles().add(r);
		
		//userRepository.save(u);
		return userRepository.save(u);
	}


	@Override
	public RoleApp saveRole(RoleApp role) {
		
		return roleRepository.save(role);
	}

	
	

























}
