package com.satya.ecom.dto.user;

import com.satya.ecom.model.Role;

import java.util.UUID;

public record UserResponseDto(UUID id, String name, String email, Role role) {
}
