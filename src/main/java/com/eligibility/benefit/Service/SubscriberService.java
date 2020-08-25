package com.eligibility.benefit.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.PolicyRepository;
import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.Benefit;
import com.eligibility.benefit.model.Dependents;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;

@Service
public class SubscriberService {
	
	@Autowired
	BenefitService benefitService;

	@Autowired
	SubscriberRepository subscriberRepository;


public boolean addSubscribers(Subscribers subscribers) {
		
		Subscribers subscriber=new Subscribers();
	   List<Benefit> benefitList= new ArrayList();
	   List <Dependents> dependentList=new ArrayList();
		Benefit benefit=new Benefit();
		Dependents dependents=new Dependents();
		// TODO Auto-generated method stub
		boolean s=false;
		System.out.println("get valuealue"+subscribers.getEmail()+subscribers.getPassword()+subscribers.getDateOfBirth()+subscribers.getAddress().getCity());
		//System.out.println(new ObjectId(subscribers.getBenefits().get(0).getId()).toString());
		subscriber.setId(subscribers.getId());
		subscriber.setSubscriberId(subscribers.getSubscriberId());
		subscriber.setName(subscribers.getName());
		subscriber.setAddress(subscribers.getAddress());
		subscriber.setEmail(subscribers.getEmail());
		subscriber.setPassword(subscribers.getPassword());
		subscriber.setDateOfBirth(subscribers.getDateOfBirth());
		Policies policy=benefitService.getBenefitService((subscribers.getBenefits().get(0).getPolicyId()));		
		benefit.setId(policy.getId());
		benefit.setPolicyId(policy.getPolicyId());
		benefit.setPolicyBenefits(policy.getPolicyBenefits());
		benefit.setPolicyName(policy.getPolicyName());
		benefit.setTotalEligibleAmount(policy.getClaimableAmount());
		benefit.setClaimedAmount(subscribers.getBenefits().get(0).getClaimedAmount());
		benefit.setCurrentEligibleAmount(subscribers.getBenefits().get(0).getCurrentEligibleAmount());
		benefitList.add(benefit);		
	 subscriber.setBenefits(benefitList);
		dependents.setDependentId(subscribers.getDependents().get(0).getDependentId());
		dependents.setDependentDateOfBirth(subscribers.getDependents().get(0).getDependentDateOfBirth());
		dependents.setDependentName(subscribers.getDependents().get(0).getDependentName());
		dependents.setDependentAddress(subscribers.getDependents().get(0).getDependentAddress());
		dependents.setDependentBenefits(benefitList);
		dependentList.add(dependents);
		subscriber.setDependents(dependentList);
		
		if(s==false) {
	 subscriberRepository.insert(subscriber);
		s=true;
		System.out.println("inside if success");
		}
		else {
			System.out.println("inside else");
			s=false;
		}
		return s;
	}
}
