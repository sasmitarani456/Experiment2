package com.hutech.payrollapp.api.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name="empId")
	private String empId;
	
	@NotEmpty
	@Pattern(regexp = "^[A-Za-z]*$")
	@Size(min = 3, max = 15, message = "FIRST NAME MUST BE BETWEEN 3 TO 15 CHARACTERS")
	//@Column(name="empFirstName")
	private String empFirstName;

	@NotEmpty
	@Pattern(regexp = "^[A-Za-z]*$")
	@Size(min = 3, max = 15, message = "LAST NAME MUST BE BETWEEN 3 TO 15 CHARACTERS")
	//@Column(name="empLastName")
	private String empLastName;
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String password;
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String resetPassword;
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private String verificationToken;
	private boolean enabled;

	@NotEmpty
	@Size(min = 10, max = 20, message = "PHONE NUMBER MUST BE BETWEEN 10 TO 20 CHARACTERS")
	//@Column(name="phnoeNumber")
	private String phnoeNumber;

	@NotEmpty
	//@Column(name="gender")
	private String gender;

	@NotEmpty
	//@Column(name="dateOfBirth")
	private String dateOfBirth;

	@NotEmpty
	@Size(min = 5, max = 50, message = "ADDRESS MUST BE 5 TO 50 CHARACTERS")
	//@Column(name="address1")
	private String address1;
	//@Column(name="address2")
	private String address2;

	@NotEmpty
	@Size(min = 10, max = 50, message = "ADDRESS MUST BE 10 TO 30 CHARACTERS")
	@Email
	//@Column(name="empEmail")
	private String empEmail;
	//@Column(name="joiningDate;")
	private String joiningDate;
	//@Column(name="relievingDate")
	private String relievingDate;

	@NotEmpty
	@Email
	//@Column(name="managerEmail")
	private String managerEmail;
	//@Column(name="experience")
	private int experience;
	//@Column(name="qualication")
	private String qualication;
	@Column(name="bankAccountNo")
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	private int bankAccountNo;
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	//@Column(name="ifscCode")
	private String ifscCode;
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	//@Column(name="bankName")
	private String bankName;
	@JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
	//@Column(name="branchName")
	private String branchName;

	@Lob
	private byte[] image;

	@Lob
	private byte[] resume;

	
	private String employeement;

	@OneToOne
	@JoinColumn(name = "designationName")
	private Designation designation;

	@ManyToOne
	@JoinColumn(name = "roleName")
	private Role roles;

	@OneToOne
	@JoinColumn(name = "departmentName")
	private Department department;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpFirstName() {
		return empFirstName;
	}

	public void setEmpFirstName(String empFirstName) {
		this.empFirstName = empFirstName;
	}

	public String getEmpLastName() {
		return empLastName;
	}

	public void setEmpLastName(String empLastName) {
		this.empLastName = empLastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPhnoeNumber() {
		return phnoeNumber;
	}

	public void setPhnoeNumber(String phnoeNumber) {
		this.phnoeNumber = phnoeNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getRelievingDate() {
		return relievingDate;
	}

	public void setRelievingDate(String relievingDate) {
		this.relievingDate = relievingDate;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getQualication() {
		return qualication;
	}

	public void setQualication(String qualication) {
		this.qualication = qualication;
	}

	public int getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(int bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	
	  
	 

	public byte[] getImage() {
		return image;
	}

	public byte[] getResume() {
		return resume;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	
	 

	public void setResume(byte[] resume) {
		this.resume = resume;
	}

	
	public String getEmployeement() {
		return employeement;
	}

	public void setEmployeement(String employeement) {
		this.employeement = employeement;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public Role getRoles() {
		return roles;
	}

	public void setRoles(Role roles) {
		this.roles = roles;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	

	public Employee(String empId, String empFirstName, String empLastName, String password, String resetPassword,
			String phnoeNumber, String gender, String dateOfBirth, String address1, String address2, String empEmail,
			String joiningDate, String relievingDate, String managerEmail, int experience, String qualication,
			int bankAccountNo, String ifscCode, String bankName, String branchName,
			String employeement, Designation designation, Role roles, Department department) {
		super();
		this.empId = empId;
		this.empFirstName = empFirstName;
		this.empLastName = empLastName;
		this.password = password;
		this.resetPassword = resetPassword;
		this.phnoeNumber = phnoeNumber;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.address1 = address1;
		this.address2 = address2;
		this.empEmail = empEmail;
		this.joiningDate = joiningDate;
		this.relievingDate = relievingDate;
		this.managerEmail = managerEmail;
		this.experience = experience;
		this.qualication = qualication;
		this.bankAccountNo = bankAccountNo;
		this.ifscCode = ifscCode;
		this.bankName = bankName;
		this.branchName = branchName;
		/*
		 * this.image=image; this.resume=resume;
		 */
		this.employeement = employeement;
		this.designation = designation;
		this.roles = roles;
		this.department = department;
	}

	
	

	public Employee() {
		// TODO Auto-generated constructor stub
	}
	

	public Employee(String empId, String empFirstName, String empLastName, String phnoeNumber, String gender, String dateOfBirth,
			String address1, String address2, String empEmail, String joiningDate, String relievingDate, String managerEmail,
			int experience, String qualication, int bankAccountNo, String ifscCode, String bankName, String branchName,String employeement,
			Department department) {
		super();
		this.empId = empId;
		this.empFirstName = empFirstName;
		this.empLastName = empLastName;
		this.phnoeNumber = phnoeNumber;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.address1 = address1;
		this.address2 = address2;
		this.empEmail = empEmail;
		this.joiningDate = joiningDate;
		this.relievingDate = relievingDate;
		this.managerEmail = managerEmail;
		this.experience = experience;
		this.qualication = qualication;
		this.bankAccountNo = bankAccountNo;
		this.ifscCode = ifscCode;
		this.bankName = bankName;
		this.branchName = branchName;	
		this.employeement = employeement;
		this.department = department;
		//this.designation = designation;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empFirstName=" + empFirstName + ", empLastName=" + empLastName
				+ ", phnoeNumber=" + phnoeNumber + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", address1="
				+ address1 + ", address2=" + address2 + ", empEmail=" + empEmail + ", joiningDate=" + joiningDate
				+ ", relievingDate=" + relievingDate + ", managerEmail=" + managerEmail + ", experience=" + experience
				+ ", qualication=" + qualication + ", bankAccountNo=" + bankAccountNo + ", ifscCode=" + ifscCode
				+ ", bankName=" + bankName + ", branchName=" + branchName + ", employeement=" + employeement
				+ ", designation=" + designation + ", department=" + department + ", getEmpId()=" + getEmpId()
				+ ", getEmpFirstName()=" + getEmpFirstName() + ", getEmpLastName()=" + getEmpLastName()
				+ ", getPassword()=" + getPassword() + ", getResetPassword()=" + getResetPassword()
				+ ", getVerificationToken()=" + getVerificationToken() + ", isEnabled()=" + isEnabled()
				+ ", getPhnoeNumber()=" + getPhnoeNumber() + ", getGender()=" + getGender() + ", getDateOfBirth()="
				+ getDateOfBirth() + ", getAddress1()=" + getAddress1() + ", getAddress2()=" + getAddress2()
				+ ", getEmpEmail()=" + getEmpEmail() + ", getJoiningDate()=" + getJoiningDate()
				+ ", getRelievingDate()=" + getRelievingDate() + ", getManagerEmail()=" + getManagerEmail()
				+ ", getExperience()=" + getExperience() + ", getQualication()=" + getQualication()
				+ ", getBankAccountNo()=" + getBankAccountNo() + ", getIfscCode()=" + getIfscCode() + ", getBankName()="
				+ getBankName() + ", getBranchName()=" + getBranchName() + ", getImage()=" + Arrays.toString(getImage())
				+ ", getResume()=" + Arrays.toString(getResume()) + ", getEmployeement()=" + getEmployeement()
				+ ", getDesignation()=" + getDesignation() + ", getRoles()=" + getRoles() + ", getDepartment()="
				+ getDepartment() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	

	

	 
	
	

	

	

}
