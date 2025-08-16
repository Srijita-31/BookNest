package com.bookworm.bookworm_backend.repository;

import com.bookworm.bookworm_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository interface for the User entity.
 * Spring Data JPA will automatically provide all the necessary CRUD methods.
 * We also define a custom query method to find a user by their username and email.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // This is a custom query method. Spring Data JPA will automatically implement this for us.
    // It's crucial for authentication as we will look up users by their username.
    Optional<User> findByUsername(String username);

    // New custom query method to find a user by their email, which is used for login and registration.
    Optional<User> findByEmail(String email);
}

