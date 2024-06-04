package com.example.ecommerce.controller;

import com.example.ecommerce.entity.UserDetails;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.implement.UserServiceImpl;
import com.example.ecommerce.utils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user-details")
public class UserDetailsController {
    @Autowired
    UserServiceImpl userService;

    @PostMapping(path = "/register")
    public ResponseEntity<String> createUser(@RequestBody UserDetails userDetailsRequest) {
        return userService.createUser(userDetailsRequest);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody UserDetails userDetailsRequest) {
        return userService.login(userDetailsRequest);
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout(@RequestHeader("session-id") String sessionId) throws Exception {
        return userService.logout(sessionId);
    }
}
