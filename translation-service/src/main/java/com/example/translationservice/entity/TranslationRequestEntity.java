package com.example.translationservice.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TranslationRequestEntity {

    private String fromLang;
    private String toLang;
    private String text;
}
