package com.eligibility.benefit.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.EligibilityCheck;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class EligibilityServiceTest {

    private EligibilityService eligibilityService;

    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private EligibilityCheck eligibilityCheck;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        eligibilityService = Mockito.mock(EligibilityService.class);
    }

    @AfterEach
    public void tearDown() throws Exception {
        eligibilityService = null;
    }

    @Test
    public void testGetEligibility() {
        Mockito.when(eligibilityService.getEligibility("", "", "", "")).thenReturn(eligibilityCheck);
    }

}
