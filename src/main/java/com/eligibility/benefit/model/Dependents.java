package com.eligibility.benefit.model;

import java.util.Date;
import java.util.List;


public class Dependents {

	
	private String dependentId;
	private Name dependentName;
	private Date dependentDateOfBirth;
	private Address dependentAddress;
	//private Benefit[] dependentbenefit=new Benefit[1];

	private List<Benefit> dependentBenefits;
	public String getDependentId() {
		return dependentId;
	}
	public void setDependentId(String dependentId) {
		this.dependentId = dependentId;
	}
	public Name getDependentName() {
		return dependentName;
	}
	public void setDependentName(Name dependentName) {
		this.dependentName = dependentName;
	}
	
	public Date getDependentDateOfBirth() {
		return dependentDateOfBirth;
	}
	public void setDependentDateOfBirth(Date dependentDateOfBirth) {
		this.dependentDateOfBirth = dependentDateOfBirth;
	}
	public Address getDependentAddress() {
		return dependentAddress;
	}
	public void setDependentAddress(Address dependentAddress) {
		this.dependentAddress = dependentAddress;
	}
	
	public List<Benefit> getDependentbenefit() {
		return dependentBenefits;
	}
	public void setDependentbenefit(List<Benefit> dependentbenefit) {
		this.dependentBenefits = dependentbenefit;
	}
	public Dependents() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Dependents [dependentId=" + dependentId + ", dependentName=" + dependentName + ", dependentDateOfBirth="
				+ dependentDateOfBirth + ", dependentAddress=" + dependentAddress + ", dependentBenefits="
				+ dependentBenefits + "]";
	}
	
	
	
}
