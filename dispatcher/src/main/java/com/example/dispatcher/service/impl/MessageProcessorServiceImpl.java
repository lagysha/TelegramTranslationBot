package com.example.dispatcher.service.impl;


import com.example.dispatcher.client.GroupClient;
import com.example.dispatcher.client.TranslateApiClient;
import com.example.dispatcher.client.UserApiClient;
import com.example.dispatcher.dto.RequestUser;
import com.example.dispatcher.dto.TranslationRequestDto;
import com.example.dispatcher.dto.UserDto;
import com.example.dispatcher.dto.group.GroupCreateRequest;
import com.example.dispatcher.dto.group.GroupDto;
import com.example.dispatcher.mapper.UserMapper;
import com.example.dispatcher.service.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageProcessorServiceImpl implements MessageProcessor {

    private final GroupClient groupClient;
    private final UserApiClient userApiClient;
    private final UserMapper userMapper;
    private final TranslateApiClient translateApiClient;

    @Override
    public UserDto registerUser(Update update) {
        User telegramUser = update.getMessage().getFrom();
        RequestUser requestUser = userMapper.telegramUserToRequestUser(telegramUser);
        return userApiClient.saveUser(requestUser);
    }

    @Override
    public UserDto findAppUser(Update update) {
        Long userTelegramId = update.getMessage().getFrom().getId();
        return userApiClient.getUserByTelegramId(userTelegramId);
    }

    @Override
    public GroupDto findGroupById(Long id) {
        return groupClient.findGroupById(id);
    }

    @Override
    public GroupDto saveGroupById(Long groupId, Long adminId) {
        return groupClient.saveGroupById(new GroupCreateRequest(groupId,adminId));
    }

    @Override
    public String translate(TranslationRequestDto translationRequest) {
        return translateApiClient.translate(translationRequest);
    }

    @Override
    public List<String> getLangTypes() {
        return translateApiClient.getLangTypes();
    }
}
