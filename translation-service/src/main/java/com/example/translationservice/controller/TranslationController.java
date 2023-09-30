package com.example.translationservice.controller;

import com.example.translationservice.dto.TranslationRequestDto;
import com.example.translationservice.service.impl.TranslationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationServiceImpl translationServiceImpl;

    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestBody TranslationRequestDto translationRequest) {
        return ResponseEntity.ok(translationServiceImpl.translate(translationRequest));
    }

    @GetMapping("/lang")
    public ResponseEntity<List<String>> getLangTypes() {
        return ResponseEntity.ok(translationServiceImpl.getLangTypes());
    }
}
