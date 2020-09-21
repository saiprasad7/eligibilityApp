package com.eligibility.benefit.Service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.model.Subscribers;

public class SubscriberServiceTest {

	private SubscriberService subscriberService;
	
	@Mock
	private SubscriberRepository subscriberRepository;
	
	@Mock
	private PoliciesService policiesService;
	
	@Mock
	private Subscribers subscribers;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		subscriberService = Mockito.mock(SubscriberService.class);
	}

	@After
	public void tearDown() throws Exception {
		subscriberService = null;
	}

	@Test
	public void testAddSubscribers() {
		Mockito.when(subscriberService.addSubscribers(subscribers,"")).thenReturn(Mockito.anyString());
	}

}
