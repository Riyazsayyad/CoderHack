package com.codehack.codehack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.codehack.codehack.repository.*;
import com.codehack.codehack.entity.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() throws Exception {
        String userJson = "{\"username\": \"TestUser\", \"score\": 50}";

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("TestUser")))
                .andExpect(jsonPath("$.score", is(50)))
                .andExpect(jsonPath("$.badges", hasSize(1))); 
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = userRepository.save(new User("1", "TestUser", 50, null));

        mockMvc.perform(get("/users/" + user.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("TestUser")))
                .andExpect(jsonPath("$.score", is(50)));
    }

    @Test
    public void testUpdateUserScore() throws Exception {
        User user = userRepository.save(new User("1", "TestUser", 50, null));
        String updatedUserJson = "{\"score\": 60}";

        mockMvc.perform(put("/users/" + user.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.score", is(60)));
    }

    @Test
    public void testDeleteUser() throws Exception {
        User user = userRepository.save(new User("1", "TestUser", 50, null));

        mockMvc.perform(delete("/users/" + user.getUserId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/users/" + user.getUserId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testInvalidUserRegistration() throws Exception {
        String invalidUserJson = "{\"username\": \"\", \"score\": -10}";

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidUserJson))
                .andExpect(status().isBadRequest());
    }

}
