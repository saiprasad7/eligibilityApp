package com.eligibility.benefit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eligibility.benefit.Service.EligibilityService;
import com.eligibility.benefit.model.EligibilityCheck;

@RestController
public class EligibilityController {
	
	@Autowired
	private EligibilityService eligibilityService;
	
	@GetMapping(path="/getBenefits",produces = "application/json")
	public EligibilityCheck getBenefit(@RequestParam String subscriberId,String uniqueId,String plan ) {
		return eligibilityService.getEligibility(subscriberId, uniqueId, plan);
	}
	
}
