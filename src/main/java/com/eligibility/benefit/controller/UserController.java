package com.eligibility.benefit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.eligibility.benefit.Service.UserService;
import com.eligibility.benefit.model.TokenDetails;
import com.eligibility.benefit.util.ResponseHandlingUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {
	
	protected Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@GetMapping(path="/getUser",produces = "application/json")
	public ResponseEntity<Object> getUserDetails(@RequestParam String name) {
		log.info("calling getByUsername API");
		
		return ResponseHandlingUtil.prepareResponse(userService.getByUsername(name));
	}
	
	@PostMapping(path="/tokens",consumes = "application/json")
	public ResponseEntity<Object> addTokenDetails(@RequestBody TokenDetails tokenDetails){
		log.info("calling API for token validation");
		
		return ResponseHandlingUtil.prepareResponse(userService.updateUserInfo(tokenDetails));
	}
	
}
