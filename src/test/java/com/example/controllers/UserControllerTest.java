package com.example.controllers;

import com.example.pojo.User;
import com.example.pojo.UserInfo;
import com.example.configs.TestBeansConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(TestBeansConfig.class)   // <-- THIS WORKS NOW
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("HELLO"));
    }

    @Test
    void testMessageWithQueryParam() throws Exception {
        mockMvc.perform(get("/api/v1/message?text=World"))
                .andExpect(status().isOk())
                .andExpect(content().string("HELLO :World"));
    }

    @Test
    void testPostMessageGenderMale() throws Exception {
        UserInfo info = new UserInfo("male", "a good boy");

        mockMvc.perform(post("/api/v1/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(info)))
                .andExpect(status().isOk())
                .andExpect(content().string("He is a good boy"));
    }

    @Test
    void testPostMessageGenderFemale() throws Exception {
        UserInfo info = new UserInfo("female", "a good girl");

        mockMvc.perform(post("/api/v1/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(info)))
                .andExpect(status().isOk())
                .andExpect(content().string("She is a good girl"));
    }

    @Test
    void testSaveAllUsers() throws Exception {
        List<User> newUsers = List.of(
                new User("Alice", 1),
                new User("Bob", 2)
        );

        mockMvc.perform(post("/api/v1/saveall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUsers)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Alice")))
                .andExpect(jsonPath("$[1].rollNo", is(2)));
    }

    @Test
    void testGetUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(List.class)));
    }

    /*@Test
    void testUpdateUserSuccess() throws Exception {
        User updated = new User("Harish", 99);

        mockMvc.perform(put("/api/v1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Harish")))
                .andExpect(jsonPath("$.rollNo", is(99)));
    }*/

    @Test
    void testUpdateUserNotFound() throws Exception {
        User updated = new User("Unknown", 50);

        mockMvc.perform(put("/api/v1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }
}
