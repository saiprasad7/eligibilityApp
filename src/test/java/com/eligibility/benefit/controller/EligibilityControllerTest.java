package com.eligibility.benefit.controller;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.eligibility.benefit.Service.EligibilityService;
import com.eligibility.benefit.model.EligibilityCheck;

public class EligibilityControllerTest {
	
	private EligibilityController eligibilityController;
	
	@Mock
	private EligibilityService eligibilityService;
	
	@Mock
	private ResponseEntity<Object> eligibilityCheck;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		eligibilityController = Mockito.mock(EligibilityController.class);
	}

	@After
	public void tearDown() throws Exception {
		eligibilityController = null;
	}
	HttpHeaders headers = new HttpHeaders();
	@Test
	public void testGetBenefit() {
		Mockito.when(eligibilityController.getBenefit(headers,"","", "")).thenReturn(eligibilityCheck);
	}

}
