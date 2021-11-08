package com.hutech.payrollapp.api.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hutech.payrollapp.api.model.Department;
import com.hutech.payrollapp.api.model.Designation;
import com.hutech.payrollapp.api.repository.DesignationRepository;
import com.hutech.payrollapp.api.service.DesignationService;

@Service
public class DesignationServiceImpl implements DesignationService {
	
	@Autowired
	private DesignationRepository designationRepo;

	@Override
	public String save(Designation designation) {
		designationRepo.save(designation);
		return "Designation Saved";
	}

	@Override
	public List<Designation> get() {
		List<Designation> designation = (List<Designation>) designationRepo.findAll();
		return designation;
	}

}
