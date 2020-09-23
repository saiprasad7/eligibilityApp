package com.eligibility.benefit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.eligibility.benefit.Service.UserService;
import com.eligibility.benefit.model.TokenDetails;
import com.eligibility.benefit.util.LoggerUtil;
import com.eligibility.benefit.util.ResponseHandlingUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {
	
	protected Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private ObjectMapper mapper;
	
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
	
	@PostMapping(value = "/sendTemporaryPassword")
	public ResponseEntity<JsonNode> sendTemporaryPassword(@RequestBody JsonNode userNode) {
		LoggerUtil.infoLog(logger,"calling sendTemporaryPassword API");

		JsonNode responceNode = mapper.createObjectNode();
		try {
			responceNode = userService.sendTemporaryPassword(userNode.get("email").asText(),
					userNode.get("userName").asText());
			LoggerUtil.infoLog(logger,"end sendTemporaryPassword API");
			return new ResponseEntity<JsonNode>(responceNode, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			((ObjectNode)responceNode).put("status", false);
			((ObjectNode)responceNode).put("data",  "Email Not send. Exception accured");
			e.printStackTrace();
			return new ResponseEntity<JsonNode>(responceNode, HttpStatus.NOT_FOUND);

		}

	}
	
}
