package com.eligibility.benefit.Service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.Dependents;
import com.eligibility.benefit.model.EligibilityCheck;
import com.eligibility.benefit.model.Subscribers;
import com.eligibility.benefit.model.Users;
import com.eligibility.benefit.util.Constants;
import com.eligibility.benefit.util.ExceptionHandlingUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EligibilityService {

	@Autowired
	private SubscriberRepository subscriberRepository;
	@Autowired
	private UserRepository userRepository;
	boolean isdependent = false;
	boolean ispolicyidnull = false;

	public Object getEligibility(String subscriberId, String uniqueId, String plan, String token) {
		log.info("Getting Eligibility info");
		EligibilityCheck eligible = new EligibilityCheck();
		Users user = userRepository.findByToken(token);

		log.info("Getting Eligibility info" + token);

		if (StringUtils.isEmpty(subscriberId) || StringUtils.isEmpty(plan)) {
			return ExceptionHandlingUtil.returnErrorObject("the given subscriber/policy is invalid",
					Constants.NULLCONT_CHECK);
		} else {
			try {
				Subscribers subscribers = subscriberRepository.findBySubscriberId(subscriberId);
				log.info("Getting Eligibility info" + subscribers);
				if (null != subscribers) {
					if (null == uniqueId || uniqueId.equals("00") || uniqueId.isEmpty()
							|| uniqueId.equals(subscriberId.concat("00"))) {
						validatePlan(subscribers, eligible, plan, uniqueId);
					} else {
						for (Dependents dependent : subscribers.getDependents()) {
							log.info("Getting info");
							if (null == dependent.getDependentId() || dependent.getDependentId().isEmpty()) {

								log.error("There is no dependent id for the subscriber", subscriberId);
								return ExceptionHandlingUtil.returnErrorObject(Constants.INVALIDDEPENDENT + uniqueId,
										Constants.PAGE_NOT_FND);
							} else if (dependent.getDependentId().equals(uniqueId)) {
								isdependent = true;
								dependent.getDependentBenefits().stream().forEach(dependentBenefit -> {

									if (dependentBenefit.getPolicyId().equals(plan)
											&& dependentBenefit.getCurrentEligibleAmount() > 0) {
										ispolicyidnull = true;
										eligible.setSubscriberId(subscribers.getSubscriberId());
										eligible.setEligible(true);
										eligible.setUniqueId(dependent.getDependentId());
										eligible.setPlanCode(dependentBenefit.getPolicyId());
										eligible.setRelationShip(subscribers.getDependents());
										eligible.setTotalEligibleAmount(dependentBenefit.getTotalEligibleAmount());
										eligible.setCurrentEligibleAmount(dependentBenefit.getCurrentEligibleAmount());

									} else if (dependentBenefit.getPolicyId().equals(plan)
											&& dependentBenefit.getCurrentEligibleAmount() == 0) {
										ispolicyidnull = true;
										eligible.setSubscriberId(subscribers.getSubscriberId());
										eligible.setEligible(false);
										eligible.setUniqueId(dependent.getDependentId());
										eligible.setPlanCode(dependentBenefit.getPolicyId());
										eligible.setRelationShip(subscribers.getDependents());
										eligible.setTotalEligibleAmount(dependentBenefit.getTotalEligibleAmount());
										eligible.setCurrentEligibleAmount(dependentBenefit.getCurrentEligibleAmount());
									}

								});
							}
						}
					}

					if (null == eligible.getPlanCode() && !isdependent) {
						log.info("the Given dependent id is not Matched");
						return ExceptionHandlingUtil.returnErrorObject(Constants.INVALIDDEPENDENT + uniqueId,
								Constants.PAGE_NOT_FND);
					}
					if (null == eligible.getPlanCode() && !ispolicyidnull) {
						log.info("the Given Policy id is not Matched");
						return ExceptionHandlingUtil.returnErrorObject(Constants.INVALIDPOLICY + plan,
								Constants.PAGE_NOT_FND);
					}

					log.info("the subsciber is eligible for the benefit");

				}

				else if (null == subscribers) {
					log.info("Response value has null");
					return ExceptionHandlingUtil.returnErrorObject(Constants.PAGE_NOT_FOUND + subscriberId,
							Constants.PAGE_NOT_FND);
				}
			}

			catch (Exception e) {
				log.error("the subsciber is not eligible for the benefit", e);
			}
		}

		isdependent = false;
		ispolicyidnull = false;
		return eligible;
	}

	private void validatePlan(Subscribers subscribers, EligibilityCheck eligible, String plan, String uniqueId) {
		subscribers.getBenefits().stream().forEach(benefitPlan -> {
			if (benefitPlan.getPolicyId().equals(plan) && benefitPlan.getCurrentEligibleAmount() > 0) {
				eligible.setSubscriberId(subscribers.getSubscriberId());
				eligible.setEligible(true);
				eligible.setUniqueId(uniqueId);
				eligible.setPlanCode(benefitPlan.getPolicyId());
				if (uniqueId.equals("00")) {
					eligible.setRelationShip(null);
				} else {
					eligible.setRelationShip(subscribers.getDependents());
				}
				eligible.setTotalEligibleAmount(benefitPlan.getTotalEligibleAmount());
				eligible.setCurrentEligibleAmount(benefitPlan.getCurrentEligibleAmount());
			} else {
				log.info("Invalid Policy id is provided");

			}
		});
	}

}
