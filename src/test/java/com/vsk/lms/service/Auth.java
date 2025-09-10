package com.vsk.lms.service;


import com.vsk.lms.notifications.NotificationService;
import com.vsk.lms.user.dto.UserDTO;
import com.vsk.lms.user.entity.User;
import com.vsk.lms.user.entity.enums.Role;
import com.vsk.lms.user.reposirtory.UserRepository;
import com.vsk.lms.user.service.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl authService;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_Success() {
        User request = new User();
        request.setUsername("testname");
        request.setPassword(passwordEncoder.encode("password"));

        request.setEmail("test@example.com");

        request.setRole(Role.valueOf("STUDENT"));

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User savedUser = userRepository.save(request);;

        assertNotNull(savedUser);
        assertEquals("test@example.com", savedUser.getEmail());
    }




}

