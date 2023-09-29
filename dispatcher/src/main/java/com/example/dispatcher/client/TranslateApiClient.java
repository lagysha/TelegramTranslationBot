package com.example.dispatcher.client;

import com.example.dispatcher.dto.TranslationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "translate-service")
public interface TranslateApiClient {
    @PostMapping("/translate")
    String translate(@RequestBody TranslationRequestDto translationRequest);

    @GetMapping("/lang")
    List<String> getLangTypes();
}
