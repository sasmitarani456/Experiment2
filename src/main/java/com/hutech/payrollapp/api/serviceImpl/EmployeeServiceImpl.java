package com.hutech.payrollapp.api.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		List<Employee> emp = (List<Employee>) employeeRepo.findByEmpEmail(username);
		if (emp != null) {
			((Employee) emp).setResetPassword(token);
			
			employeeRepo.saveAll(emp);
		} else {
			throw new EmployeeNotFoundException("Could Not Find Any Employee with Username" + username);
		}
	}

	public List<Employee> getByResetPassword(String token) {
		return employeeRepo.findByResetPassword(token);
	}

	public void updatePassword(List<Employee> employee, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(newPassword);
		for(Employee emp:employee) {
			emp.setPassword(encodedPassword);
		emp.setResetPassword(encodedPassword);}
		
		employeeRepo.saveAll(employee);
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

  
	@Override
	public void saveA(MultipartFile file) throws MessagingException, EmailAlreadyExistException {
		try {
			List<Employee> employees = CSVhelper.csvToTutorials(file.getInputStream());
			
			
			employeeRepo.saveAll(employees);
			
			for (Employee emp : employees) {

				String randomCode = RandomString.make(64);
				System.out.println(randomCode);
				emp.setVerificationToken(randomCode);
				emp.setEnabled(false);
				// employeeRepo.save(emp);
				MimeMessage message = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message);
				employeeRepo.save(emp);
				helper.setTo(emp.getEmpEmail());
				helper.setSubject("PeopleCentral Onboarding Verification");
				String content = "<center><h1>Welcome to PeopleCentral</h1>" + "<h3> Dear, " + emp.getEmpFirstName()
						+ "</h3>" + "<h4>Please click the link below to Activate your account</h4></center>"
						+ "<center>" + "http://localhost:8080/application/verifyEmployee?verificationToken="
						+ emp.getVerificationToken() + "</center>";

				message.setContent(content, "text/html; charset=utf-8");
				javaMailSender.send(message);
			}
			//helper.setTo(employees.getEmpEmail());
			
			//return "Employee Info Saved";
			
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}
	
	public ByteArrayInputStream load() {
	    List<Employee> tutorials =  (List<Employee>) employeeRepo.findAll();

	    ByteArrayInputStream in = CSVhelper.tutorialsToCSV(tutorials);
	    return in;
	  }

	@Override
	public List<Employee> getAllcsv() {
		return (List<Employee>) employeeRepo.findAll();
	}
	
}

