package com.items.user.controller;

import java.nio.charset.StandardCharsets;
import java.util.*;

import io.jsonwebtoken.security.Keys; // <-- Import for secure key generation
import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.items.user.model.AuthRequest;
import com.items.user.model.User;
import com.items.user.service.UserService;

import io.jsonwebtoken.Jwts;

@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    // 生成 key
    private final String SECRET_KEY = Base64.getEncoder()
            .encodeToString("myKey12345678901234567".getBytes(StandardCharsets.UTF_8));

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody AuthRequest request) {
        User newUser = userService.registerUser(request.getUsername(), request.getPassword());
        Map<String, String> response = new HashMap<>();
        response.put("message", "User" + newUser + "registered successfully!");
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            System.out.println("Attempting authentication for user: " + authRequest.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            System.out.println("Authentication successful!");
        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            throw new RuntimeException("Invalid credentials", e);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

        // 使用 key 登录
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(key)
                .compact();

        System.out.println("Authenticating user: " + authRequest.getUsername());
        String userRole = userService.getUserRole(authRequest.getUsername());
        System.out.println(userRole);
        return ResponseEntity.ok(Map.of(
                "token", token,
                "userId", authRequest.getUsername(),
                "role", userRole));
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}