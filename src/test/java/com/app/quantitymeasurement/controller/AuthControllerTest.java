package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.UserRegistrationDTO;
import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.security.JwtUtil;
import com.app.quantitymeasurement.service.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// IMPORTANT: Import the csrf post-processor
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void testRegisterUser() throws Exception {
        // Mock the service to return a User entity when any DTO is passed
        User userEntity = new User(1L, "Abhay", "abhay@gmail.com", null, "encoded_pass", "ROLE_USER", "LOCAL");
        Mockito.when(authService.register(Mockito.any(UserRegistrationDTO.class)))
                .thenReturn(userEntity);

        String json = """
        {
          "name":"Abhay",
          "email":"abhay@gmail.com",
          "password":"password123"
        }
        """;

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("abhay@gmail.com"));
    }

    @Test
    void testLoginUser() throws Exception {
        User user = new User(null, "Abhay", "abhay@gmail.com", null, "1234", "USER", null);

        Mockito.when(authService.login(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(user);

        Mockito.when(jwtUtil.generateToken(Mockito.any(User.class)))
                .thenReturn("test-token");

        String json = """
        {
          "email":"abhay@gmail.com",
          "password":"1234"
        }
        """;

        mockMvc.perform(post("/auth/login")
                .with(csrf()) // <--- Add this
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test-token"));
    }
}