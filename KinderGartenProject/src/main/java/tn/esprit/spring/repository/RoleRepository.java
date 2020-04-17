package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.entities.RoleApp;

public interface RoleRepository extends JpaRepository<RoleApp, Long> {
public RoleApp findByRoleName(String roleName);
}
