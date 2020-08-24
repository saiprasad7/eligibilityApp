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
		Optional<Policies> policy=policyRepository.findByPolicyId((subscribers.getBenefits().get(0).getPolicyId()));
		//benefit.setId(new ObjectId(subscribers.getBenefits().get(0).getId()).toString());
		benefit.setId(policy.get().getId());
		benefit.setPolicyId(policy.get().getPolicyId());
		benefit.setPolicyBenefits(policy.get().getPolicyBenefits());
		benefit.setPolicyName(policy.get().getPolicyName());
		benefit.setTotalEligibleAmount(policy.get().getClaimableAmount());
		benefit.setClaimedAmount(subscribers.getBenefits().get(0).getClaimedAmount());
		benefit.setCurrentEligibleAmount(subscribers.getBenefits().get(0).getCurrentEligibleAmount());
		benefitList.add(benefit);		
	 subscriber.setBenefits(benefitList);
		dependents.setDependentId(subscribers.getDependents().get(0).getDependentId());
		dependents.setDependentDateOfBirth(subscribers.getDependents().get(0).getDependentDateOfBirth());
		dependents.setDependentName(subscribers.getDependents().get(0).getDependentName());
		dependents.setDependentAddress(subscribers.getDependents().get(0).getDependentAddress());
		dependents.setDependentbenefit(benefitList);
		dependentList.add(dependents);
		subscriber.setDependents(dependentList);
		//dependents.setDependentbenefit((List<Benefit>) benefit);
	//	subscriber.setDependents((List<Dependents>) dependents);
		
		//subscriber.setDependents()
		
		
	//subscriber.setBenefits(subscribers.getBenefits().get(0));
	
		
		if(s==false) {
		//subscriberRepo.save(subscriber);
		subscriberRepo.insert(subscriber);
	//	subscriberRepo.saveAll(s);
		s=true;
		System.out.println("inside if success");
		}
		else {
			System.out.println("inside else");
			s=false;
		}
		//return null;
		return s;
	}
	
	
}
