package com.eligibility.benefit.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class EligibilityServiceTest {

    public static final String SUBSCRIBER_ID = "1234";
    @InjectMocks
    private EligibilityService eligibilityService;
    @Mock
    private SubscriberRepository subscriberRepository;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenSubscriberIsPresentAndEligibleShouldReturnEligibilityInfo() {
        Users users = users();
        Mockito.when(userRepository.findByToken(SUBSCRIBER_ID)).thenReturn(users);
        Subscribers subscribers = subscribers();
        Mockito.when(subscriberRepository.findBySubscriberId(SUBSCRIBER_ID)).thenReturn(subscribers);

        Object check = eligibilityService.getEligibility(SUBSCRIBER_ID, "00", SUBSCRIBER_ID, SUBSCRIBER_ID);

        Assertions.assertThat(check).isInstanceOf(EligibilityCheck.class);
        EligibilityCheck converted = (EligibilityCheck) check;

        Assertions.assertThat(converted.getPlanCode()).isEqualTo("1234");
        Assertions.assertThat(converted.getCurrentEligibleAmount()).isEqualTo(100);
        Assertions.assertThat(converted.getSubscriberId()).isEqualTo(SUBSCRIBER_ID);
    }

    private Subscribers subscribers() {
        Subscribers subscribers = new Subscribers();
        subscribers.setSubscriberId(SUBSCRIBER_ID);
        subscribers.setBenefits(benefits());
        subscribers.setDependents(new ArrayList<>());
        subscribers.setId(SUBSCRIBER_ID);
        Name name = new Name();
        name.setFirstName("first");
        name.setLastName("last");
        subscribers.setName(name);
        return subscribers;
    }

    private Users users() {
        Users users = new Users();
        users.setId("1234");
        users.setEmail("email@email.com");
        users.setPassword("1234");
        users.setName("Name");
        users.setToken("");
        return users;
    }

    private List<Benefit> benefits() {
        List<Benefit> benefits = new ArrayList<>();

        Benefit benefit = getBenefit();

        benefits.add(benefit);
        return benefits;
    }

    private Benefit getBenefit() {
        Benefit benefit = new Benefit();
        benefit.setId(SUBSCRIBER_ID);
        benefit.setPolicyId(SUBSCRIBER_ID);
        benefit.setClaimedAmount(0);
        benefit.setCurrentEligibleAmount(100);
        benefit.setPolicyName("Name");
        return benefit;
    }

}
