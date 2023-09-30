package com.example.dispatcher.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TranslationRequestDto {
    @NotEmpty
    private String source_language;
    @NotEmpty
    private String translation_language;
    @NotEmpty
    private String text;
}
