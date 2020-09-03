package com.eligibility.benefit.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org. springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.PolicyRepository;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.util.LoggerUtil;


@Service
public class PoliciesService {

	protected Logger logger = LoggerFactory.getLogger(PoliciesService.class);
	@Autowired
	private PolicyRepository policyRepository;

	public Policies getPolicyDetails(String policyId) {
		LoggerUtil.infoLog(logger,"get the policy id"+policyId);
		Policies policy=policyRepository.findByPolicyId(policyId);
		LoggerUtil.infoLog(logger,"Getting policy information");
		return policy;
	}

	public List<Policies> getAllPolicies() {
		LoggerUtil.infoLog(logger,"call all policies in service before findAll");
		return policyRepository.findAll();
	}
	
	public List<Policies> getPolicyDetailsList(List<String> policyIdList) {
		LoggerUtil.infoLog(logger,"get the policy id List"+policyIdList);
		List<Policies> policyList = new ArrayList<>();
		policyIdList.stream().forEach(policyId -> {
			Policies policy=policyRepository.findByPolicyId(policyId);
			policyList.add(policy);
		});
		LoggerUtil.infoLog(logger,"Getting policy List information");
		return policyList;
	}

}
