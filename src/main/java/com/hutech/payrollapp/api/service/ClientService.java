package com.hutech.payrollapp.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hutech.payrollapp.api.model.Client;
import com.hutech.payrollapp.api.model.Employee;

@Service
public interface ClientService {

	String save(Client client);
	List<Client> findAll();
}
