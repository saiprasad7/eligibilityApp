package com.eligibility.benefit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eligibility.benefit.Service.EligibilityService;
import com.eligibility.benefit.util.ResponseHandlingUtil;

@RestController
public class EligibilityController {

	@Autowired
	private EligibilityService eligibilityService;

	@GetMapping(path = "/getBenefits", produces = "application/json")
	public ResponseEntity<Object> getBenefit(@RequestHeader HttpHeaders headers,@RequestParam String subscriberId, @RequestParam String dependentId,
			@RequestParam String policyId) {
		List<String> token=headers.get("authorization");
		System.out.println("he--"+token.toString()+"----token.get(0);-"+token.get(0));
		//System.out.println("he--"+headers.toString()+"------"+headers.get("authorization"));
		return ResponseHandlingUtil.prepareResponse(eligibilityService.getEligibility(subscriberId, dependentId, policyId,token.get(0)));

	}

}

