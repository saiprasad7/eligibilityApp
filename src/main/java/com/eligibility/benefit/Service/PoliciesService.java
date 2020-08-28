package com.eligibility.benefit.Service;

import java.util.List;

import org. springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.PolicyRepository;
import com.eligibility.benefit.model.Policies;


@Service
public class PoliciesService {

	@Autowired
	private PolicyRepository policyRepository;

	public Policies getPolicyDetails(String policyId) {
		Policies policy=policyRepository.findByPolicyId(policyId);
		return policy;
	}

	public List<Policies> getAllPolicies() {
		return policyRepository.findAll();
	}

}
