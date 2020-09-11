package com.eligibility.benefit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.eligibility.benefit.Service.UserService;

import com.eligibility.benefit.util.LoggerUtil;
import com.eligibility.benefit.util.ResponseHandlingUtil;

@Controller
public class UserController {
	
	protected Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@GetMapping(path="/getUser",produces = "application/json")
	public ResponseEntity<Object> getUserDetails(@RequestParam String name) {
		LoggerUtil.infoLog(logger,"calling getByUsername API");
		
		return ResponseHandlingUtil.prepareResponse(userService.getByUsername(name));
	}
	
}
