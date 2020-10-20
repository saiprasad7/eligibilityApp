package com.eligibility.benefit.controller;

import com.eligibility.benefit.Service.PoliciesService;
import com.eligibility.benefit.Service.SubscriberService;
import com.eligibility.benefit.Service.UserService;
import com.eligibility.benefit.model.Address;
import com.eligibility.benefit.model.Name;
import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebMvcTest(controllers = EnrollmentController.class)
public class EnrollmentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriberService subscriberService;

    @MockBean
    private PoliciesService policiesService;

    @MockBean
    private UserService userService;

    @Test
    void whenPoliciesRequestedShouldReturnAllPolicies() throws Exception {
        Mockito.when(policiesService.getAllPolicies()).thenReturn(policies());

        mockMvc.perform(MockMvcRequestBuilders.get("/getAllPolicies")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":\"123\",\"policyId\":null," +
                                                                        "\"policyName\":\"name\"," +
                                                                        "\"policyBenefits\":null," +
                                                                        "\"claimableAmount\":100}]"));
    }

    @Test
    void whenPolicyMatchesShouldReturnDetails() throws Exception {
        Mockito.when(policiesService.getPolicyDetails("123")).thenReturn(policy());

        mockMvc.perform(MockMvcRequestBuilders.get("/getPolicyDetails")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("policyId", "123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":\"123\",\"policyId\":null," +
                                                                        "\"policyName\":\"name\"," +
                                                                        "\"policyBenefits\":null," +
                                                                        "\"claimableAmount\":100}"));
    }

    @Test
    void whenPoliciesProvidedShouldReturnAllDetails() throws Exception {
        String policyIdString = String.join(",", policyIds());
        Mockito.when(policiesService.getPolicyDetailsList(Mockito.anyList())).thenReturn(policies());

        mockMvc.perform(MockMvcRequestBuilders.get("/getPolicyDetailsList")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("policyIdList", policyIdString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":\"123\",\"policyId\":null," +
                                                                        "\"policyName\":\"name\"," +
                                                                        "\"policyBenefits\":null," +
                                                                        "\"claimableAmount\":100}]"));
    }

    @Test
    void whenSubscriberInfoSentShouldEnroll() throws Exception {
        Mockito.when(subscriberService.addSubscribers(Mockito.any(), Mockito.eq("123"))).thenReturn("1234");

        mockMvc.perform(MockMvcRequestBuilders.post("/enrollment")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(subscriber())
                                .header("authorization", "123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1234"));
    }

    private String subscriber() throws JsonProcessingException {
        Subscribers subscribers = new Subscribers();
        Name name = getName();
        subscribers.setName(name);

        Address address = getAddress();
        subscribers.setAddress(address);

        subscribers.setBenefits(new ArrayList<>());
        subscribers.setDependents(new ArrayList<>());
        subscribers.setId("");
        subscribers.setSubscriberId("");

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(subscribers);
    }

    private Name getName() {
        Name name = new Name();
        name.setFirstName("first");
        name.setLastName("last");
        return name;
    }

    private Address getAddress() {
        Address address = new Address();
        address.setCity("city");
        address.setCountry("country");
        address.setState("state");
        address.setStreet("street");
        return address;
    }

    private List<String> policyIds() {
        return Collections.singletonList("123");
    }

    private List<Policies> policies() {
        List<Policies> policies = new ArrayList<>();
        Policies policy = policy();
        policies.add(policy);
        return policies;
    }

    private Policies policy() {
        Policies policy = new Policies();
        policy.setClaimableAmount(100L);
        policy.setId("123");
        policy.setPolicyName("name");
        return policy;
    }
}
