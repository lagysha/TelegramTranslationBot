package com.example.translationservice.service;

import com.azure.ai.translation.text.TextTranslationClient;
import com.azure.ai.translation.text.models.GetLanguagesResult;
import com.azure.ai.translation.text.models.TranslationLanguage;
import com.azure.core.util.BinaryData;
import com.example.translationservice.client.AzureClient;
import com.example.translationservice.configuration.Config;
import com.example.translationservice.dto.TranslationRequestDto;
import com.example.translationservice.exceptions.AzureTranslationException;
import com.example.translationservice.service.impl.TranslationServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class TranslationServiceImplTest {
    @MockBean
    Config config;

    @MockBean
    TextTranslationClient translationClient;

    @Mock
    AzureClient azureClient;

    @InjectMocks
    TranslationServiceImpl translationServiceImpl;

    @Mock
    GetLanguagesResult getLanguagesResult;

    @Test
    @Order(1)
    @DisplayName("translateMessage with valid request")
    public void translateMessage() {
        String expected = "Hello";
        when(azureClient.translate(any(TranslationRequestDto.class))).thenReturn(expected);

        String result = translationServiceImpl.translate(new TranslationRequestDto("uk","en","Привіт"));

        assertEquals(result,expected);
    }

    @Test
    @Order(2)
    @DisplayName("translateMessage with valid invalid request")
    public void translateMessageWithInvalidRequest() {
        when(azureClient.translate(any(TranslationRequestDto.class))).thenThrow(AzureTranslationException.class);

        assertThrows(AzureTranslationException.class,() ->
                translationServiceImpl.translate(new TranslationRequestDto("ukdasd","enasdasd","Привіт")));
    }


    //This is a trivial test, because Azure has lots of internal private classes.
    //In this case we can only provide an empty dummy map to check if method is invoked and nothing bad is happening
    @Test
    @Order(3)
    @DisplayName("getLangTypes retrieves empty language list")
    public void getLangTypes() {
        Map<String, TranslationLanguage> map = new HashMap<>();
        when(getLanguagesResult.getTranslation()).thenReturn(map);
        when(azureClient.getAllLanguages()).thenReturn(getLanguagesResult);
        getLanguagesResult = BinaryData.fromString("").toObject(GetLanguagesResult.class);

        List<String> langTypes = translationServiceImpl.getLangTypes();
        assertTrue(langTypes.isEmpty());
    }
}
