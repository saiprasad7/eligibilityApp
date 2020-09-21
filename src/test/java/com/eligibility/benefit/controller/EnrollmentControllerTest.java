package com.eligibility.benefit.controller;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.eligibility.benefit.Service.PoliciesService;
import com.eligibility.benefit.Service.SubscriberService;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;

public class EnrollmentControllerTest {
	
	private EnrollmentController enrollmentController;
	
	@Mock
	private SubscriberService subscriberService;
	
	@Mock
	private PoliciesService policiesService;
	
	@Mock
	private Subscribers subscribers;
	
	@Mock
	private ResponseEntity<Object> policies;
	
	@Mock
	private ResponseEntity<Object> policyList;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		enrollmentController = Mockito.mock(EnrollmentController.class);
	}

	@After
	public void tearDown() throws Exception {
		enrollmentController = null;
	}
	HttpHeaders headers = new HttpHeaders();
	@Test
	public void testAddSubscriberslist() {
		Mockito.when(enrollmentController.addSubscriberslist(headers, subscribers)).thenReturn(Mockito.anyString());
	}

	@Test
	public void testGetPolicyDetails() {
		Mockito.when(enrollmentController.getPolicyDetails("")).thenReturn(policies);
	}

	@Test
	public void testGetAllPolicies() {
		Mockito.when(enrollmentController.getAllPolicies()).thenReturn(policyList);
	}

}
