package com.example.dispatcher.controller;

import com.example.dispatcher.controller.enums.Command;
import com.example.dispatcher.dto.TranslationRequestDto;
import com.example.dispatcher.service.impl.MessageProcessorServiceImpl;
import com.example.dispatcher.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.dispatcher.controller.enums.Command.*;

@Component
@RequiredArgsConstructor
@Log4j
public class UpdateProcessor {
    @Value("${bot.name}")
    private String botName;

    private final TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final MessageProcessorServiceImpl messageProcessorService;

    public void processUpdate(Update update) {
        if (update == null) {
            log.error("Null Update: " + update);
        } else if (update.hasMessage()) {
            log.info("Received Update: " + update);
            distributeMessageByType(update);
        } else {
            log.error("Unsupported message type is received: " + update);
        }
    }

    private void distributeMessageByType(Update update) {
        var message = update.getMessage();
        if (message.hasText()) {
            processTextMessage(update);
        } else {
            var leftChatMember = update.getMessage().getLeftChatMember();
            if (leftChatMember != null) {
                List<String> goodByes = new ArrayList<>();
                goodByes.add("Take care");
                goodByes.add("Take it easy");
                goodByes.add("Catch you later");
                goodByes.add("Have a good one");
                goodByes.add("Peace");
                setAnswerMessageTypeView(update,
                        goodByes.get(LocalDateTime.now().getSecond() % goodByes.size()) + " " +
                                leftChatMember.getFirstName());
            } else if (update.getMessage().getNewChatMembers() != null) {
                List<String> greetings = new ArrayList<>();
                greetings.add("Howdy!");
                greetings.add("What’s up?");
                greetings.add("Yo!");
                greetings.add("How are things?");
                greetings.add("Glory to Ukraine!");
                update
                        .getMessage()
                        .getNewChatMembers().forEach(
                                (object) -> {
                                    var name = object.getFirstName();
                                    if (!name.equals("Pupa"))
                                        setAnswerMessageTypeView(update,
                                                greetings.get(LocalDateTime.now().getNano() % greetings.size()) + " " +
                                                        object.getFirstName());
                                    else
                                        setAddBotMessageTypeView(update);
                                });
            }
        }
    }

    private void processTextMessage(Update update) {

        if (messageProcessorService.findAppUser(update) == null) {
            messageProcessorService.registerUser(update);
        }

        Long groupId = update.getMessage().getChat().getId();
        if (messageProcessorService.findGroupById(groupId) == null) {
            messageProcessorService.saveGroupById(groupId, update.getMessage().getFrom().getId());
        }

        var output = "";

        if (update.getMessage().getText().contains("/translate")
                && update.getMessage().isReply()) {
            String repliedText = update.getMessage().getReplyToMessage().getText();
            String text = update.getMessage().getText();
            String[] languages = text.split(" ");
            if(languages.length!=3){
                output = "Something went wrong with your input. Maybe you made a mistake. Type /help to see again command usage";
            }else {
                try {
                    output = messageProcessorService.translate(new TranslationRequestDto(languages[1], languages[2], repliedText));
                }catch (Exception e){
                    output = extractErrorMessage(e.getMessage());
                }
            }
        } else {
            if(!update.getMessage().getText().contains("@"+botName)){
                return;
            }
            output = processServiceCommand(update);
        }

        setAnswerMessageTypeView(update, output);
    }

    public static String extractErrorMessage(String errorMessage) {
        Pattern pattern = Pattern.compile("\"message\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "Unexpected exception";
    }
    private void setAnswerMessageTypeView(Update update, String message) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, message);
        setView(sendMessage);
    }

    private void setAddBotMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, "Hi, I am translator bot. To get started please type command /help");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    private String processServiceCommand(Update update) {
        var serviceCommand = Command.fromValue(update.getMessage().getText().replace("@"+botName,""));

        if (HELP.equals(serviceCommand)) {
            return help();
        } else if (START.equals(serviceCommand)) {
            return "Hi! To see all available commands type /help";
        } else if (GETSTARTED.equals(serviceCommand)) {
            return "Hi! Please follow instructions to configure bot\n"
                    + "1. Open group settings \n"
                    + "2. Change the chat history for new members setting\n"
                    + "3. Hit \"Save\" on the chat visibility menu\n"
                    + "4. Hit \"Save\" on the group settings menu\n";
        } else if (LIST.equals(serviceCommand)) {
            return String.join("\n", messageProcessorService.getLangTypes());
        } else {
            return "Mmmm... Unknown command. To see all available commands type /help";
        }
    }

    private String help() {
        return "All available commands:\n"
                + "/getStarted - instructions how to add bot\n"
                + "/languages - to view all supported languages\n"
                + "/translate <lang1> <lang2> - reply to a message to translate\"\n" +
                "For example: /translate en fr";
    }
}
