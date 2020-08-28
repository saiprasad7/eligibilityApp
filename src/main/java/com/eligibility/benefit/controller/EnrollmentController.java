package com.eligibility.benefit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	protected Logger logger = LoggerFactory.getLogger(EnrollmentController.class);
	
	@Autowired
	SubscriberService subscriberService;
	
	@Autowired
	private PoliciesService policiesService;

	@PostMapping(path="/enrollment",consumes = "application/json")
	public String addSubscriberslist(@RequestBody Subscribers subscribers) {
		logger.info("calling enrollment API");
		return subscriberService.addSubscribers(subscribers);
	}	
	
	@GetMapping(path="/getPolicyDetails",produces = "application/json")
    public Policies getPolicyDetails(@RequestParam String policyId) {
		logger.info("calling getPolicyDetails API");
        return policiesService.getPolicyDetails(policyId);
    }
	
	@GetMapping(path="/getAllPolicies",produces = "application/json")
	public List<Policies> getAllPolicies() {
		logger.info("calling getAllPolicies API");
		return policiesService.getAllPolicies();
	}
}
