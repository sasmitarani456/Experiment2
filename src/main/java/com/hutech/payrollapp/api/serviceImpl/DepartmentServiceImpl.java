package com.hutech.payrollapp.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.hutech.payrollapp.api.model.Department;
import com.hutech.payrollapp.api.repository.DepartmentRepository;
import com.hutech.payrollapp.api.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	
	@Autowired
	private DepartmentRepository departmentRepo;

	@Override
	public String save(Department department) {
		departmentRepo.save(department);
		return "Department Added";
	}

	@Override
	public List<Department> get() {
		List<Department> department = (List<Department>) departmentRepo.findAll();
		return department;
		
	}

}
