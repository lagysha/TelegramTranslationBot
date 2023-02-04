package com.example.translationservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TranslationSettingDto {

    @NotNull(message="groupId can't be empty")
    private Long groupId;

    @NotEmpty(message="fromLangCode can't be empty")
    private String fromLangCode;

    @NotEmpty(message="toLangCode can't be empty")
    private String toLangCode;
}
