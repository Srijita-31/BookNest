package com.bookworm.bookworm_backend.config;

import com.bookworm.bookworm_backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Custom filter to handle JWT authentication for incoming requests.
 * This filter intercepts all incoming requests and, if a valid JWT is present,
 * sets the user's authentication in the security context.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * The core logic of the filter.
     * It checks for a JWT in the Authorization header.
     * If a valid token is found, it authenticates the user.
     *
     * @param request The HttpServletRequest.
     * @param response The HttpServletResponse.
     * @param filterChain The FilterChain to continue the request.
     * @throws ServletException If a servlet-related error occurs.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        // Explicitly bypass JWT filter for public authentication endpoints.
        // This is a failsafe to prevent the SignatureException we've been seeing.
        if (request.getServletPath().startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        try {
            userEmail = jwtUtil.extractUsername(jwt);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtUtil.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            // Handle expired token gracefully, e.g., by logging or sending an error response.
            // For now, we simply let the filter chain continue without authentication.
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            // Log the signature mismatch to help with debugging.
            // This is the error we have been seeing.
            System.err.println("JWT signature does not match: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other exceptions during token parsing/validation.
            System.err.println("Error processing JWT token: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
