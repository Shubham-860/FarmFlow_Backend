package com.shubham.farmflow_backend.controller;

import com.shubham.farmflow_backend.dto.UserInfoDTO;
import com.shubham.farmflow_backend.entity.User;
import com.shubham.farmflow_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

//    @Autowired
//    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserInfoDTO> getCurrentUser() {
        return userService.getCurrentUserMap();
    }

//    @PostMapping("/add")
//    public ResponseEntity<User> addUser(@RequestBody User user) {
//        return ResponseEntity.ok(userService.addUser(user));
//    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok(userService.updateUser(user));
    }
}
