package com.hutech.payrollapp.api.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.hutech.payrollapp.api.exceptionhandler.EmailAlreadyExistException;
import com.hutech.payrollapp.api.model.Employee;

public interface EmployeeService {

	String save(Employee employee) throws MessagingException,EmailAlreadyExistException;

	List<Employee> findAll();

	

	

	String addFile(MultipartFile image, MultipartFile resume, Employee employee) throws IOException;

	//void saveA(MultipartFile file);

	List<Employee> getAllcsv();
	//public void saveCSV() throws IOException;

	void saveA(MultipartFile file) throws MessagingException, EmailAlreadyExistException;

}




















/*	String save(MultipartFile image, MultipartFile resume, Employee employee)
throws IOException, IdAlreadyExistException, EmailAlreadyExistException, MessagingException;*/
