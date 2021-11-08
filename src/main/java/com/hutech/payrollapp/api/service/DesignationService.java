package com.hutech.payrollapp.api.service;

import java.util.List;

import com.hutech.payrollapp.api.model.Designation;

public interface DesignationService {

	String save(Designation designation);

	List<Designation> get();

}
