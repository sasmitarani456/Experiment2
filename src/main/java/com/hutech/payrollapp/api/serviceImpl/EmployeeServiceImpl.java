package com.hutech.payrollapp.api.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hutech.payrollapp.api.exceptionhandler.EmailAlreadyExistException;
import com.hutech.payrollapp.api.exceptionhandler.EmployeeNotFoundException;
import com.hutech.payrollapp.api.model.Employee;
import com.hutech.payrollapp.api.repository.EmployeeRepository;
import com.hutech.payrollapp.api.service.EmployeeService;

import net.bytebuddy.utility.RandomString;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private JavaMailSender javaMailSender;

	public EmployeeServiceImpl(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	public boolean checkIfUserExist(String email) {
		return employeeRepo.findByEmpEmail(email) != null ? true : false;
	}

	public boolean checkIfUserIdExist(String empId) {
		return employeeRepo.findByEmpId(empId) != null ? true : false;
	}

	@Override
	public String save(Employee employee) throws MessagingException, EmailAlreadyExistException {
		if (checkIfUserIdExist(employee.getEmpId())) {
			throw new EmailAlreadyExistException("Employee Id already Exist!. Please check the Id.", 409);
		}
		if (checkIfUserExist(employee.getEmpEmail())) {
			throw new EmailAlreadyExistException("Email Id already registered! Please try a different Email Id.", 409);
		}
		String randomCode = RandomString.make(64);
		System.out.println(randomCode);
		employee.setVerificationToken(randomCode);
		employee.setEnabled(false);
		employeeRepo.save(employee);
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(employee.getEmpEmail());
		helper.setSubject("PeopleCentral Onboarding Verification");
		String content = "<center><h1>Welcome to PeopleCentral</h1>" + "<h3> Dear, " + employee.getEmpFirstName()
				+ "</h3>" + "<h4>Please click the link below to Activate your account</h4></center>" + "<center>"
				+ "http://localhost:8080/application/verifyEmployee?verificationToken="
				+ employee.getVerificationToken() + "</center>";

		message.setContent(content, "text/html; charset=utf-8");
		javaMailSender.send(message);
		return "Employee Info Saved";
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepo.findByEmpEmail(username);
		if (employee == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(employee.getEmpEmail(), employee.getPassword(),
				new ArrayList<>());
	}

	public void updateResetPassword(String token, String username) throws EmployeeNotFoundException {
		Employee emp = employeeRepo.findByEmpEmail(username);
		if (emp != null) {
			emp.setResetPassword(token);
			;
			employeeRepo.save(emp);
		} else {
			throw new EmployeeNotFoundException("Could Not Find Any Employee with Username" + username);
		}
	}

	public Employee getByResetPassword(String token) {
		return employeeRepo.findByResetPassword(token);
	}

	public void updatePassword(Employee employee, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		employee.setPassword(encodedPassword);
		employee.setResetPassword(encodedPassword);
		employeeRepo.save(employee);
	}

	public boolean verifyEmp(String verificationToken) {
		Employee employee = employeeRepo.findByVerificationToken(verificationToken);
		if (employee == null || employee.isEnabled()) {
			return false;
		} else {
			employee.setVerificationToken(null);
			employee.setEnabled(true);
			employeeRepo.save(employee);
			return true;
		}
	}

	@Override
	public List<Employee> findAll() {
		return (List<Employee>) employeeRepo.findAll();
	}

	@Override
	public String addFile(MultipartFile image, MultipartFile resume, Employee employee) throws IOException {

		Employee emp = employeeRepo.findByEmpId(employee.getEmpId());
		
		emp.setImage(image.getBytes());
		emp.setResume(resume.getBytes());
		employeeRepo.save(emp);
		return "save";
	}

}

/*
 * @Override public String save(MultipartFile image, MultipartFile resume,
 * Employee employee) throws IOException, EmailAlreadyExistException,
 * MessagingException { Employee onboardEmployee = new
 * Employee(employee.getEmpId(), employee.getEmpFirstName(),
 * employee.getEmpLastName(), employee.getPassword(),
 * employee.getResetPassword(), employee.getPhnoeNumber(), employee.getGender(),
 * employee.getDateOfBirth(), employee.getAddress1(), employee.getAddress2(),
 * employee.getEmpEmail(), employee.getJoiningDate(),
 * employee.getRelievingDate(), employee.getManagerEmail(),
 * employee.getExperience(), employee.getQualication(),
 * employee.getBankAccountNo(), employee.getIfscCode(), employee.getBankName(),
 * employee.getBranchName(), image.getBytes(), resume.getBytes(),
 * employee.getEmployeement(), employee.getDesignation(), employee.getRoles(),
 * employee.getDepartment());
 * 
 * if (checkIfUserIdExist(employee.getEmpId())) { throw new
 * EmailAlreadyExistException("Employee Id already Exist!. Please check the Id."
 * , 409); } if (checkIfUserExist(employee.getEmpEmail())) { throw new
 * EmailAlreadyExistException("Email Id already registered! Please try a different Email Id."
 * , 409); } String randomCode = RandomString.make(64);
 * System.out.println(randomCode);
 * onboardEmployee.setVerificationToken(randomCode); employee.setEnabled(false);
 * 
 * employeeRepo.save(onboardEmployee); MimeMessage message =
 * javaMailSender.createMimeMessage(); MimeMessageHelper helper = new
 * MimeMessageHelper(message); helper.setTo(employee.getEmpEmail());
 * helper.setSubject("PeopleCentral Onboarding Verification"); String content =
 * "<center><h1>Welcome to PeopleCentral</h1>" + "<h3> Dear, " +
 * employee.getEmpFirstName() + "</h3>" +
 * "<h4>Please click the link below to Activate your account</h4></center>" +
 * "<center>" +
 * "http://localhost:8045/application/verifyEmployee?verificationToken=" +
 * onboardEmployee.getVerificationToken() + "</center>";
 * 
 * message.setContent(content, "text/html; charset=utf-8");
 * javaMailSender.send(message); return "Employee Onboarded"; }
 */
