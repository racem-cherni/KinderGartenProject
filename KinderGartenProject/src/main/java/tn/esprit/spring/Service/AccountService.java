package tn.esprit.spring.Service;

import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.RoleApp;
import tn.esprit.spring.entities.UserApp;


@Service
public interface AccountService {
public UserApp saveUser(UserApp user);
public RoleApp saveRole(RoleApp role);
public UserApp findUserByUsername(String username);
public UserApp addRoleToUser(String username,String role);
}
