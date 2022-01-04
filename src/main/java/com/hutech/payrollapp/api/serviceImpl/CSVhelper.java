package com.hutech.payrollapp.api.serviceImpl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.hutech.payrollapp.api.exceptionhandler.EmailAlreadyExistException;
import com.hutech.payrollapp.api.model.Department;
import com.hutech.payrollapp.api.model.Designation;
import com.hutech.payrollapp.api.model.Employee;
import com.hutech.payrollapp.api.repository.DepartmentRepository;
import com.hutech.payrollapp.api.repository.EmployeeRepository;

import net.bytebuddy.utility.RandomString;

public class CSVhelper {

	
	@Autowired
	private static JavaMailSender javaMailSender;
	@Autowired
	private static EmployeeRepository employeeRepository;
	
	public static String TYPE = "text/csv";
	  static String[] HEADERs = { "empId", "empFirstName", "empLastName", "phnoeNumber","gender","dateOfBirth","address1","address2","empEmail","joiningDate","relievingDate","managerEmail","experience","qualification","bankAccountNo","ifscCode",
			  "bankName","branchName","employeement", "department"};

	  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }
public static List<Employee> csvToTutorials(InputStream inputStream) throws MessagingException, EmailAlreadyExistException {
  try (BufferedReader fileReader = new BufferedReader(new InputStreamReader((InputStream) inputStream, "UTF-8"));
      CSVParser csvParser = new CSVParser(fileReader,
      CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

	  List<Employee> employees = new ArrayList<Employee>();

	  Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	  for (CSVRecord csvRecord : csvRecords) {
		  
		
		 Gson gson = new Gson();
		  Employee emp = new Employee( 
				  csvRecord.get("empId"),
		  csvRecord.get("empFirstName"), csvRecord.get("empLastName"),
		  csvRecord.get("phnoeNumber"), csvRecord.get("gender"),
		  csvRecord.get("dateOfBirth"), csvRecord.get("address1"),
		  csvRecord.get("address2"), csvRecord.get("empEmail"),
		  csvRecord.get("joiningDate"), csvRecord.get("relievingDate"),
		  csvRecord.get("managerEmail"), Integer.parseInt(csvRecord.get("experience")),
		  
		  csvRecord.get("qualification"),
		  Integer.parseInt(csvRecord.get("bankAccountNo")), csvRecord.get("ifscCode"),
		  csvRecord.get("bankName"), csvRecord.get("branchName"),
		  		  csvRecord.get("employeement"),gson.fromJson(csvRecord.get("department"), Department.class)
		  		  );
		  
					/*
					 * String randomCode = RandomString.make(64); System.out.println(randomCode);
					 * emp.setVerificationToken(randomCode); emp.setEnabled(false);
					 */
      employees.add(emp);
		
      
    }
    
    return employees;
  } catch (IOException e) {
    throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
  }

  }

public static ByteArrayInputStream tutorialsToCSV(List<Employee> tutorials) {
    final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
    
    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      for (Employee tutorial : tutorials) {
        List<String> data = Arrays.asList(
        		tutorial.getEmpId(),
              tutorial.getEmpFirstName(),
              tutorial.getEmpLastName(),
              tutorial.getPhnoeNumber(),
             // tutorial.getTitle(),
              tutorial.getGender(),
              tutorial.getDateOfBirth(),
              tutorial.getAddress1(),
              tutorial.getAddress2(),
              tutorial.getEmpEmail(),
              tutorial.getJoiningDate(),
              tutorial.getRelievingDate(),
              tutorial.getManagerEmail(),
              String.valueOf(tutorial.getExperience()),
              tutorial.getQualication(),
              String.valueOf(tutorial.getBankAccountNo()),
              tutorial.getIfscCode(),
              tutorial.getBankName(),
              tutorial.getBranchName(),
              tutorial.getEmployeement(),String.valueOf(tutorial.getDepartment()));
              
              
              //String.valueOf(tutorial.isPublished())
           

        csvPrinter.printRecord(data);
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }

}
