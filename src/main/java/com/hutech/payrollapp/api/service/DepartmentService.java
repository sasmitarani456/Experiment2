package com.hutech.payrollapp.api.service;

import java.util.List;

import com.hutech.payrollapp.api.model.Department;

public interface DepartmentService {

	public String save(Department department);

	public List<Department> get();

}
