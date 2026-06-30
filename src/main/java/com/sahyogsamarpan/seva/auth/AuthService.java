package com.sahyogsamarpan.seva.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.sahyogsamarpan.seva.auth.dto.LoginRequest;
import com.sahyogsamarpan.seva.auth.dto.LoginResponse;
import com.sahyogsamarpan.seva.auth.dto.UserResponse;
import com.sahyogsamarpan.seva.security.JwtService;
import com.sahyogsamarpan.seva.user.UserPrincipal;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final long expirationMs;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.expirationMs = expirationMs;
    }

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().trim().toLowerCase(),
                        request.getPassword()));

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtService.generateToken(principal);

        return new LoginResponse(
                token,
                "Bearer",
                expirationMs,
                principal.getEmail(),
                principal.getRole());
    }

    public UserResponse getCurrentUser(UserPrincipal principal) {
        return new UserResponse(
                principal.getId(),
                principal.getEmail(),
                principal.getRole());
    }
}
