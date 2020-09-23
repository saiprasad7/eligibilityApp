package com.eligibility.benefit.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.Benefit;
import com.eligibility.benefit.model.Dependents;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;
import com.eligibility.benefit.model.Users;
import com.eligibility.benefit.util.Constants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubscriberService {

	@Autowired
	private SubscriberRepository subscriberRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PoliciesService policiesService;

	public String addSubscribers(Subscribers subscribers,String token) {
		List<Benefit> benefitList= new ArrayList();
		List<String> policyIdList = new ArrayList<>();
		List <Dependents> dependentList=new ArrayList();
		Users dbUser=userRepository.findByToken(token);
		if(dbUser.getToken()!=null) {	
		if(dbUser.getToken().equals(token)) {
			log.info("Getting  info"+token);	
		Random random =new Random(System.currentTimeMillis());
		int id = Constants.INITIAL_ID + random.nextInt(Constants.END_ID) & Integer.MAX_VALUE;
		subscribers.setSubscriberId(String.valueOf(id));
		try {
			if(subscribers.getBenefits().isEmpty()) {
				throw new Exception(Constants.INVALID_POLICY_CODE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
		//To add Benefits List
		subscribers.getBenefits().stream().forEach(action -> {
			String policyId = action.getPolicyId();
			policyIdList.add(policyId);
		});
		benefitList = getPolicyList(policyIdList,subscribers);
		subscribers.setBenefits(benefitList);
		//To add Dependents List
		if(!subscribers.getDependents().isEmpty()) {
			subscribers.getDependents().stream().forEach(action -> {
				String dependentId = null;
				List<Benefit> allBenefitList= new ArrayList();
				Dependents dependents=new Dependents();
				dependentId = generateDependentId(action.getDependentRelation(),subscribers);
				dependents.setDependentId(dependentId);
				dependents.setDependentDateOfBirth(action.getDependentDateOfBirth());
				dependents.setDependentName(action.getDependentName());
				dependents.setDependentAddress(action.getDependentAddress());
				dependents.setDependentRelation(action.getDependentRelation());
				List<Benefit> dependentBenefitsList = action.getDependentBenefits();
				allBenefitList = checkDependentBenefits(dependentBenefitsList,subscribers);
				dependents.setDependentBenefits(allBenefitList);
				dependentList.add(dependents);
			});
			subscribers.setDependents(dependentList);
		} 
		subscriberRepository.save(subscribers);
	}
		}
		else {
			subscribers.setSubscriberId(null);
		
		}
		return subscribers.getSubscriberId();
	}

	public List<Benefit> getPolicyList(List<String> policyIdList,Subscribers subscribers) {
		List<Benefit> benList= new ArrayList();
		List<Policies> policyList = policiesService.getPolicyDetailsList(policyIdList);
		policyList.stream().forEach(policy -> {
			Benefit benefit=new Benefit();
			benefit.setId(policy.getId());
			benefit.setPolicyId(policy.getPolicyId());
			benefit.setPolicyBenefits(policy.getPolicyBenefits());
			benefit.setPolicyName(policy.getPolicyName());
			benefit.setTotalEligibleAmount(policy.getClaimableAmount());
			benefit.setClaimedAmount(Constants.CLAIMED_AMOUNT);
			benefit.setCurrentEligibleAmount(policy.getClaimableAmount());
			benList.add(benefit);		
		});
		return benList;
	}

	public String generateDependentId(String dependentRelation,Subscribers subscribers) {
		String dependentId = null;
		if(dependentRelation.equalsIgnoreCase(Constants.FATHER)) {
			dependentId = subscribers.getSubscriberId().concat(Constants.FATHER_ID);
		} else if(dependentRelation.equalsIgnoreCase(Constants.MOTHER)) {
			dependentId = subscribers.getSubscriberId().concat(Constants.MOTHER_ID);
		} else if(dependentRelation.equalsIgnoreCase(Constants.SPOUSE)) {
			dependentId = subscribers.getSubscriberId().concat(Constants.SPOUSE_ID);
		} else if(dependentRelation.equalsIgnoreCase(Constants.DAUGHTER)) {
			dependentId = subscribers.getSubscriberId().concat(Constants.DAUGHTER_ID);
		} else if(dependentRelation.equalsIgnoreCase(Constants.SON)) {
			dependentId = subscribers.getSubscriberId().concat(Constants.SON_ID);
		}
		return dependentId;
	}

	public List<Benefit> checkDependentBenefits(List<Benefit> dependentBenefitsList,Subscribers subscribers) {
		List<String> benPolicyIdList = new ArrayList<>();
		List<Benefit> allBenList= new ArrayList();
		dependentBenefitsList.stream().forEach(action -> {
			String policyId = action.getPolicyId();
			benPolicyIdList.add(policyId);
		});
		allBenList = getPolicyList(benPolicyIdList,subscribers);
		return allBenList;
	}
}
