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
		List <Dependents> dependentList=new ArrayList();
		Benefit benefit=new Benefit();
		Dependents dependents=new Dependents();
		Random random = new Random();
		int id = random.nextInt((999999999-100)+1)+10;
		subscribers.setSubscriberId(String.valueOf(id));
		try {
		String email = subscribers.getEmail();
		if (!isValidEmail(email)) {
			throw new Exception("Invalid email id");
		}
		} catch (Exception e) {
			 e.printStackTrace();
			 return e.toString();
		}
		try {
			String password = subscribers.getPassword();
			if (!isValidPassword(password)) {
				throw new Exception("Password must be at least 5 characters and at most 8 characters which contains atleast one number, upper case alphabet, lower case alphabet and special characters.");
			}
			} catch (Exception e) {
				 e.printStackTrace();
				 return e.toString();
			}
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
		if(subscribers.getDependents()==null) {
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
		subscriberRepository.save(subscribers);
		return "Inserted Successfully";
		
	}
	
	  public static boolean isValidEmail(String email) {
	      String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      return email.matches(regex);
	   }
	  
	  public static boolean isValidPassword(String password) {
	      String regex = "^(?=.*[0-9])"
                  + "(?=.*[a-z])(?=.*[A-Z])"
                  + "(?=.*[@#$%^&+=])"
                  + "(?=\\S+$).{5,8}$"; 
	      return password.matches(regex);
	   }
}
