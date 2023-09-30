package com.example.translationservice.service.impl;

import com.azure.ai.translation.text.models.GetLanguagesResult;
import com.azure.ai.translation.text.models.TranslationLanguage;
import com.example.translationservice.client.AzureClient;
import com.example.translationservice.dto.TranslationRequestDto;
import com.example.translationservice.service.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final AzureClient azureClient;

    public String translate(TranslationRequestDto translationRequest) {
        return azureClient.translate(translationRequest);
    }

    public List<String> getLangTypes() {
        GetLanguagesResult languages = azureClient.getAllLanguages();
        return languages.getTranslation().entrySet().stream()
                .map(getPrettyStringWithCode())
                .collect(Collectors.toList());
    }

    private static Function<Map.Entry<String, TranslationLanguage>, String> getPrettyStringWithCode() {
        return translationLanguage -> translationLanguage.getKey() + " -- "
                + translationLanguage.getValue().getName() + " (" + translationLanguage.getValue().getNativeName() + ")";
    }
}
