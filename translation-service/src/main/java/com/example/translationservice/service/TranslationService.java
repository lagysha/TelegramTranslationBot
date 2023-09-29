package com.example.translationservice.service;

import com.azure.ai.translation.text.models.GetLanguagesResult;
import com.example.translationservice.client.AzureClient;
import com.example.translationservice.dto.TranslationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslationService {

    private final AzureClient azureClient;

    public String translate(TranslationRequestDto translationRequest) {
        return azureClient.translate(translationRequest);
    }

    public List<String> getLangTypes() {
        GetLanguagesResult languages = azureClient.getAllLanguages();
        return languages.getTranslation().entrySet().stream()
                .map(translationLanguage -> translationLanguage.getKey() + " -- " + translationLanguage.getValue().getName() + " (" + translationLanguage.getValue().getNativeName() + ")")
                .collect(Collectors.toList());
    }
}
