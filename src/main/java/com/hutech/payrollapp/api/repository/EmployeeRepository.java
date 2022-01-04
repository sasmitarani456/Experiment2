package com.hutech.payrollapp.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hutech.payrollapp.api.model.Department;
import com.hutech.payrollapp.api.model.Employee;
import com.hutech.payrollapp.api.model.Holiday;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {

	Employee findByEmpEmail(String empEmail);

	Employee findByEmpId(String empId);

	List<Employee> findByResetPassword(String token);
	
	@Query("SELECT t FROM Employee t WHERE t.verificationToken = ?1")
	Employee findByVerificationToken(String verificationToken);

	//void save(Optional<Employee> emp);
	@Query(value = "select * from department where r.department_name=:departmentName", nativeQuery = true)
	public Department findByDepartmentName(String departmentName);

}
