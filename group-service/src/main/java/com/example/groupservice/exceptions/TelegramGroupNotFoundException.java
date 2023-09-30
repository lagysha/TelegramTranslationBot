package com.example.groupservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TelegramGroupNotFoundException extends RuntimeException{

    public TelegramGroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramGroupNotFoundException(String message) {
        super(message);
    }
}
