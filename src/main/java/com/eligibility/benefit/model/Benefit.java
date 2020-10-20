package com.eligibility.benefit.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Benefit {

	private String id;
	private String policyId;
	private String policyName;
	private String policyBenefits;
	private long totalEligibleAmount;
	private int claimedAmount;
	private long currentEligibleAmount;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Benefit benefit = (Benefit) o;
		return Objects.equals(id, benefit.id) &&
				Objects.equals(policyId, benefit.policyId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, policyId);
	}
}
