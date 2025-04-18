package com.satya.ecom.service;

import com.satya.ecom.dto.user.LoginUserDto;
import com.satya.ecom.dto.user.RegisterUserDto;
import com.satya.ecom.dto.user.UserResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    UserResponseDto registerUser(RegisterUserDto registerUserDto);
    boolean loginUser(LoginUserDto loginUserDto);
    UserResponseDto getCurrentUser();
}
