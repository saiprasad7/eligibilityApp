package com.eligibility.benefit.Service;

import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.TokenDetails;
import com.eligibility.benefit.model.Users;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenUserNameExistsGetByUsernameShouldReturnUser() {
        Mockito.when(userService.getByUsername("")).thenReturn(users());
        Users found = userService.getByUsername("");
        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.getName()).isEqualTo("name");
    }

    @Test
    void whenGetTemporaryPasswordIsCalledShouldReturnFormattedEmail() {
        Mockito.when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(users());

        JsonNode node = userService.getTemporaryPassword("email@email.com", "name");

        Assertions.assertThat(node).isNotNull();
        Assertions.assertThat(node.get("message").toString()).isNotEmpty();
        Assertions.assertThat(node.get("subject").toString()).isNotEmpty();
        Assertions.assertThat(node.get("link").toString()).isNotEmpty();
        Assertions.assertThat(node.get("to").toString()).isEqualTo("\"email@email.com\"");
    }

    @Test
    void whenSendTemporaryPasswordIsCalledWithoutErrorShouldSendForgotPasswordEmail() throws Exception {
        Mockito.doNothing().when(mailServices).sendMail(Mockito.any());

        JsonNode node = userService.sendTemporaryPassword("email", "1234");
        Assertions.assertThat(node).isNotNull();
        Assertions.assertThat(node.get("status").asBoolean()).isTrue();
        Assertions.assertThat(node.get("data").asText()).contains("successfully");
    }

    @Test
    void whenLoadUserByNameIsCalledWithExistingUserShouldReturnCorrectUser() {
        Users users = users();
        Mockito.when(userRepository.findByName("")).thenReturn(users);
        Mockito.when(passwordEncoder.encode(users.getPassword())).thenReturn(users.getPassword());

        UserDetails details = userService.loadUserByUsername("");

        Assertions.assertThat(details).isNotNull();
        Assertions.assertThat(details.getPassword()).isEqualTo(users.getPassword());
        Assertions.assertThat(details.getAuthorities().size()).isEqualTo(1);
    }

    @Test
    void whenUpdateUserInfoIsCalledWithExistingTokenShouldReturnSuccess() {
        Users users = users();
        Mockito.when(userRepository.findByName(users.getName())).thenReturn(users);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(users);

        TokenDetails details = new TokenDetails();
        details.setToken("1234");
        details.setName(users.getName());

        String result = userService.updateUserInfo(details);

        Assertions.assertThat(result).isNotEmpty();
        Assertions.assertThat(result).contains("success");
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
        public ObjectMapper mapper() {
            return new ObjectMapper();
        }

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }
}
