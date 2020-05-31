package tn.esprit.spring.Service;

import java.nio.file.Path; 

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl  {
	/* @Autowired
	    public JavaMailSender emailSender;*/
	 
	/*    public void sendSimpleMessage(
	      String to, String subject, String text) {
	       
	        SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setTo(to); 
	        message.setSubject(subject); 
	        message.setText(text);
	 //       emailSender.send(message);
	       
	    }*/
	    
	    
	    
	 /*   public void sendMessageWithAttachment(
	      String to, String subject, String text, String pathToAttachment) throws MessagingException {
	       
	         
	        MimeMessage message = emailSender.createMimeMessage();
	          
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	         
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(text);
	             
	        FileSystemResource file 
	          = new FileSystemResource((Path) new File());
	        helper.addAttachment("Invoice", file);
	     
	        emailSender.send(message);
	        // ...
	    }*/
}
