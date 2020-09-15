package com.eligibility.benefit.Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.Users;

public class UserServiceTest {
	
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private Users users;

	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		userService= Mockito.mock(UserService.class);
	}
	
	@After
	public void tearDown() throws Exception {
		userService = null;
	}

	@Test
	public void testGetByUsernames() {
		Mockito.when(userService.getByUsername("")).thenReturn(users);
	
}
}
