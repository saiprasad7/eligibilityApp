package com.eligibility.benefit.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org. springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.PolicyRepository;
import com.eligibility.benefit.model.Policies;


@Service
public class PoliciesService {

	protected Logger logger = LoggerFactory.getLogger(PoliciesService.class);
	@Autowired
	private PolicyRepository policyRepository;

	public Policies getPolicyDetails(String policyId) {
		logger.info("get the policy id"+policyId);
		Policies policy=policyRepository.findByPolicyId(policyId);
		logger.info("Getting policy information");
		return policy;
	}

	public List<Policies> getAllPolicies() {
		logger.info("call all policies in service before findAll");
		return policyRepository.findAll();
	}

}
