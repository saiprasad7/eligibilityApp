package com.eligibility.benefit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eligibility.benefit.Service.BenefitService;
import com.eligibility.benefit.Service.SubscriberService;
import com.eligibility.benefit.model.Subscribers;

@RestController
public class SubscriberController {
	
	@Autowired
	SubscriberService subscriberService;

	@PostMapping(path="/enrollment",consumes = "application/json")
	public boolean addSubscriberslist(@RequestBody Subscribers subscribers) {
		System.out.println("-----get mapping---");
		return subscriberService.addSubscribers(subscribers);
	}	
}
