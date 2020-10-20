package com.eligibility.benefit.Service;

import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.TokenDetails;
import com.eligibility.benefit.model.Users;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class UserService implements UserDetailsService {
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

    public JsonNode getTemporaryPassword(String email, String userName) {
        log.info("getTemporaryPassword method is started in service class");
        JsonNode userNode = mapper.createObjectNode();
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(userName).and("email").is(email));
        Users exitUser = mongoTemplate.findOne(query, Users.class);
        String subject = "Temporary Password";
        String msg = "";
        String link = "Please visit this link http://localhost:8080";
        if (exitUser != null) {
            msg =
                    "Hi " + userName + "  \n\n Welcome to PERFICIENT online courses. Your Temporary Password is " + exitUser.getPassword() + " \n\n Please visit this link http://localhost:8080";
        } else {
            msg = "Hi " + userName + "  \n\n Welcome to PERFICIENT online courses. You are not registered user. " +
                    "Please register online course. \n\n Please visit this link http://localhost:8080";
        }
        addMailContent(subject, msg, link, email, userNode);
        log.info("getTemporaryPassword method is End in service class");
        return userNode;

    }

    private void addMailContent(String subject, String msg, String link, String tomail, JsonNode userNode) {
        ((ObjectNode) userNode).put("subject", subject);
        ((ObjectNode) userNode).put("message", msg);
        ((ObjectNode) userNode).put("link", link);
        ((ObjectNode) userNode).put("to", tomail);
    }


    public JsonNode sendTemporaryPassword(String toEmail, String userId) {
        log.info("sendTemporaryPassword is started");
        JsonNode responceNode = mapper.createObjectNode();
        try {
            mailServices.sendMail(getTemporaryPassword(toEmail, userId));
            addReturnStatus(responceNode, true, "Email send successfully");
            log.info("sendTemporaryPassword is end");
            return responceNode;
        }
        catch (NullPointerException e) {
            addReturnStatus(responceNode, false, "Email Not send. Exception accured");
            log.error("Email Not send. Exception accured" + e);
            return responceNode;
        }
        catch (Exception e) {
            addReturnStatus(responceNode, false, "Email Not send. Exception accured");
            log.error("Email Not send. Exception accured" + e);
            return responceNode;
        }

    }

    private void addReturnStatus(JsonNode responceNode, boolean status, String msg) {
        ((ObjectNode) responceNode).put("status", status);
        ((ObjectNode) responceNode).put("data", msg);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("get the user name " + username);
        Users dbUser = userRepository.findByName(username);
        log.info("Getting user information");
        dbUser.setPassword(passwordEncoder.encode(dbUser.getPassword()));
        if (null != dbUser) {
            log.info("db user" + dbUser.getName());
            Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

            GrantedAuthority authority = new SimpleGrantedAuthority(dbUser.getName());
            grantedAuthorities.add(authority);

            org.springframework.security.core.userdetails.User user =
                    new org.springframework.security.core.userdetails.User(
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
        log.info("token" + tokenDetails.getToken());
        dbUser.setToken("Bearer " + tokenDetails.getToken());
        db = userRepository.save(dbUser);

        if (db != null) {
            msg = "successfully updated";
        }
        return msg;
    }
}
