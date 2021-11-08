package com.hutech.payrollapp.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hutech.payrollapp.api.model.Admin;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	Admin findByEmail(String email);
	
	public Admin findByResetPasswordToken(String token);
}
