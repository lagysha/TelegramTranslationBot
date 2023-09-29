package com.example.translationservice.configuration;

import com.azure.ai.translation.text.TextTranslationClient;
import com.azure.ai.translation.text.TextTranslationClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${APIKEY}")
    private String APIKEY;
    @Value("${ENDPOINT}")
    private String ENDPOINT;
    @Value("${REGION}")
    private String REGION;

    @Bean
    public TextTranslationClient textTranslationClient() {
        AzureKeyCredential credential = new AzureKeyCredential(APIKEY);
        return new TextTranslationClientBuilder()
                .credential(credential)
                .region(REGION)
                .endpoint(ENDPOINT)
                .buildClient();
    }
}
