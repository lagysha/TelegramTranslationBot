package com.example.userservice.mapper;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper instance = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "telegramUserId", source = "id")
    User requestUserToUser(RequestUser requestUser);
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
}
