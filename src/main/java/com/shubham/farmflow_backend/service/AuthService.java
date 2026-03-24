package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


    public ResponseEntity<Map<String, Object>> register(User user) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);

        try {
            userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("status", true, "message", "User registered successfully"));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("status", false, "message", e.getMessage()));
        }
    }

    public String verify(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken((UserDetails) Objects.requireNonNull(authentication.getPrincipal()));
            }
        } catch (Exception ex) {
            return null;
        }

        return null;
    }
}
