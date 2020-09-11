package com.eligibility.benefit.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.Users;
import com.eligibility.benefit.util.LoggerUtil;

@Service
public class UserService {
	
	protected Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepository;
	
	public Users getByUsername(String name) {
		
		LoggerUtil.infoLog(logger,"get the user name "+name);
		Users users=userRepository.findByName(name);
		LoggerUtil.infoLog(logger,"Getting user information");
		return users;
		
	}

}
