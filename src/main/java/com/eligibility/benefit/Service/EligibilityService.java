package com.eligibility.benefit.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.EligibilityCheck;
import com.eligibility.benefit.model.Subscribers;


@Service
public class EligibilityService {

	protected Logger logger = LoggerFactory.getLogger(EligibilityService.class);
	@Autowired
	private SubscriberRepository subscriberRepository;

	public EligibilityCheck getEligibility(String subscriberId, String uniqueId, String plan) {
		logger.info("Getting Eligibility info");
		EligibilityCheck eligible= new EligibilityCheck();
		//boolean isEligibile=false;
		try {
		Subscribers subscribers=subscriberRepository.findBySubscriberId(subscriberId);
		if(null!=subscribers) {
	//	boolean isEligibile=false;
			subscribers.getDependents().stream().forEach(dependent -> {
				boolean isEligibile=false;
				if(dependent.getDependentId().equals(uniqueId)&& dependent.getDependentBenefits().get(0).getCurrentEligibleAmount()>0){
					isEligibile=true;
					subscribers.getBenefits().stream().forEach(benefitPlan -> {
						if(benefitPlan.getPolicyId().equals(plan)) {
							eligible.setSubscriberId(subscribers.getSubscriberId());	
							eligible.setEligible(true);
							eligible.setUniqueId(dependent.getDependentId());
							eligible.setPlanCode(benefitPlan.getPolicyId());
							eligible.setRelationShip(subscribers.getDependents());
							eligible.setTotalEligibleAmount(benefitPlan.getTotalEligibleAmount());
							eligible.setCurrentEligibleAmount(benefitPlan.getCurrentEligibleAmount());
						    }
					      });		
						}
			});
			logger.info("the subsciber is eligible for the benefit");	
	}
	//logger.info("the subsciber is not eligible for the benefit");
		}
		catch(Exception e) {
			logger.info("the subsciber is not eligible for the benefit"+e);
		}
	
		return eligible;
	}

}
