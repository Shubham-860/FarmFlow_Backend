package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.dto.UserInfoDTO;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User getUserById(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("User with id " + id + " not found.");
        }
        return user;
    }

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User user)) {
            throw new IllegalStateException("No authenticated user");
        }
        return user;
    }

    public ResponseEntity<UserInfoDTO> getCurrentUserMap() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new IllegalArgumentException("No authenticated user found.");
        }
        User user = userRepository.findByEmail(authentication.getName());
        return ResponseEntity.ok(new UserInfoDTO(user));

    }

    public void addUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
        }
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null) {
            throw new IllegalArgumentException("User with id " + id + " not found.");
        }
        userRepository.delete(user);
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findUserById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("User with id " + user.getId() + " not found.");
        }
        return userRepository.save(user);
    }

}
