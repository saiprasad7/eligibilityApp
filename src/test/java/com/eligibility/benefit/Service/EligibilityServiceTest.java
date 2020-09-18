package com.eligibility.benefit.Service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.EligibilityCheck;

public class EligibilityServiceTest {
	
	private EligibilityService eligibilityService;
	
	@Mock
	private SubscriberRepository subscriberRepository;
	
	@Mock
	private EligibilityCheck eligibilityCheck;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		eligibilityService = Mockito.mock(EligibilityService.class);
	}

	@After
	public void tearDown() throws Exception {
		eligibilityService = null;
	}

	@Test
	public void testGetEligibility() {
		Mockito.when(eligibilityService.getEligibility("", "", "","")).thenReturn(eligibilityCheck);
	}

}
