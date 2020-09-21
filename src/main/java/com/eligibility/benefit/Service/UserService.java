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
import com.eligibility.benefit.model.TokenDetails;
import com.eligibility.benefit.model.Users;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {

	protected Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Users getByUsername(String name) {

		Users dbUser = userRepository.findByName(name);
		log.info("Getting user information");
		return dbUser;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("get the user name " + username);
		Users dbUser = userRepository.findByName(username);
		log.info( "Getting user information");
		dbUser.setPassword(passwordEncoder.encode(dbUser.getPassword()));
		if (null!=dbUser) {
			log.info("db user" + dbUser.getName());
			Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

			GrantedAuthority authority = new SimpleGrantedAuthority(dbUser.getName());
			grantedAuthorities.add(authority);

			org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
					dbUser.getName(), dbUser.getPassword(), grantedAuthorities);
			return user;
		} else {
			throw new UsernameNotFoundException(String.format("User '%s' not found", username));
		}

	}

	public String updateUserInfo(TokenDetails tokenDetails) {
		String msg = "";
		Users dbUser = userRepository.findByName(tokenDetails.getName());
		Users db = new Users();
		log.info("token"+tokenDetails.getToken());
		dbUser.setToken("Bearer " + tokenDetails.getToken());
		db = userRepository.save(dbUser);

		if (db != null) {
			msg = "successfully updated";
		}
		return msg;
	}
}
