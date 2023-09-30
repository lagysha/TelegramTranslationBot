package com.example.dispatcher.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
@Log4j
public class TelegramBot extends TelegramWebhookBot {

    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;
    @Value("${bot.uri}")
    private String botUri;

    private UpdateProcessor updateProcessor;

    @Autowired
    public void setUpdateProcessor(@Lazy UpdateProcessor updateProcessor) {
        this.updateProcessor = updateProcessor;
    }

    @PostConstruct
    public void init() {
        try{
            var setWebHook = SetWebhook.builder()
                    .url(botUri)
                    .build();
            this.setWebhook(setWebHook);
        } catch (TelegramApiException e) {
            log.error(e.getCause() +" " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendAnswerMessage(SendMessage message){
        if(message!=null){
            try {
                execute(message);
            } catch (TelegramApiException e) {
                log.error(e.getCause() +" " + e.getMessage());
            }
        }
    }

    @Override
    public String getBotPath() {
        return "/update";
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }
}
