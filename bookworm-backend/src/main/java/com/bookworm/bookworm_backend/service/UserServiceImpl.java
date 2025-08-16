package com.bookworm.bookworm_backend.service;

import com.bookworm.bookworm_backend.model.User;
import com.bookworm.bookworm_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation for user-related business logic.
 * This class handles operations like user registration, including password hashing.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUser(User user) {
        // Hash the password before saving to the database.
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        // Check if the provided raw password matches the encoded password.
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

