package com.bookworm.bookworm_backend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for authentication responses.
 * This class is used to send the JWT token back to the client after a successful login.
 */
@Data
@Builder // This is the crucial annotation that generates the builder() method.
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
}
