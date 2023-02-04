package com.example.translationservice.controller;

import com.example.translationservice.dto.TranslationSettingDto;
import com.example.translationservice.entity.LangType;
import com.example.translationservice.service.TranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestParam Long groupId, @RequestParam String text) {
        return translationService.translate(groupId, text);
    }

    @PostMapping("/setting")
    public ResponseEntity<?> addSetting(@Valid @RequestBody TranslationSettingDto translationSettingDto) {
        translationService.addSetting(translationSettingDto);
        return new ResponseEntity(translationSettingDto, HttpStatus.CREATED);
    }

    @GetMapping("/lang")
     public ResponseEntity<List<LangType>> getLangTypes() {
        List<LangType> types = translationService.getLangTypes();
        return ResponseEntity.ok(types);
    }
}
