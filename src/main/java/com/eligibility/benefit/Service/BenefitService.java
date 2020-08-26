package com.eligibility.benefit.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org. springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.PolicyRepository;
import com.eligibility.benefit.Repo.SubscriberRepo;
import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.Benefit;
import com.eligibility.benefit.model.Dependents;
import com.eligibility.benefit.model.EligibilityCheck;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;

import lombok.Getter;
import lombok.Setter;


@Service
public class BenefitService {
	
	@Autowired
	SubscriberRepository subscriberRepo;
	@Autowired
	PolicyRepository policyRepository;
	
	public List<Subscribers> findAll(){
		System.out.println("-----get mapping ***-");
		return subscriberRepo.findAll();
	}

	public Policies getBenefitService(String policyId) {
		// TODO Auto-generated method stub
		Policies policy=policyRepository.findByPolicyId(policyId);
		return policy;
	}

	public EligibilityCheck getEligibility(String subscriberId, String uniqueId, String plan) {
		EligibilityCheck eligible= new EligibilityCheck();
	Boolean isEligibile=false;
		// TODO Auto-generated method stub
	Subscribers subscribers=subscriberRepo.findBySubscriberId(subscriberId);
		System.out.println("subscriber-----------"+subscribers);
		if(subscribers.getDependents().get(0).getDependentId().equals(plan)&&subscribers.getBenefits().get(0).getPolicyId().equals(uniqueId)) {
			eligible.setUniqueId(subscribers.getDependents().get(0).getDependentId());
			eligible.setPlanCode(subscribers.getBenefits().get(0).getPolicyId());
			eligible.setCurrentEligibleAmount(subscribers.getBenefits().get(0).getCurrentEligibleAmount());
			if(subscribers.getDependents().get(0).getDependentBenefits().get(0).getCurrentEligibleAmount()>0) {
				isEligibile=true;
		eligible.setEligible(isEligibile);	
		
		}
		eligible.setSubscriberId(subscribers.getSubscriberId());	
		eligible.setRelationShip(subscribers.getDependents());
		
		
	}
	
	return eligible;
	}

	public List<Policies> getAllPolicies() {
		// TODO Auto-generated method stub
		return policyRepository.findAll();
	}

}
