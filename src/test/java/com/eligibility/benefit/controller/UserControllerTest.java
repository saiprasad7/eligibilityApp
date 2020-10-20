package com.eligibility.benefit.controller;

import com.eligibility.benefit.Service.UserService;
import com.eligibility.benefit.model.Users;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {
    public static final String SUCCESSFULLY_UPDATED = "successfully updated";
    public static final String USER = "user";
    public static final String TOKEN = "token";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void whenUserExistsAndRequestsTokenShouldReturnToken() throws Exception {
        Mockito.when(userService.updateUserInfo(Mockito.any())).thenReturn(SUCCESSFULLY_UPDATED);
        String tokenDetailsString = getTokenDetailsString();

        mockMvc.perform(MockMvcRequestBuilders.post("/tokens")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(tokenDetailsString)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(SUCCESSFULLY_UPDATED))
                .andReturn();
    }

    @Test
    void whenIncorrectRequestSentShouldReturnBadRequest() throws Exception {
        Mockito.when(userService.updateUserInfo(Mockito.any())).thenReturn("");
        String tokenDetailsString = getIncorrectTokenDetails();

        mockMvc.perform(MockMvcRequestBuilders.post("/tokens")
                                .accept(MediaType.APPLICATION_JSON)
                                .content(tokenDetailsString)
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andReturn();
    }

    @Test
    void whenUserExistsShouldReturnUser() throws Exception {
        Mockito.when(userService.getByUsername(USER)).thenReturn(user());

        mockMvc.perform(MockMvcRequestBuilders.get("/getUser")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("name", USER))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{name: user, token: token, password: null, id: null," +
                                                                        " email: null}"))
                .andReturn();
    }

    @Test
    void whenSendTemporaryPasswordIsCalledWithoutErrorShouldSendPassword() throws Exception {
        Mockito.when(userService.sendTemporaryPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(createObjectNode());

        mockMvc.perform(MockMvcRequestBuilders.post("/sendTemporaryPassword")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"email\": \"email@email.com\", \"userName\": \"name\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{email: email@email.com, userName: name}"));
    }

    private JsonNode createObjectNode() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        node.put("email", "email@email.com");
        node.put("userName", "name");
        return node;
    }

    private Users user() {
        Users users = new Users();
        users.setName(USER);
        users.setToken(TOKEN);
        return users;
    }

    private String getTokenDetailsString() {
        return String.format("{\"token\": \"%s\", \"name\": \"%s\"}", TOKEN, USER);
    }

    private String getIncorrectTokenDetails() {
        return String.format("{token: \"%s\"}", TOKEN);
    }
}
