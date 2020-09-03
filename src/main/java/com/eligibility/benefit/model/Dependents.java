package com.eligibility.benefit.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Dependents {
	
	private String dependentId;
	private Name dependentName;
	private Date dependentDateOfBirth;
	private Address dependentAddress;
	private String dependentRelation;
	private List<Benefit> dependentBenefits;
	
}
