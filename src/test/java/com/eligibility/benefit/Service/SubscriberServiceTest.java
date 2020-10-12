package com.eligibility.benefit.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class SubscriberServiceTest {

    @Autowired
    private SubscriberService subscriberService;

    @MockBean
    private SubscriberRepository subscriberRepository;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PoliciesService policiesService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddSubscribers() {
        Mockito.when(userRepository.findByToken("")).thenReturn(new Users());
        Mockito.when(userRepository.save(Mockito.any())).thenReturn("");
    }

    @TestConfiguration
    static class SubscriberServiceTestConfig {
        @Bean
        public SubscriberService subscriberService() {
            return new SubscriberService();
        }
    }

}
