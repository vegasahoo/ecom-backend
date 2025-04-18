package com.satya.ecom.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record LoginUserDto(@Email(message = "Email should be valid") String email,
                           @NotBlank(message = "Password is required") String password) {

}