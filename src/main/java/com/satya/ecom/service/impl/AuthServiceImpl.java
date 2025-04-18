package com.satya.ecom.service.impl;

import com.satya.ecom.dto.user.RegisterUserDto;
import com.satya.ecom.dto.user.UserResponseDto;
import com.satya.ecom.mapper.UserMapper;
import com.satya.ecom.model.Cart;
import com.satya.ecom.model.Role;
import com.satya.ecom.model.User;
import com.satya.ecom.repository.CartRepo;
import com.satya.ecom.repository.UserRepo;
import com.satya.ecom.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final CartRepo cartRepo;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(UserRepo userRepo, CartRepo cartRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponseDto registerUser(RegisterUserDto registerUserDto) {
        User user = UserMapper.mapRegisterUserDtoToUser(registerUserDto);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepo.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepo.save(cart);
        return UserMapper.mapUserToUserResponseDto(user);
    }

    @Override
    public UserResponseDto getCurrentUser() {
        return new UserResponseDto
                (UUID.fromString("e75871a2-d0f4-4bc0-a3ef-c65671b168e4"),
                        "Satya", "satya@gmail.com", Role.CUSTOMER);
    }
}
