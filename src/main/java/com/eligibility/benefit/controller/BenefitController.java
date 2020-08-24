package com.eligibility.benefit.controller;

import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eligibility.benefit.Service.BenefitService;
import com.eligibility.benefit.model.Benefit;
import com.eligibility.benefit.model.Subscribers;

@RestController
public class BenefitController {
	
	@Autowired
	BenefitService benefitService;
	
//	@Autowired
//	Subscribers subscribers;
	
	@GetMapping(path="/hello")
	public String hello() {
		System.out.println("-----get mapping-- hi hello-");
		return "hi";
	}
	
	
	@RequestMapping("/get")
	public List getSubscribersList() {
		System.out.println("-----get mapping---");
		return benefitService.findAll();
	}
	
	@PostMapping(path="/getBenefitService",consumes = "application/json")
	public boolean addSubscriberslist(@RequestBody Subscribers subscribers) {
		System.out.println("-----get mapping---");
		return benefitService.addSubscribers(subscribers);
	}
	
}
