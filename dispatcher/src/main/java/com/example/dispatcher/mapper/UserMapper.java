package com.example.dispatcher.mapper;

import com.example.dispatcher.dto.RequestUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.telegram.telegrambots.meta.api.objects.User;

@Mapper
public interface UserMapper {

    @Mapping(source = "userName", target = "username")
    RequestUser telegramUserToRequestUser(User telegramUserMapper);
}
