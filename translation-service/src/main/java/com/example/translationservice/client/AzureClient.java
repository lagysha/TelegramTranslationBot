package com.example.translationservice.client;

import com.azure.ai.translation.text.TextTranslationClient;
import com.azure.ai.translation.text.models.*;
import com.azure.core.exception.HttpResponseException;
import com.example.translationservice.dto.TranslationRequestDto;
import com.example.translationservice.exceptions.AzureTranslationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class AzureClient {

    private final TextTranslationClient client;

    public GetLanguagesResult getAllLanguages() {
        return client.getLanguages();
    }

    public String translate(TranslationRequestDto request) {
        String from = request.getSource_language();
        List<String> targetLanguages = new ArrayList<>();
        targetLanguages.add(request.getTranslation_language());
        List<InputTextItem> content = new ArrayList<>();
        content.add(new InputTextItem(request.getText()));

        try {
            List<TranslatedTextItem> translations = client.translate(
                    targetLanguages,
                    content,
                    null,
                    from,
                    TextType.PLAIN,
                    null,
                    ProfanityAction.NO_ACTION,
                    ProfanityMarker.ASTERISK,
                    false,
                    false,
                    null,
                    null,
                    null,
                    false);

            return translations.get(0).getTranslations().get(0).getText();
        } catch (HttpResponseException e) {
            String errorMessage = getErrorMessage(e);
            throw new AzureTranslationException(errorMessage, e.getCause());
        }
    }

    private static String getErrorMessage(RuntimeException e) {
        String errorMessage = e.getMessage();
        Matcher matcher = Pattern.compile("(?<=\\\"message\\\":\\\").+?(?=\\\"}})").matcher(errorMessage);
        matcher.find();
        errorMessage = matcher.group();
        return errorMessage;
    }
}
