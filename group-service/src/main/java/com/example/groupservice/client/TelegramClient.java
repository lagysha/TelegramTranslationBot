package com.example.groupservice.client;

import com.example.groupservice.exceptions.TelegramGroupNotFoundException;
import com.example.groupservice.model.Group;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TelegramClient {

    @Value("${bot.token}")
    private String botToken;

    public Group getTelegramGroup(Long id) {
        try {
            return WebClient.create("https://api.telegram.org/bot" + botToken + "/getChat")
                    .get()
                    .uri(UriBuilder -> UriBuilder.queryParam("chat_id", id)
                            .build())
                    .retrieve()
                    .bodyToMono(Group.class)
                    .block();
        } catch (RuntimeException runtimeException) {
            throw new TelegramGroupNotFoundException("Group with id = " + id + " not found");
        }
    }
}
