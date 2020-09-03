package com.eligibility.benefit.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.Dependents;
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

	public Object getEligibility(String subscriberId, String uniqueId, String plan) {
		LoggerUtil.infoLog(logger, "Getting Eligibility info");
		EligibilityCheck eligible= new EligibilityCheck();
		if("null".equals(subscriberId)||"null".equals(plan)) {
			return ExceptionHandlingUtil.returnErrorObject("the given subscriber/policy is invalid",Constants.NULLCONT_CHECK);	
		}
		else {
		try {
		Subscribers subscribers=subscriberRepository.findBySubscriberId(subscriberId);
		LoggerUtil.infoLog(logger, "Getting Eligibility info"+subscribers);
		if(null!=subscribers) {
			subscribers.getDependents().stream().forEach(dependent -> {
				LoggerUtil.infoLog(logger, "Getting info");
			
						if(null==dependent.getDependentId()||dependent.getDependentId().isEmpty()) {
							validatePlan(subscribers,eligible,plan,dependent);
							}	
						else if(dependent.getDependentId().equals(uniqueId)){
					validatePlan(subscribers,eligible,plan,dependent);		
						}						
			});	
			if (eligible.isEligible()==false) {
				return ExceptionHandlingUtil.returnErrorObject(Constants.INVALIDDEPENDENT +uniqueId,Constants.NO_CONT_CODE);
			}
				if (eligible.isEligible()==false &&eligible.getPlanCode()!=plan) {
					LoggerUtil.warnLog(logger, "the Given Policy id is not Matched");
			
				return ExceptionHandlingUtil.returnErrorObject(Constants.INVALIDPOLICY +plan,Constants.NO_CONT_CODE);
				}
				
			LoggerUtil.infoLog(logger,"the subsciber is eligible for the benefit");	
				
			}
		
		else if (null==subscribers) {
			LoggerUtil.warnLog(logger, "Response value has null");
			return ExceptionHandlingUtil.returnErrorObject(Constants.INVALIDSUBS +subscriberId,Constants.NO_CONT_CODE);
		}	
		}
		
		catch(Exception e) {
			LoggerUtil.errorLog(logger, "the subsciber is not eligible for the benefit", e);
		}
		}
		return eligible;
	}

	private void validatePlan(Subscribers subscribers, EligibilityCheck eligible, String plan, Dependents dependent) {
		// TODO Auto-generated method stub
		subscribers.getBenefits().stream().forEach(benefitPlan -> {
			if(benefitPlan.getPolicyId().equals(plan)&& benefitPlan.getCurrentEligibleAmount()>0) {
				eligible.setSubscriberId(subscribers.getSubscriberId());	
				eligible.setEligible(true);
				eligible.setUniqueId(dependent.getDependentId());
				eligible.setPlanCode(benefitPlan.getPolicyId());
				eligible.setRelationShip(subscribers.getDependents());
				eligible.setTotalEligibleAmount(benefitPlan.getTotalEligibleAmount());
				eligible.setCurrentEligibleAmount(benefitPlan.getCurrentEligibleAmount());
			    }
			else {
				LoggerUtil.infoLog(logger,"Invalid Policy id is provided");
			
			}
		      });
	}
}
