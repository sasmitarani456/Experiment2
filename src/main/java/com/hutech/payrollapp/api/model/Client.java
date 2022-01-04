package com.hutech.payrollapp.api.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "client_details")
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clentId;
	private String customerName;
	private String website;
	private String email;
	private String addressLine1;
	private String addressLine2;
	private int phoneNumber;
	private String GSTNumber;
	private String CIN;
	private String paymentTerms;
	private String currency;

	public int getClentId() {
		return clentId;
	}

	public void setClentId(int clentId) {
		this.clentId = clentId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGSTNumber() {
		return GSTNumber;
	}

	public void setGSTNumber(String gSTNumber) {
		GSTNumber = gSTNumber;
	}

	public String getCIN() {
		return CIN;
	}

	public void setCIN(String cIN) {
		CIN = cIN;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Client(int clentId, String customerName, String website, String email, String addressLine1,
			String addressLine2, int phoneNumber, String gSTNumber, String cIN, String paymentTerms, String currency) {
		super();
		this.clentId = clentId;
		this.customerName = customerName;
		this.website = website;
		this.email = email;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.phoneNumber = phoneNumber;
		GSTNumber = gSTNumber;
		CIN = cIN;
		this.paymentTerms = paymentTerms;
		this.currency = currency;
	}

	public Client() {
		super();
	}

}
