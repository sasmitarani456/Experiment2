package com.hutech.payrollapp.api.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hutech.payrollapp.api.exceptionhandler.EmailAlreadyExistException;
import com.hutech.payrollapp.api.model.Employee;
import com.hutech.payrollapp.api.repository.EmployeeRepository;
import com.hutech.payrollapp.api.service.EmployeeService;
import com.hutech.payrollapp.api.serviceImpl.CSVhelper;
import com.hutech.payrollapp.api.serviceImpl.EmployeeServiceImpl;
import com.hutech.payrollapp.api.serviceImpl.Response;

import springfox.documentation.service.ResponseMessage;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeServiceImpl employeeServiceImpl;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	Logger log;

	
	@PostMapping("/addEmployee")
	public String onboardEmployee(@RequestBody Employee employee)
			throws MessagingException, EmailAlreadyExistException {
		employeeService.save(employee);
		
		return "Employee Onboarded. Please check your registered email id: " + employee.getEmpEmail();
	}
	
	@PostMapping("/addMultipartfile")
	public String addMultipartFile(@RequestParam("image") MultipartFile
			  image,@RequestParam("resume") MultipartFile resume, @RequestPart Employee
			  employee) throws IOException {
		employeeService.addFile(image,resume,employee);
		return "file added sucessfully";
	}
	@GetMapping("/onboardedEmployees")
	public List<Employee> findall() {
		return (List<Employee>) employeeService.findAll();
	}

	@GetMapping("/createPassword")
	public ModelAndView openForm() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("createPasswordPage");
		return mv;
	}

	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		List<Employee> employee = employeeServiceImpl.getByResetPassword(token);
		employeeServiceImpl.updatePassword(employee, password);
		model.addAttribute("message", "You have successfully changed your password.");
		return "message";
	}

	@GetMapping("/verifyEmployee")
	public ModelAndView verifyEmp(@Param("verificationToken") String verificationToken) {
		if (employeeServiceImpl.verifyEmp(verificationToken)) {
			ModelAndView activationsuccess = new ModelAndView();
			activationsuccess.setViewName("activationsuccess");
			return activationsuccess;
		} else {
			ModelAndView activationfail = new ModelAndView();
			activationfail.setViewName("activationfail");
			return activationfail;
		}
	}

	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("loginSuccess");
		return mv;
	}
	
	
	
	  @PostMapping("/upload") 
	  public ResponseEntity<Response>
				uploadFile(@RequestParam("file") MultipartFile file) {
			String message = "";

			if (CSVhelper.hasCSVFormat(file)) {
				try {
					//System.Net.ServicePointManager.Expect100Continue = false;
			//		log.info("File Updated successfully");
					System.out.println("File Updated successfully");
					employeeService.saveA(file);
					
					
					message = "Uploaded the file successfully: " + file.getOriginalFilename();
					return (ResponseEntity<Response>) ResponseEntity.status(HttpStatus.OK).body(new Response(message));
				} catch (Exception e) {
					message = "Could not upload the file: " + file.getOriginalFilename() + "!";
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(message));
				}
			}

			message = "Please upload a csv file!";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(message));
		}

		@GetMapping("/tutorials")
		public ResponseEntity<List<Employee>> getAllTutorials() {
			try {
				List<Employee> employee = employeeService.getAllcsv();

				if (employee.isEmpty()) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
				}

				return new ResponseEntity<>(employee, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

}

/*
 * @PostMapping("/onboardEmployee") public String
 * addEmployee(@Valid @RequestParam("image") MultipartFile
 * image,@RequestParam("resume") MultipartFile resume, @RequestPart Employee
 * employee) throws IOException, IdAlreadyExistException,
 * EmailAlreadyExistException, MessagingException { employeeService.save(image,
 * resume, employee); return
 * "Employee Onboarded. Please check your registered email id: " +
 * employee.getEmpEmail(); }
 */

/*
 * @PostMapping("/check") public ResponseEntity<?> empInfo(@RequestBody Employee
 * emp) { final UserDetails userDetails =
 * employeeServiceImpl.loadUserByUsername(emp.getEmpEmail()); return
 * ResponseEntity.ok(userDetails); }
 */

/*
 * MimeMessage message = mailSender.createMimeMessage(); MimeMessageHelper
 * helper = new MimeMessageHelper(message);
 * helper.setTo(employee.getEmpEmail());
 * helper.setSubject("PeopleCentral Onboarding Verification"); String content =
 * "<center><h1>Welcome to PeopleCentral</h1>" + "<h3> Dear, " +
 * employee.getEmpFirstName() + "</h3>" +
 * "<h4>Please click the link below to Activate your account</h4></center>" +
 * "<center>" +
 * "http://localhost:8045/application/createPassword/verify?verificationCode=" +
 * "</center>"; message.setContent(content, "text/html; charset=utf-8");
 * mailSender.send(message);
 */