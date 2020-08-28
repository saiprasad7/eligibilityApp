package com.eligibility.benefit.controller;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.eligibility.benefit.Service.EligibilityService;
import com.eligibility.benefit.model.EligibilityCheck;

public class EligibilityControllerTest {
	
	private EligibilityController eligibilityController;
	
	@Mock
	private EligibilityService eligibilityService;
	
	@Mock
	private EligibilityCheck eligibilityCheck;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		eligibilityController = Mockito.mock(EligibilityController.class);
	}

	@After
	public void tearDown() throws Exception {
		eligibilityController = null;
	}

	@Test
	public void testGetBenefit() {
		Mockito.when(eligibilityController.getBenefit("", "", "")).thenReturn(eligibilityCheck);
	}

}
