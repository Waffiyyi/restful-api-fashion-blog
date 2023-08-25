package com.example.week8taask.Controller;

import com.example.week8taask.DTOs.LoginDTO;
import com.example.week8taask.Entities.User;
import com.example.week8taask.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @RestController
    @RequestMapping("/save/users")
    public class UserController {

        private final UserService userService;
        @Autowired
        public UserController(UserService userService){
            this.userService = userService;
        }
        @PostMapping
        public ResponseEntity<User> createCategory(@RequestBody User user) {
            User createdUser = userService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        }



        @PostMapping("/login")
        public ResponseEntity<String> login(@RequestBody LoginDTO loginRequest) {
            LoginDTO user = userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
            if (user != null) {
                return ResponseEntity.ok("Welcome");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        }
    }
