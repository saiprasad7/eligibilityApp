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

	public Policies getBenefitService(String policyId) {
		// TODO Auto-generated method stub
		Policies policy=policyRepository.findByPolicyId(policyId);
		return policy;
	}
	
	
}
