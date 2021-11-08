package com.hutech.payrollapp.api.serviceImpl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hutech.payrollapp.api.exceptionhandler.UserNotFoundException;
import com.hutech.payrollapp.api.model.Admin;
import com.hutech.payrollapp.api.repository.AdminRepository;
import com.hutech.payrollapp.api.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public AdminServiceImpl(AdminRepository userRepository) {
		super();
		this.adminRepository = userRepository;
	}

	@Override
	public Admin save(Admin admin) {
		Admin user = new Admin(null, admin.getFirstName(), admin.getLastName(), admin.getEmail(),
				passwordEncoder.encode(admin.getPassword()), admin.getResetPasswordToken());

		return adminRepository.save(user);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Admin admin = adminRepository.findByEmail(username);
		if (admin == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(admin.getEmail(), admin.getPassword(),
				(Collection<? extends GrantedAuthority>) new HashSet<GrantedAuthority>());
	}

	public void updateResetPassword(String token, String username) throws UserNotFoundException {
		Admin admin = adminRepository.findByEmail(username);
		if (admin != null) {
			admin.setResetPasswordToken(token);
			adminRepository.save(admin);
		} else {
			throw new UserNotFoundException("could not find any user with username" + username);
		}
	}

	public Admin getByResetPasswordToken(String token) {
		return adminRepository.findByResetPasswordToken(token);
	}

	public void updatePassword(Admin user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);
		user.setResetPasswordToken(null);
		adminRepository.save(user);
	}

}
