package com.eligibility.benefit.Service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.eligibility.benefit.Repo.PolicyRepository;
import com.eligibility.benefit.model.Policies;

public class PoliciesServiceTest {

	private PoliciesService policiesService;
	
	@Mock
	private PolicyRepository policyRepository;
	
	@Mock
	private Policies policy;
	
	@Mock
	private List<Policies> policyList;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		policiesService = Mockito.mock(PoliciesService.class);
	}

	@After
	public void tearDown() throws Exception {
		policiesService = null;
	}

	@Test
	public void testGetPolicyDetails() {
		Mockito.when(policiesService.getPolicyDetails("")).thenReturn(policy);
	}

	@Test
	public void testGetAllPolicies() {
		Mockito.when(policiesService.getAllPolicies()).thenReturn(policyList);
	}

}
