package com.eligibility.benefit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eligibility.benefit.Service.PoliciesService;
import com.eligibility.benefit.Service.SubscriberService;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;
import com.eligibility.benefit.util.LoggerUtil;
import com.eligibility.benefit.util.ResponseHandlingUtil;

@RestController
public class EnrollmentController {
	
	protected Logger logger = LoggerFactory.getLogger(EnrollmentController.class);
	
	@Autowired
	private SubscriberService subscriberService;
	
	@Autowired
	private PoliciesService policiesService;

	@PostMapping(path="/enrollment",consumes = "application/json")
	public String addSubscriberslist(@RequestHeader HttpHeaders headers,@RequestBody Subscribers subscribers) {
		LoggerUtil.infoLog(logger, "calling enrollment API");
		List<String> token=headers.get("authorization");
		return subscriberService.addSubscribers(subscribers,token.get(0));
	}
	
	@GetMapping(path="/getPolicyDetails",produces = "application/json")
	 public ResponseEntity<Object> getPolicyDetails(@RequestParam String policyId) {
		LoggerUtil.infoLog(logger,"calling getPolicyDetails API");
		return ResponseHandlingUtil.prepareResponse(policiesService.getPolicyDetails(policyId));
    }
	
  
	@GetMapping(path="/getAllPolicies",produces = "application/json")
	public ResponseEntity<Object> getAllPolicies() {
		LoggerUtil.infoLog(logger,"calling getAllPolicies API");
		return ResponseHandlingUtil.prepareResponse(policiesService.getAllPolicies());
	}
	
	@GetMapping(path="/getPolicyDetailsList",produces = "application/json")
	 public ResponseEntity<Object> getPolicyDetailsList(@RequestParam List<String> policyIdList) {
		LoggerUtil.infoLog(logger,"calling getPolicyDetailsList API");
		return ResponseHandlingUtil.prepareResponse(policiesService.getPolicyDetailsList(policyIdList));
   }
}
