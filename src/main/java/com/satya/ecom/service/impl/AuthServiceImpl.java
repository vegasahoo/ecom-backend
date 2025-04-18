package com.satya.ecom.service.impl;

import com.satya.ecom.dto.user.LoginUserDto;
import com.satya.ecom.dto.user.RegisterUserDto;
import com.satya.ecom.dto.user.UserResponseDto;
import com.satya.ecom.mapper.UserMapper;
import com.satya.ecom.model.Cart;
import com.satya.ecom.model.Role;
import com.satya.ecom.model.User;
import com.satya.ecom.repository.CartRepo;
import com.satya.ecom.repository.UserRepo;
import com.satya.ecom.service.AuthService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Component
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final CartRepo cartRepo;

    public AuthServiceImpl(UserRepo userRepo, CartRepo cartRepo) {
        this.userRepo = userRepo;
        this.cartRepo = cartRepo;
    }


    @Override
    public UserResponseDto registerUser(RegisterUserDto registerUserDto) {
        User user = UserMapper.mapRegisterUserDtoToUser(registerUserDto);
        user.setRole(Role.CUSTOMER);
        user = userRepo.save(user);
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepo.save(cart);
        return UserMapper.mapUserToUserResponseDto(user);
    }

    @Override
    public boolean loginUser(LoginUserDto loginUserDto) {
        Optional<User> user = userRepo.findByEmail(loginUserDto.email());
        return user.map(value -> value.getPassword().equals(loginUserDto.password())).orElse(false);
    }

    @Override
    public UserResponseDto getCurrentUser() {
        return new UserResponseDto
                (UUID.fromString("e75871a2-d0f4-4bc0-a3ef-c65671b168e4"),
                        "Satya", "satya@gmail.com", Role.CUSTOMER);
    }
}
