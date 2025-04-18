package com.satya.ecom.service.impl;

import com.satya.ecom.dto.user.RegisterUserDto;
import com.satya.ecom.dto.user.UserResponseDto;
import com.satya.ecom.exception.ResourceNotFoundException;
import com.satya.ecom.mapper.UserMapper;
import com.satya.ecom.model.Cart;
import com.satya.ecom.model.Role;
import com.satya.ecom.model.User;
import com.satya.ecom.repository.UserRepo;
import com.satya.ecom.service.AuthService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponseDto registerUser(RegisterUserDto registerUserDto) {
        User user = UserMapper.mapRegisterUserDtoToUser(registerUserDto);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Cart cart = new Cart();
        user.setCart(cart);
        userRepo.save(user);
        return UserMapper.mapUserToUserResponseDto(user);
    }

    @Override
    public UserResponseDto getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return UserMapper.mapUserToUserResponseDto(user);
    }
}
