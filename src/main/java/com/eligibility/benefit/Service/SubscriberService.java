package com.eligibility.benefit.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		List<Benefit> allBenefitList= new ArrayList();
		List<String> policyIdList = new ArrayList<>();
		List <Dependents> dependentList=new ArrayList();
		
		Dependents dependents=new Dependents();
		Random random =new Random(System.currentTimeMillis());
		int id = 1000000000 + random.nextInt(2000000000);
		subscribers.setSubscriberId(String.valueOf(id));
		try {
			String policyId = subscribers.getBenefits().get(0).getPolicyId();
			if ((!policyId.equals("0000000001")) && (!policyId.equals("0000000002"))
					&& (!policyId.equals("0000000003")) && (!policyId.equals("0000000004"))) {
				throw new Exception("Invalid Policy Code");
			}
			} catch (Exception e) {
				 e.printStackTrace();
				 return e.toString();
			}
		subscribers.getBenefits().stream().forEach(action -> {
			String policyId = action.getPolicyId();
			policyIdList.add(policyId);
		});
		List<Policies> policyList = policiesService.getPolicyDetailsList(policyIdList);
		policyList.stream().forEach(policy -> {
			Benefit benefit=new Benefit();
			benefit.setId(policy.getId());
			benefit.setPolicyId(policy.getPolicyId());
			benefit.setPolicyBenefits(policy.getPolicyBenefits());
			benefit.setPolicyName(policy.getPolicyName());
			benefit.setTotalEligibleAmount(policy.getClaimableAmount());
			benefit.setClaimedAmount(subscribers.getBenefits().get(0).getClaimedAmount());
			benefit.setCurrentEligibleAmount(subscribers.getBenefits().get(0).getCurrentEligibleAmount());
			benefitList.add(benefit);		
		});
		allBenefitList.addAll(benefitList);
		subscribers.setBenefits(allBenefitList);
		if(subscribers.getDependents().size() > 0) {
			if(subscribers.getDependents().get(0).getDependentName().getFirstName().isEmpty()) {
				dependentList.add(dependents);
			} else {
				String dependentId = subscribers.getSubscriberId().concat("0000000001");
				dependents.setDependentId(dependentId);
				dependents.setDependentDateOfBirth(subscribers.getDependents().get(0).getDependentDateOfBirth());
				dependents.setDependentName(subscribers.getDependents().get(0).getDependentName());
				dependents.setDependentAddress(subscribers.getDependents().get(0).getDependentAddress());
				dependents.setDependentBenefits(benefitList);
				dependentList.add(dependents);
				subscribers.setDependents(dependentList);
			}
		} 
		subscriberRepository.save(subscribers);
		return "Inserted Successfully";
		
	}
	
}
