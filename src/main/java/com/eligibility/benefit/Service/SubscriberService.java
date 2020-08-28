package com.eligibility.benefit.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.Benefit;
import com.eligibility.benefit.model.Dependents;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;

@Service
public class SubscriberService {

	@Autowired
	private SubscriberRepository subscriberRepository;

	@Autowired
	private PoliciesService policiesService;

	public String addSubscribers(Subscribers subscribers) {

		List<Benefit> benefitList= new ArrayList();
		List <Dependents> dependentList=new ArrayList();
		Benefit benefit=new Benefit();
		Dependents dependents=new Dependents();
		Policies policy=policiesService.getPolicyDetails((subscribers.getBenefits().get(0).getPolicyId()));
		benefit.setId(policy.getId());
		benefit.setPolicyId(policy.getPolicyId());
		benefit.setPolicyBenefits(policy.getPolicyBenefits());
		benefit.setPolicyName(policy.getPolicyName());
		benefit.setTotalEligibleAmount(policy.getClaimableAmount());
		benefit.setClaimedAmount(subscribers.getBenefits().get(0).getClaimedAmount());
		benefit.setCurrentEligibleAmount(subscribers.getBenefits().get(0).getCurrentEligibleAmount());
		benefitList.add(benefit);		
		subscribers.setBenefits(benefitList);
		dependents.setDependentId(subscribers.getDependents().get(0).getDependentId());
		dependents.setDependentDateOfBirth(subscribers.getDependents().get(0).getDependentDateOfBirth());
		dependents.setDependentName(subscribers.getDependents().get(0).getDependentName());
		dependents.setDependentAddress(subscribers.getDependents().get(0).getDependentAddress());
		dependents.setDependentBenefits(benefitList);
		dependentList.add(dependents);
		subscribers.setDependents(dependentList);
		subscriberRepository.save(subscribers);
		return "Inserted Successfully";
	}
}
