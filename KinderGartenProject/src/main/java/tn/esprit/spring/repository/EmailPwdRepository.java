package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.EmailPwd;

public interface EmailPwdRepository extends JpaRepository<EmailPwd, Long> {
@Query("select e from EmailPwd e where e.code= :code")
public EmailPwd getEmailPwdByCode(@Param("code") String code);
}
