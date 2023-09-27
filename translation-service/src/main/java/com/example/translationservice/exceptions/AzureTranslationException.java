package com.example.translationservice.exceptions;

public class AzureTranslationException extends RuntimeException {

    public AzureTranslationException(String message) {
        super(message);
    }

    public AzureTranslationException(String message, Throwable cause) {
        super(message, cause);
    }
}
