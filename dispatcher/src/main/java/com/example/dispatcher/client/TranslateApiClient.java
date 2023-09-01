package com.example.dispatcher.client;

import com.example.dispatcher.dto.LangType;
import com.example.dispatcher.dto.TranslationSettingDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "translate-service")
public interface TranslateApiClient {
    @PostMapping("/translate")
    String translate(@RequestParam Long groupId, @RequestParam String text);

    @PostMapping("/setting")
    Object addSetting(@Valid @RequestBody TranslationSettingDto translationSettingDto);

    @GetMapping("/lang")
    List<LangType> getLangTypes();
}
