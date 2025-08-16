package com.bookworm.bookworm_backend.controller;

import com.bookworm.bookworm_backend.AuthRequest;
import com.bookworm.bookworm_backend.AuthResponse;
import com.bookworm.bookworm_backend.RegisterRequest;
import com.bookworm.bookworm_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest request) {
        if (authService.userExists(request.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        authService.register(request);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
