package com.satya.ecom.mapper;

import com.satya.ecom.dto.user.RegisterUserDto;
import com.satya.ecom.dto.user.UserResponseDto;
import com.satya.ecom.model.User;

public class UserMapper {

    public static User mapRegisterUserDtoToUser(RegisterUserDto registerUserDto){
        User user = new User();
        user.setName(registerUserDto.name());
        user.setEmail(registerUserDto.email());
        user.setPassword(registerUserDto.password());
        return user;
    }


    public static UserResponseDto mapUserToUserResponseDto(User user){
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }
}
