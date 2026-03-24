package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.dto.UserInfoDTO;
import com.shubham.farmflow_backend.dto.UserRequestDTO;
import com.shubham.farmflow_backend.entity.Enums;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.UserRepository;
import com.shubham.farmflow_backend.service.AuthService;
import com.shubham.farmflow_backend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserRequestDTO request) {
        // Map UserRequestDTO → User entity for auth
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        String token = this.authService.verify(user);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        User currentUser = userRepository.findByEmail(jwtService.extractUsername(token));
        UserInfoDTO userInfoDTO = new UserInfoDTO(currentUser);  // ← UserDTO here

        return ResponseEntity.ok(Map.of(
                "token", token,
                "user", userInfoDTO   // ← clean user object, no password/authorities
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserRequestDTO request) {
        // Map UserRequestDTO → User entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Enums.Role.ADMIN);
        return ResponseEntity.ok(this.authService.register(user).getBody());
    }
    @PostMapping("/registerUser")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserRequestDTO request) {
        // Map UserRequestDTO → User entity
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Enums.Role.USER);
        return ResponseEntity.ok(this.authService.register(user).getBody());
    }
}