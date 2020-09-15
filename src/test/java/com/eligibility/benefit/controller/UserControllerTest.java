package com.eligibility.benefit.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.eligibility.benefit.Service.UserService;
import com.eligibility.benefit.model.Users;

public class UserControllerTest {
	
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	@Mock
	private Users users;
	
	@Mock
	private ResponseEntity<Object> user;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		userController = Mockito.mock(UserController.class);
	}

	@After
	public void tearDown() throws Exception {
		userController = null;
	}


	@Test
	public void testGetUserDetails() {
		Mockito.when(userController.getUserDetails("")).thenReturn(user);
	}


}
