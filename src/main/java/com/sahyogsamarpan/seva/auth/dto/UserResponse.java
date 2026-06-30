package com.sahyogsamarpan.seva.auth.dto;

import com.sahyogsamarpan.seva.user.Role;

public record UserResponse(Long id, String email, Role role) {
}
