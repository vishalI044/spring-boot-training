package com.example.ecommerce.service;

import com.example.ecommerce.entity.UserDetails;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> createUser(UserDetails userDetailsRequest);
    ResponseEntity<String> login(UserDetails userDetailsRequest);

    ResponseEntity<String> logout(String sessionId) throws Exception;
}
