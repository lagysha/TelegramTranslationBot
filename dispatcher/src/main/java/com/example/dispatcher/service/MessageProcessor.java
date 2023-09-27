package com.example.dispatcher.service;

import com.example.dispatcher.dto.TranslationRequestDto;
import com.example.dispatcher.dto.UserDto;
import com.example.dispatcher.dto.group.GroupDto;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


public interface MessageProcessor {

    UserDto registerUser(Update update);

    UserDto findAppUser(Update update);

    GroupDto findGroupById(Long id);

    GroupDto saveGroupById(Long groupId,
                           Long adminId);

    String translate(TranslationRequestDto translationRequest);

    List<String> getLangTypes();
}
