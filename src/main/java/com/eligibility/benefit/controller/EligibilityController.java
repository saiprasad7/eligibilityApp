package com.eligibility.benefit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eligibility.benefit.Service.EligibilityService;
import com.eligibility.benefit.model.EligibilityCheck;

@RestController
public class EligibilityController {
	protected Logger logger = LoggerFactory.getLogger(EligibilityController.class);
	public static final int status =100;
	@Autowired
	private EligibilityService eligibilityService;
	
	@GetMapping(path="/getBenefits",produces = "application/json")
	public ResponseEntity<EligibilityCheck> getBenefit(@RequestParam String subscriberId,String uniqueId,String plan ) {
		logger.info("calling getBenefits API");
			EligibilityCheck eligibilityCheck=eligibilityService.getEligibility(subscriberId, uniqueId, plan);
			if(null!=eligibilityCheck.getRelationShip()||true==eligibilityCheck.isEligible()) {
				return   ResponseEntity.ok(eligibilityCheck);
						}
			else {
				logger.info("null value-- not eligible");
				return ResponseEntity.noContent().build();
				
			}	
			
	}
	
}
