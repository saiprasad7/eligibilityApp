package com.eligibility.benefit.Service;

import com.eligibility.benefit.Repo.SubscriberRepository;
import com.eligibility.benefit.Repo.UserRepository;
import com.eligibility.benefit.model.Benefit;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;
import com.eligibility.benefit.model.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SubscriberServiceTest {
    @InjectMocks
    private SubscriberService subscriberService;

    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PoliciesService policiesService;

    @Test
    void whenSubscriberAndBenefitExistShouldReturnAllSubscribersBenefits() {
        Mockito.when(policiesService.getPolicyDetailsList(Arrays.asList("123", "234"))).thenReturn(Arrays.asList(getPolicy("123"), getPolicy("234")));

        List<Benefit> benefits = subscriberService.getPolicyList(Arrays.asList("123", "234"), new Subscribers());
        Assertions.assertThat(benefits.size()).isEqualTo(2);
        Assertions.assertThat(benefits.get(0)).isEqualTo(getBenefit("123"));
        Assertions.assertThat(benefits.get(1)).isEqualTo(getBenefit("234"));
    }

    @Test
    void whenDependentRelationAndSubscriberInfoProvidedShouldGenerateDependentId() {
        // father
        String dependentId = subscriberService.generateDependentId("father", getSubscriber());
        Assertions.assertThat(dependentId).isEqualTo("123401");
        // mother
        dependentId = subscriberService.generateDependentId("mother", getSubscriber());
        Assertions.assertThat(dependentId).isEqualTo("123402");
        // daughter
        dependentId = subscriberService.generateDependentId("daughter", getSubscriber());
        Assertions.assertThat(dependentId).isEqualTo("123404");
        // son
        dependentId = subscriberService.generateDependentId("son", getSubscriber());
        Assertions.assertThat(dependentId).isEqualTo("123405");
        // spouse
        dependentId = subscriberService.generateDependentId("spouse", getSubscriber());
        Assertions.assertThat(dependentId).isEqualTo("123403");
    }

    @Test
    void whenBenefitsExistShouldReturnMatchingBenefits() {
        Mockito.when(policiesService.getPolicyDetailsList(Collections.singletonList("1234"))).thenReturn(Collections.singletonList(getPolicy("1234")));
        List<Benefit> benefits =
                subscriberService.checkDependentBenefits(
                        Collections.singletonList(getBenefit("1234")),
                        new Subscribers());
        Assertions.assertThat(benefits.size()).isEqualTo(1);
        Assertions.assertThat(benefits.get(0)).isEqualTo(getBenefit("1234"));
    }

    @Test
    void whenAddSubscriberIsCalledAndInfoIsCorrectShouldReturnId() {
        Mockito.when(userRepository.findByToken("123")).thenReturn(getUser("123"));
        Mockito.when(subscriberRepository.save(Mockito.any())).thenReturn(getSubscriber());

        String createdId = subscriberService.addSubscribers(getSubscriber(), "123");
        Assertions.assertThat(createdId).isNotNull();
    }

    private Users getUser(String id) {
        Users users = new Users();
        users.setToken(id);
        users.setName("name");
        users.setPassword("pass");
        users.setEmail("mail@mail.com");
        users.setId(id);
        return users;
    }

    private Subscribers getSubscriber() {
        Subscribers subscribers = new Subscribers();
        subscribers.setSubscriberId("1234");
        subscribers.setBenefits(Collections.singletonList(getBenefit("123")));
        subscribers.setDependents(new ArrayList<>());
        return subscribers;
    }

    private Benefit getBenefit(String id) {
        Benefit benefit = new Benefit();
        benefit.setPolicyName("name " + id);
        benefit.setCurrentEligibleAmount(100L);
        benefit.setClaimedAmount(0);
        benefit.setPolicyId(id);
        benefit.setId(id);
        benefit.setPolicyBenefits("benefits " + id);
        return benefit;
    }

    private Policies getPolicy(String id) {
        Policies policies = new Policies();
        policies.setPolicyId(id);
        policies.setId(id);
        policies.setClaimableAmount(100L);
        policies.setPolicyName("");
        policies.setPolicyBenefits("");
        return policies;
    }
}
