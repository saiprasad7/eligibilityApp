package com.eligibility.benefit.model;

import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import com.eligibility.benefit.Repo.CascadeSave;

import lombok.Data;

@Data
public class Benefit {

//	@DBRef(db="policies")
	//@JoinColumn(name = "policies_id", nullable = false)
	 @CascadeSave
//	@Id
//	 @Field("id")
	private String id;
	//@DBRef(db="policies")
	//@CascadeSave
	private String policyId;
//	@DBRef(db="policies")
//	@CascadeSave
	private String policyName;
//	@DBRef(db="policies")
//	@CascadeSave
	private String policyBenefits;
//	@DBRef(db="policies")
//	@CascadeSave
	private long totalEligibleAmount;
	private int claimedAmount;
	private long currentEligibleAmount;
	
	
	/*public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getPolicyBenefits() {
		return policyBenefits;
	}
	public void setPolicyBenefits(String policyBenefits) {
		this.policyBenefits = policyBenefits;
	}
	public long getTotalEligibleAmount() {
		return totalEligibleAmount;
	}
	public void setTotalEligibleAmount(long totalEligibleAmount) {
		this.totalEligibleAmount = totalEligibleAmount;
	}
	public int getClaimedAmount() {
		return claimedAmount;
	}
	public void setClaimedAmount(int claimedAmount) {
		this.claimedAmount = claimedAmount;
	}
	public long getCurrentEligibleAmount() {
		return currentEligibleAmount;
	}
	public void setCurrentEligibleAmount(long currentEligibleAmount) {
		this.currentEligibleAmount = currentEligibleAmount;
	}
	public Benefit() {
		super();
		// TODO Auto-generated constructor stub
	}*/
	/*@Override
	public String toString() {
		return "Benefit [id=" + id + ", policyId=" + policyId + ", policyName=" + policyName + ", policyBenefits="
				+ policyBenefits + ", totalEligibleAmount=" + totalEligibleAmount + ", claimedAmount=" + claimedAmount
				+ ", currentEligibleAmount=" + currentEligibleAmount + "]";
	}*/
	

}
