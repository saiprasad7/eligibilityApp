package com.eligibility.benefit.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_ABSENT) 
@Data
public class EligibilityCheck {
	
	
	private String subscriberId;
	private String uniqueId;
	private String planCode;
	private boolean isEligible;
	private List<Dependents> relationShip;
	private Long totalEligibleAmount;
	private Long currentEligibleAmount;
	
}
