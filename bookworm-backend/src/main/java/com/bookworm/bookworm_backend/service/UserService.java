package com.bookworm.bookworm_backend.service;

import com.bookworm.bookworm_backend.model.User;

import java.util.Optional;

public interface UserService {
    User registerNewUser(User user);

    Optional<User> findByUsername(String username);

    boolean checkPassword(String rawPassword, String encodedPassword);
}
