package com.bookworm.bookworm_backend.service;

import com.bookworm.bookworm_backend.AuthRequest;
import com.bookworm.bookworm_backend.AuthResponse;
import com.bookworm.bookworm_backend.RegisterRequest;
import com.bookworm.bookworm_backend.model.User;
import com.bookworm.bookworm_backend.repository.UserRepository; // Corrected import path
import com.bookworm.bookworm_backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user authentication and registration logic.
 * This acts as a middleman between the controller and the repository.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    /**
     * Using constructor injection is the recommended practice for dependency injection.
     * All required dependencies are provided through the constructor.
     */
    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public void register(RegisterRequest request) {
        // Create a new User object from the registration request DTO.
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Hash the password
        userRepository.save(user); // Save the new user to the database
    }

    public AuthResponse login(AuthRequest request) {
        // Authenticate the user with the provided email and password.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Find the user details from the repository.
        UserDetails userDetails = (UserDetails) userRepository.findByEmail(request.getEmail()).orElseThrow();
        // Generate a JWT token for the authenticated user.
        String jwtToken = jwtUtil.generateToken(String.valueOf(userDetails));
        // Return the token in an AuthResponse object.
        return AuthResponse.builder().token(jwtToken).build();
    }

    public boolean userExists(String email) {
        return false;
    }
}
