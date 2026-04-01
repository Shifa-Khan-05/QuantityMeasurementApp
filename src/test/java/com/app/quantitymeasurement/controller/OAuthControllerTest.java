package com.app.quantitymeasurement.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;

import com.app.quantitymeasurement.model.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.JwtUtil;

@WebMvcTest(OAuthController.class)
public class OAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Crucial: Must be MockBean, not Autowired
    private UserRepository userRepository;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean // Required because Spring Security looks for this bean
    private ClientRegistrationRepository clientRegistrationRepository;

    @Test
    void testOAuthSuccess_ExistingUser() throws Exception {
        // 1. Setup Mock Data
        User existingUser = new User();
        existingUser.setEmail("abhay@gmail.com");
        existingUser.setName("Abhay");

        // 2. Mock behavior
        when(userRepository.findByEmail("abhay@gmail.com")).thenReturn(Optional.of(existingUser));
        when(jwtUtil.generateToken(any(User.class))).thenReturn("mock-jwt-token");

        // 3. Perform Request with OAuth2 Login mock
        mockMvc.perform(get("/oauth/success")
                .with(oauth2Login() // Simulates the @AuthenticationPrincipal
                    .attributes(attrs -> {
                        attrs.put("email", "abhay@gmail.com");
                        attrs.put("name", "Abhay");
                    })))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }
}