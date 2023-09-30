package com.example.translationservice.service;

import com.example.translationservice.dto.TranslationRequestDto;

import java.util.List;

public interface TranslationService {
    String translate(TranslationRequestDto translationRequest);
    List<String> getLangTypes();
}
