package com.sahyogsamarpan.seva.auth.dto;

import com.sahyogsamarpan.seva.user.Role;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresIn,
        String email,
        Role role) {
}
