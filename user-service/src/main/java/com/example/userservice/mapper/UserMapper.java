package com.example.userservice.mapper;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "telegramUserId", source = "id")
    User requestUserToUser(RequestUser requestUser);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);
}
