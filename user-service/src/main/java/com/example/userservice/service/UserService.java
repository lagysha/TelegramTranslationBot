package com.example.userservice.service;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${bot.id}")
    private String botId;

    @Value("${bot.token}")
    private String botToken;

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserDto saveUser(RequestUser requestUser) {
        System.out.println(requestUser);
        var user = userMapper.requestUserToUser(requestUser);
        var persistedUser = userRepository.saveAndFlush(user); // will be flushed and we can get access to the LocalDateTime field immediately
        return userMapper.userToUserDto(persistedUser);
    }

    @Transactional
    public UserDto updateUser(RequestUser requestUser) {
        User user = userRepository.findEntityByTelegramUserId(requestUser.getId());
        user.setNextAction(requestUser.getNextAction());
        user.setUsername(requestUser.getUsername());
        user.setLastName(requestUser.getLastName());
        user.setFirstName(requestUser.getFirstName());
        return userMapper.userToUserDto(user);
    }

    @Transactional
    public UserDto getUser(Long id) {
        return userRepository.findBy(id);
    }

    @Transactional
    public UserDto getUserByTelegramId(Long id) {
        return userRepository.findByTelegramUserId(id);
    }


    //Can not make producer exception
    //Need to verify status - "administrator"
    //TODO: if needed add New TelegramUserResponseObject
    public String verifyBotStatus(String groupName) {
        String retrievedUser = WebClient.create("https://api.telegram.org/bot" + botToken + "/getChatMember")
                .get()
                .uri(UriBuilder -> UriBuilder
                        .queryParam("chat_id", "@" + groupName)
                        .queryParam("user_id", botId)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return retrievedUser.contains("administrator")?"Success":"Make bot an administrator!";
    }

    //TODO: if needed add New TelegramResponseObject
    public String unbanUser(String groupName, Long userId) {

        String response = WebClient.create("https://api.telegram.org/bot" + botToken + "/unbanChatMember")
                .get()
                .uri(UriBuilder -> UriBuilder
                        .queryParam("chat_id", "@" + groupName)
                        .queryParam("user_id", userId)
                          //So if the user is a member of the chat they will also be removed
                          //from the chat. If you don't want this,
                          //use the parameter only_if_banned. Returns True on success.
                        .queryParam("only_if_banned", true)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

            return response.contains("\"result\": true")?"Success":"Something went wrong";
    }
}
