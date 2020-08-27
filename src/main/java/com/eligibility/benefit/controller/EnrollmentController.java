package com.eligibility.benefit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eligibility.benefit.Service.PoliciesService;
import com.eligibility.benefit.Service.SubscriberService;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;

@RestController
public class EnrollmentController {
	
	@Autowired
	SubscriberService subscriberService;
	
	@Autowired
	private PoliciesService policiesService;

	@PostMapping(path="/enrollment",consumes = "application/json")
	public String addSubscriberslist(@RequestBody Subscribers subscribers) {
		return subscriberService.addSubscribers(subscribers);
	}	
	
	@GetMapping(path="/getPolicyDetails",produces = "application/json")
    public Policies getPolicyDetails(@RequestParam String policyId) {
        return policiesService.getPolicyDetails(policyId);
    }
	
	@GetMapping(path="/getAllPolicies",produces = "application/json")
	public List<Policies> getAllPolicies() {
		return policiesService.getAllPolicies();
	}
}
