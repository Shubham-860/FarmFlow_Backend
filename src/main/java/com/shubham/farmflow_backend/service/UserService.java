package com.shubham.farmflow_backend.service;

import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public User addUser(User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists.");
        }
        return userRepository.save(user);

    }

    public String deleteUser(Long id) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            String name = user.getName();
            userRepository.delete(user);
            return "User " + name + " deleted successfully";
        }
        return "User not found";
    }

    public User updateUser(User user) {
        User existingUser = userRepository.findUserById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("User with id " + user.getId() + " not found.");
        }
        return userRepository.save(user);
    }

}
