package com.eligibility.benefit.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.EligibilityCheck;
import com.eligibility.benefit.model.Subscribers;
import com.eligibility.benefit.util.Constants;
import com.eligibility.benefit.util.ErrorResponse;
import com.eligibility.benefit.util.ExceptionHandlingUtil;
import com.eligibility.benefit.util.LoggerUtil;
import com.eligibility.benefit.util.ResponseHandlingUtil;


@Service
public class EligibilityService {

	protected Logger logger = LoggerFactory.getLogger(EligibilityService.class);
	@Autowired
	private SubscriberRepository subscriberRepository;

	//public EligibilityCheck getEligibility(String subscriberId, String uniqueId, String plan) {
	public Object getEligibility(String subscriberId, String uniqueId, String plan) {
		LoggerUtil.infoLog(logger, "Getting Eligibility info");
		EligibilityCheck eligible= new EligibilityCheck();
		//boolean isEligibile=false;
		//Dependents dependent=null;
		try {
		Subscribers subscribers=subscriberRepository.findBySubscriberId(subscriberId);
		if(null!=subscribers) {
			subscribers.getDependents().stream().forEach(dependent -> {
				boolean isEligibile=false;
				if(dependent.getDependentId().equals(uniqueId) && dependent.getDependentBenefits().get(0).getCurrentEligibleAmount()>0){
				//	if(dependent.getDependentBenefits().get(0).getCurrentEligibleAmount()>0) {
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
				if (eligible.isEligible()==false) {
					LoggerUtil.warnLog(logger, "the Given Dependent id is not Matched");
				//checkDependent(eligible);
				return ExceptionHandlingUtil.returnErrorObject(Constants.NO_CONTENT,Constants.NO_CONT_CODE);
				}
			LoggerUtil.infoLog(logger,"the subsciber is eligible for the benefit");	
				
			}
		
		else if (null==subscribers) {
			LoggerUtil.warnLog(logger, "Response value has null");
			return ExceptionHandlingUtil.returnErrorObject(Constants.PAGE_NOT_FOUND,Constants.PAGE_NOT_FND);
		}	
		}
		catch(Exception e) {
			LoggerUtil.errorLog(logger, "the subsciber is not eligible for the benefit", e);
		}
	
		return eligible;
	}
}
