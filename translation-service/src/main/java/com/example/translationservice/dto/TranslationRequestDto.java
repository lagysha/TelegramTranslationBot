package com.example.translationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TranslationRequestDto {
    private String source_language;
    private String translation_language;
    private String text;
}
