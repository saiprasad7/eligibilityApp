package com.eligibility.benefit.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Policies {
	
	@Id
	private String id;
	
	private String policyId;
	
	private String policyName;
	
	private String policyBenefits;
	
	private Long claimableAmount;

}
