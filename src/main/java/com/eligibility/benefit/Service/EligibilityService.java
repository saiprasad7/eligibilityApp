package com.eligibility.benefit.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.EligibilityCheck;
import com.eligibility.benefit.model.Subscribers;


@Service
public class EligibilityService {

	@Autowired
	private SubscriberRepository subscriberRepository;

	public EligibilityCheck getEligibility(String subscriberId, String uniqueId, String plan) {
		EligibilityCheck eligible= new EligibilityCheck();
		Boolean isEligibile=false;
		Subscribers subscribers=subscriberRepository.findBySubscriberId(subscriberId);
		if(subscribers.getDependents().get(0).getDependentId().equals(uniqueId)&&subscribers.getBenefits().get(0).getPolicyId().equals(plan)) {
			eligible.setSubscriberId(subscribers.getSubscriberId());
			eligible.setUniqueId(subscribers.getDependents().get(0).getDependentId());
			eligible.setPlanCode(subscribers.getBenefits().get(0).getPolicyId());
			if(subscribers.getDependents().get(0).getDependentBenefits().get(0).getCurrentEligibleAmount()>0) {
				isEligibile=true;
				eligible.setEligible(isEligibile);   
			}
			eligible.setRelationShip(subscribers.getDependents());
			eligible.setTotalEligibleAmount(subscribers.getBenefits().get(0).getTotalEligibleAmount());
			eligible.setCurrentEligibleAmount(subscribers.getBenefits().get(0).getCurrentEligibleAmount());
		}
		return eligible;

	}

}
