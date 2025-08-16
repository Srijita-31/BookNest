package com.bookworm.bookworm_backend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for user authentication (login) requests.
 * This class captures the email and password for a login attempt.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String email;
    private String password;
}
