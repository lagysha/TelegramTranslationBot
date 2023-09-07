package com.example.dispatcher.service.impl;


import com.example.dispatcher.client.GroupClient;
import com.example.dispatcher.client.UserApiClient;

import com.example.dispatcher.client.TranslateApiClient;
import com.example.dispatcher.controller.enums.NextAction;
import com.example.dispatcher.dto.LangType;
import com.example.dispatcher.dto.TranslationSettingDto;
import com.example.dispatcher.dto.UserDto;
import com.example.dispatcher.dto.group.GroupCreateRequest;
import com.example.dispatcher.dto.group.GroupDto;
import com.example.dispatcher.dto.RequestUser;
import com.example.dispatcher.dto.UserDto;
import com.example.dispatcher.mapper.UserMapper;
import com.example.dispatcher.service.MessageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;
import org.telegram.telegrambots.meta.api.objects.User;

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
        requestUser.setNextAction(NextAction.NONE);
        var response = userApiClient.saveUser(requestUser);
        return response.getBody();
    }

    @Override
    public UserDto findAppUser(Update update) {
        Long userTelegramId = update.getMessage().getFrom().getId();
        var response = userApiClient.getUserByTelegramId(userTelegramId);
        return response.getBody();
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
    public String translate(Long groupId, String text) {
        return translateApiClient.translate(groupId,text);
    }

    @Override
    public Object addSetting(TranslationSettingDto translationSettingDto) {
        return translateApiClient.addSetting(translationSettingDto);
    }

    @Override
    public List<LangType> getLangTypes() {
        return translateApiClient.getLangTypes();
    }

    @Override
    public UserDto updateUser(RequestUser requestUser) {
        return userApiClient.updateUser(requestUser);
    }
}
