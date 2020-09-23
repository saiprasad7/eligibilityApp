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
import com.eligibility.benefit.util.LoggerUtil;
import com.eligibility.benefit.util.MailServices;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements UserDetailsService {

	protected Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailServices mailServices;
	
	@Autowired
	private ObjectMapper mapper;
	
	public Users getByUsername(String name) {

		Users dbUser = userRepository.findByName(name);
		log.info("Getting user information");
		return dbUser;

	}

	public JsonNode getTemporaryPassword(String tomail, String userName) {
		LoggerUtil.infoLog(logger,"getTemporaryPassword method is started in service class");
		JsonNode userNode = mapper.createObjectNode();
		Query query = new Query();
		try {
			query.addCriteria(Criteria.where("name").is(userName).and("email").is(tomail));
			Users exitUser = mongoTemplate.findOne(query, Users.class);
			if (exitUser != null) {
				((ObjectNode) userNode).put("subject", "Temporary Password");
				((ObjectNode) userNode).put("message",
						"Hi " + userName + "  \n\n Welcome to PERFICIENT online courses. Your Temporary Password is "
								+ exitUser.getPassword() + " \n\n Please visit this link http://localhost:8080");
				((ObjectNode) userNode).put("link", "Please visit this link http://localhost:8080");
				((ObjectNode) userNode).put("to", tomail);
				LoggerUtil.infoLog(logger,"getTemporaryPassword method is End in service class");
				return userNode;
			} else {
				((ObjectNode) userNode).put("subject", "Temporary Password");
				((ObjectNode) userNode).put("message", "Hi " + userName
						+ "  \n\n Welcome to PERFICIENT online courses. You are not registered user. Please register online course. \n\n Please visit this link http://localhost:8080");
				((ObjectNode) userNode).put("link", "Please visit this link http://localhost:8080");
				((ObjectNode) userNode).put("to", tomail);
				LoggerUtil.infoLog(logger,"getTemporaryPassword method is End in service class");
				return userNode;
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
			LoggerUtil.infoLog(logger,"getTemporaryPassword is error => "+e);
		}catch(Exception e) {
			LoggerUtil.infoLog(logger,"getTemporaryPassword is error => "+e);
			e.printStackTrace();
		}
		return userNode;
		
	}

	
	public JsonNode sendTemporaryPassword(String toEmail, String userId) {
		LoggerUtil.infoLog(logger,"sendTemporaryPassword is started");
		JsonNode responceNode = mapper.createObjectNode();
		try {
			mailServices.sendMail(getTemporaryPassword(toEmail, userId));
			((ObjectNode)responceNode).put("status", true);
			((ObjectNode)responceNode).put("data",  "Email send successfully");
			LoggerUtil.infoLog(logger,"sendTemporaryPassword is end");
			return responceNode;
		}catch(NullPointerException e) {
			((ObjectNode)responceNode).put("status", false);
			((ObjectNode)responceNode).put("data",  "Email Not send. Exception accured");
			e.printStackTrace();
			return responceNode;
		}catch(Exception e) {
			((ObjectNode)responceNode).put("status", false);
			((ObjectNode)responceNode).put("data",  "Email Not send. Exception accured");
			e.printStackTrace();
			return responceNode;
		}
		
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
