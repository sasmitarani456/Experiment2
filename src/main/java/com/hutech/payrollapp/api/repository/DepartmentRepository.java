package com.hutech.payrollapp.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hutech.payrollapp.api.model.Department;
import com.hutech.payrollapp.api.model.Employee;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer>{
//@Query("SELECT d FROM department d WHERE d.departmentName = ?1")
	//@Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
	@Query("FROM Department WHERE departmentName = ?1")
	Department findByDepartmentName(String departmentName);

	//Department findByDepartmentName(String departmentName);
}
