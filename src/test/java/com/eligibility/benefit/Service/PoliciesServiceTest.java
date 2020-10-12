package com.eligibility.benefit.Service;

import com.eligibility.benefit.Repo.PolicyRepository;
import com.eligibility.benefit.model.Policies;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class PoliciesServiceTest {

    private PoliciesService policiesService;

    @Mock
    private PolicyRepository policyRepository;

    @Mock
    private Policies policy;

    @Mock
    private List<Policies> policyList;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        policiesService = Mockito.mock(PoliciesService.class);
    }

    @AfterEach
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
