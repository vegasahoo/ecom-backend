package com.satya.ecom.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDto(@NotBlank(message = "Name is required") String name,
                              @Email(message = "Email should be valid") String email,
                              @NotBlank(message = "Password is required") String password) {

}
