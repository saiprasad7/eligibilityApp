package com.eligibility.benefit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eligibility.benefit.Service.EligibilityService;
import com.eligibility.benefit.util.ResponseHandlingUtil;

@RestController
public class EligibilityController {

	@Autowired
	private EligibilityService eligibilityService;

	@GetMapping(path = "/getBenefits", produces = "application/json")
	public ResponseEntity<Object> getBenefit(@RequestParam String subscriberId, @RequestParam String dependentId,
			@RequestParam String policyId) {
		return ResponseHandlingUtil
				.prepareResponse(eligibilityService.getEligibility(subscriberId, dependentId, policyId));

	}

}
