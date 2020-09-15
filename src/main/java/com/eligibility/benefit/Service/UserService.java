package com.eligibility.benefit.Service;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.Users;
import com.eligibility.benefit.util.LoggerUtil;


@Service
public class UserService implements UserDetailsService {
	
	protected Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Users getByUsername(String name) {
		
		LoggerUtil.infoLog(logger,"get the user name "+name);
		Users dbUser=userRepository.findByName(name);
		LoggerUtil.infoLog(logger,"Getting user information");
		
	/*	if (dbUser != null) {
			Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			
			//for (String role : dbUser.getRoles()) {
				GrantedAuthority authority = new SimpleGrantedAuthority(dbUser.getRoles());
				grantedAuthorities.add(authority);
			//}
					
			org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
					dbUser.getName(), dbUser.getPassword(), grantedAuthorities);
			return user;
		} else {
			throw new UsernameNotFoundException(String.format("User '%s' not found", name));
		}*/
	
		return dbUser;
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		LoggerUtil.infoLog(logger,"get the user name "+username);
		Users dbUser=userRepository.findByName(username);
		LoggerUtil.infoLog(logger,"Getting user information");
		dbUser.setPassword(passwordEncoder.encode(dbUser.getPassword()));
		System.out.println("befire db user"+dbUser.getName());
		if (dbUser != null) {
			System.out.println("db user"+dbUser.getName());
			Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			
			//for (String role : dbUser.getRoles()) {
				GrantedAuthority authority = new SimpleGrantedAuthority(dbUser.getName());
				grantedAuthorities.add(authority);
			//}
					
			org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
					dbUser.getName(), dbUser.getPassword(), grantedAuthorities);
			return user;
		} else {
			throw new UsernameNotFoundException(String.format("User '%s' not found", username));
		}
	
		//return user;

	}

}
