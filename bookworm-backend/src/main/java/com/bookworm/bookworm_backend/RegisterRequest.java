package com.bookworm.bookworm_backend;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for user registration requests.
 * This class ensures that all required fields for user registration are present
 * and correctly validated before processing.
 */
@Data // Generates getters, setters, toString, equals, and hashCode
@Builder // Generates a builder pattern for object creation
@AllArgsConstructor // Generates a constructor with all arguments
@NoArgsConstructor // Generates a no-argument constructor
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;
}
