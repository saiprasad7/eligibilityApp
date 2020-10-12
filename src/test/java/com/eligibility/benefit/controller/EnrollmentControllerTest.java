package com.eligibility.benefit.controller;

import com.eligibility.benefit.Service.PoliciesService;
import com.eligibility.benefit.Service.SubscriberService;
import com.eligibility.benefit.model.Subscribers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class EnrollmentControllerTest {

    HttpHeaders headers = new HttpHeaders();
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

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        enrollmentController = Mockito.mock(EnrollmentController.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        enrollmentController = null;
    }

    @Test
    public void testAddSubscriberslist() {
        Mockito.when(enrollmentController.addSubscriberslist(headers, subscribers)).thenReturn("");
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
