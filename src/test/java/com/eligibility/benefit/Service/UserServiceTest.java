package com.eligibility.benefit.Service;

import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @MockBean
    private MongoTemplate mongoTemplate;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private MailServices mailServices;
    @MockBean
    private ObjectMapper mapper;
    @Autowired
    private UserService userService;

    @Test
    public void testGetByUsernames() {
        Mockito.when(userService.getByUsername("")).thenReturn(users());
    }

    private Users users() {
        Users users = new Users();
        users.setToken("token");
        users.setName("name");
        users.setPassword("password");
        users.setEmail("email");
        users.setId("1");
        return users;
    }

    @TestConfiguration
    static class UserServiceTestConfig {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }
}
