package com.example.dispatcher.controller;

import com.example.dispatcher.controller.enums.Command;
import com.example.dispatcher.controller.enums.NextAction;
import com.example.dispatcher.dto.LangType;
import com.example.dispatcher.dto.RequestUser;
import com.example.dispatcher.dto.TranslationSettingDto;
import com.example.dispatcher.dto.UserDto;
import com.example.dispatcher.service.impl.MessageProcessorServiceImpl;
import com.example.dispatcher.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.dispatcher.controller.enums.Command.*;

@Component
@AllArgsConstructor
@Log4j
public class UpdateProcessor {
    private TelegramBot telegramBot;
    private MessageUtils messageUtils;
    private MessageProcessorServiceImpl messageProcessorService;

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
                        goodByes.get(LocalDateTime.now().getNano() % goodByes.size()) + " " +
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

        UserDto appUser = messageProcessorService.findAppUser(update);
        if (appUser == null) {
            appUser = messageProcessorService.registerUser(update);
        }

        Long groupId = update.getMessage().getChat().getId();
        if (messageProcessorService.findGroupById(groupId) == null) {
            messageProcessorService.saveGroupById(groupId, update.getMessage().getFrom().getId());
        }

        var output = "";
        var nextUserAction = appUser.getNextAction();

        if (STOP.equals(Command.fromValue(update.getMessage().getText()))) {
            setUserAction(appUser, NextAction.NONE);
            output = "Translating Stopped!";
        } else if (!update.getMessage().getText().startsWith("/") && nextUserAction.equals(NextAction.TRANSLATE)) {
            var message = update.getMessage().getText();
            output = messageProcessorService.translate(groupId, message);
            if (output.isBlank()) {
                return;
            }
        } else if (nextUserAction.equals(NextAction.CONFIGURE_LANGUAGES)) {
            var message = update.getMessage().getText();
            String languageFrom;
            String languageTo;
            Matcher matcherFrom = Pattern.compile("(?<=from=)([a-zA-z]{2})").matcher(message);
            Matcher matcherTo = Pattern.compile("(?<=to=)([a-zA-z]{2})").matcher(message);
            if (matcherFrom.groupCount() != 1 || matcherTo.groupCount() != 1) {
                setUserAction(appUser, NextAction.NONE);
                output = "Wrong input! Check your data format";
            } else {
                languageFrom = matcherFrom.group(0);
                languageTo = matcherTo.group(0);
                try {
                    messageProcessorService.addSetting(
                            TranslationSettingDto
                                    .builder()
                                    .fromLangCode(languageFrom)
                                    .toLangCode(languageTo)
                                    .groupId(groupId)
                                    .build()
                    );
                    setUserAction(appUser, NextAction.TRANSLATE);
                    output = "Languages were successfully configured!";
                } catch (Exception e) {
                    setUserAction(appUser, NextAction.NONE);
                    output = "Translate API is busy now!";
                }
            }
        } else {
            output = processServiceCommand(update, appUser);
        }

        setAnswerMessageTypeView(update, output);
    }

    private void setAnswerMessageTypeView(Update update, String message) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, message);
        setView(sendMessage);
    }

    private void setAddBotMessageTypeView(Update update) {
        var sendMessage = messageUtils.generateSendMessageWithText(update, "Translate Bot was successfully added!");
        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }

    private String processServiceCommand(Update update, UserDto appUser) {
        var serviceCommand = Command.fromValue(update.getMessage().getText());

        if (HELP.equals(serviceCommand)) {
            return help();
        } else if (START.equals(serviceCommand)) {
            return "Hi! To see all available commands type /help";
        } else if (ADDBOT.equals(serviceCommand)) {
            return "Hi! Please follow instructions to add bot: \n"
                    + "Search for bot with name: botName\n"
                    + "Then click on bot profile and click on \"More\" button (three dots in column)\n"
                    + "Then click on \"add to group\", add the bot"
                    + "And a few more steps:\n"
                    + "Click on group profile and click on \"More\" button (three dots in column)\n"
                    + "Then click on \"Manage group\""
                    + "Then click on \"Add Administrator\", chose bot and click save";
        } else if (LANGUAGE.equals(serviceCommand)) {
            setUserAction(appUser, NextAction.CONFIGURE_LANGUAGES);
            return "Write to languages in a format -> from=language to=language\n"
                    + "For example:from=en to=uk";
        } else if (LIST.equals(serviceCommand)) {
            List<LangType> langTypes = messageProcessorService.getLangTypes();
            String output = "Languages and code: \n";
            output = output + langTypes
                    .stream()
                    .map(langType -> "Code: " + langType.getCode() + " language: " + langType.getLang() + "\n")
                    .collect(Collectors.joining());
            return output;
        } else {
            return "Mmmm... Unknown command. To see all available commands type /help";
        }
    }

    private void setUserAction(UserDto appUser, NextAction nextAction) {
        appUser.setNextAction(nextAction);
        messageProcessorService.updateUser(RequestUser.builder()
                .id(appUser.getTelegramUserId())
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .nextAction(appUser.getNextAction()).build());
    }

    private String help() {
        return "All available commands:\n"
                + "/addBot - adds a new bot to your group\n"
                + "/list - to view all supported languages\n"
                + "/language - to configure bot language for translation\n"
                + "/stop - to stop translating messages";
    }
}
