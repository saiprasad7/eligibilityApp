package com.eligibility.benefit.model;

import lombok.Data;

@Data
public class Benefit {

	private String id;
	private String policyId;
	private String policyName;
	private String policyBenefits;
	private long totalEligibleAmount;
	private int claimedAmount;
	private long currentEligibleAmount;

}
