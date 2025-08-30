package com.vsk.lms.user.controller;



import com.vsk.lms.notifications.NotificationService;
import com.vsk.lms.user.dto.AuthRequest;
import com.vsk.lms.user.dto.AuthResponse;
import com.vsk.lms.user.entity.User;
import com.vsk.lms.user.reposirtory.UserRepository;
import com.vsk.lms.user.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final NotificationService notificationService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil, NotificationService notificationService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.notificationService = notificationService;
    }

    /** User Login */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Directly use user role instead of extracting again
        String token = jwtUtil.generateToken(user.getUsername(), String.valueOf(user.getRole()));
        return ResponseEntity.ok(new AuthResponse(token, String.valueOf(user.getRole())));
    }


    /** User Signup */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        notificationService.sendEmail(
                user.getEmail(),
                "Welcome to LMS 🎉",
                "Hello " + user.getUsername() + ",\n\nWelcome to our Learning Management System!"
        );


        return ResponseEntity.ok("User registered successfully");
    }
}

