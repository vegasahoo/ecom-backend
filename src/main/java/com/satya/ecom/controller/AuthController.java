package com.satya.ecom.controller;

import com.satya.ecom.dto.user.LoginUserDto;
import com.satya.ecom.dto.user.LoginUserResponseDto;
import com.satya.ecom.dto.user.RegisterUserDto;
import com.satya.ecom.dto.user.UserResponseDto;
import com.satya.ecom.security.jwt.JwtTokenProvider;
import com.satya.ecom.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(@RequestBody LoginUserDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        String token = jwtTokenProvider.generateToken(userDetails);
        LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto(userDetails.getUsername(), token);
        return ResponseEntity.ok(loginUserResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserDto registerUserRequest){
        UserResponseDto user = authService.registerUser(registerUserRequest);
        return ResponseEntity.ok(user);
    }
}
