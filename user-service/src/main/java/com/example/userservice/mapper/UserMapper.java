package com.example.userservice.mapper;

import com.example.userservice.data.User;
import com.example.userservice.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtotoUser(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    UserDto userToUserDto(User user);
}
