package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.entities.UserApp;
import tn.esprit.spring.entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
public	VerificationToken findByToken(String token);
	 
 public   VerificationToken findByUser(UserApp user);
}
