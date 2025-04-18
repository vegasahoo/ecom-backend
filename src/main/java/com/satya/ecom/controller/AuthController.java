package com.satya.ecom.controller;

import com.satya.ecom.dto.user.LoginUserDto;
import com.satya.ecom.dto.user.RegisterUserDto;
import com.satya.ecom.dto.user.UserResponseDto;
import com.satya.ecom.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserRequest){
        UserResponseDto user = authService.registerUser(registerUserRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginUserDto loginUserRequest){
        boolean success = authService.loginUser(loginUserRequest);
        return ResponseEntity.ok(success);
    }
}
