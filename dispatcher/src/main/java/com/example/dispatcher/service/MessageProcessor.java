package com.example.dispatcher.service;

import com.example.dispatcher.dto.LangType;
import com.example.dispatcher.dto.RequestUser;
import com.example.dispatcher.dto.TranslationSettingDto;
import com.example.dispatcher.dto.UserDto;
import com.example.dispatcher.dto.group.GroupDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


public interface MessageProcessor {

    UserDto registerUser(Update update);

    UserDto findAppUser(Update update);

    UserDto updateUser(RequestUser requestUser);

    GroupDto findGroupById(Long id);

    GroupDto saveGroupById(Long groupId,
                           Long adminId);

    String translate(Long groupId, String text);

    Object addSetting(TranslationSettingDto translationSettingDto);

    List<LangType> getLangTypes();
}
