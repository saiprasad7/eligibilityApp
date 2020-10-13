package com.eligibility.benefit.controller;

import com.eligibility.benefit.Service.EligibilityService;
import com.eligibility.benefit.model.EligibilityCheck;
import org.junit.jupiter.api.Disabled;
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

@WebMvcTest(controllers = EligibilityController.class)
@Disabled("Disabled for Integration Tests FIXME: Failing to initialize context")
public class EligibilityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EligibilityService eligibilityService;

    @Test
    void whenAllDetailsPresentShouldReturnEligibleObject() throws Exception {
        Mockito.when(eligibilityService.getEligibility(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                                                       Mockito.anyString())).thenReturn(eligibilityCheck());

        mockMvc.perform(MockMvcRequestBuilders.get("/getBenefits")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("subscriberId", "123")
                                .param("dependentId", "123")
                                .param("policyId", "123")
                                .header("authorization", "Bearer token"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{eligible: true, planCode: 123}"));
    }

    private EligibilityCheck eligibilityCheck() {
        EligibilityCheck check = new EligibilityCheck();
        check.setEligible(true);
        check.setPlanCode("123");
        return check;
    }
}
