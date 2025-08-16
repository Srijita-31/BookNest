package com.bookworm.bookworm_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a User entity in the database.
 * This class maps to a 'users' table in the MySQL database.
 */
@Data // Lombok annotation to automatically generate getters, setters, etc.
@NoArgsConstructor // Lombok annotation for a no-argument constructor.
@AllArgsConstructor // Lombok annotation for an all-arguments constructor.
@Entity // Specifies that this class is a JPA entity.
@Table(name = "users") // Defines the name of the table in the database.
public class User {

    /**
     * The unique ID of the user. This is the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user. It must be unique.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * The password of the user. Stored as a hashed value.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The email of the user. It must be unique.
     */
    @Column(unique = true, nullable = false)
    private String email;
}
