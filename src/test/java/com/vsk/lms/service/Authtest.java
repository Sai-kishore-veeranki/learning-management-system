package com.vsk.lms.service;

import com.vsk.lms.notifications.NotificationService;
import com.vsk.lms.user.controller.AuthController;
import com.vsk.lms.user.entity.User;
import com.vsk.lms.user.entity.enums.Role;
import com.vsk.lms.user.reposirtory.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testSignupSuccess() throws Exception {
        // Arrange
        User inputUser = User.builder()
                .username("saikishore")
                .email("veerankisaikishore@gmail.com")
                .password("rawpass")
                .role(Role.STUDENT)
                .build();

        when(userRepository.findByUsername("saikishore")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("rawpass")).thenReturn("encodedpass");
        when(userRepository.save(any(User.class))).thenReturn(inputUser);

        String json = """
            {
              "username": "saikishore",
              "email": "veerankisaikishore@gmail.com",
              "password": "rawpass",
              "role": "STUDENT"
            }
        """;

        // Act & Assert
        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));

        verify(notificationService).sendEmail(
                eq("veerankisaikishore@gmail.com"),
                eq("Welcome to LMS 🎉"),
                contains("Hello saikishore")
        );
    }

    @Test
    void testSignupUsernameTaken() throws Exception {
        when(userRepository.findByUsername("sai123"))
                .thenReturn(Optional.of(new User()));

        String json = """
            {
              "username": "sai123",
              "email": "veerankisaikishore@example.com",
              "password": "rawpass",
              "role": "STUDENT"
            }
        """;

        mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already taken!"));
    }
}

