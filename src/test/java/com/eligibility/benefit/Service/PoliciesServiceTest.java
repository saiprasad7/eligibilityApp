package com.eligibility.benefit.Service;

import com.eligibility.benefit.Repo.PolicyRepository;
import com.eligibility.benefit.model.Policies;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PoliciesServiceTest {

    @InjectMocks
    private PoliciesService policiesService;

    @Mock
    private PolicyRepository policyRepository;

    @Test
    void whenPoliciesExistShouldRetrieveAllPolicyIds() {
        Mockito.when(policyRepository.findAll()).thenReturn(policies());

        List<Policies> foundPolicies = policiesService.getAllPolicies();

        Policies policy = policies().get(0);
        Assertions.assertThat(foundPolicies.size()).isEqualTo(1);
        Assertions.assertThat(foundPolicies.get(0)).isEqualTo(policy);
    }

    @Test
    void whenPolicyIdsProvidedShouldRetrieveAllDetails() {
        Mockito.when(policyRepository.findByPolicyId("123")).thenReturn(policies().get(0));
        Policies policy = policies().get(0);

        List<Policies> found = policiesService.getPolicyDetailsList(Collections.singletonList("123"));

        Assertions.assertThat(found.size()).isEqualTo(1);
        Assertions.assertThat(found.get(0)).isEqualTo(policy);
    }

    private List<Policies> policies() {
        List<Policies> policies = new ArrayList<>();
        Policies policy = getPolicies();
        policies.add(policy);
        return policies;
    }

    private Policies getPolicies() {
        Policies policy = new Policies();
        policy.setPolicyName("name");
        policy.setId("123");
        policy.setPolicyId("123");
        policy.setClaimableAmount(100L);
        policy.setPolicyBenefits("benefits");
        return policy;
    }

}
