package com.ercanbeyen.chatapplication.validation;

import com.ercanbeyen.chatapplication.exception.BadRequestException;
import com.ercanbeyen.chatapplication.model.ChatMessage;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Slf4j
@UtilityClass
public class ChatValidation {
    private final int MINIMUM_LENGTH_OF_USERNAME = 3;
    private final int MAXIMUM_LENGTH_OF_USERNAME = 30;
    private final int MAXIMUM_LENGTH_OF_MESSAGE = 160;

    public void checkUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new BadRequestException("Empty or null username");
        }

        if (username.length() < MINIMUM_LENGTH_OF_USERNAME || username.length() > MAXIMUM_LENGTH_OF_USERNAME) {
            throw new BadRequestException("Invalid username length");
        }

        log.info("Username is valid");
    }

    public void checkMessage(ChatMessage chatMessage) {
        if (Optional.ofNullable(chatMessage).isEmpty()) {
            throw new BadRequestException("Null chat message");
        }

        if (Optional.ofNullable(chatMessage.getType()).isEmpty()) {
            throw new BadRequestException("Message Type cannot be null");
        }

        if (Optional.ofNullable(chatMessage.getSender()).isEmpty()) {
            throw new BadRequestException("Sender cannot be null");
        }

        String content = chatMessage.getContent();

        if (StringUtils.isBlank(content)) {
            throw new BadRequestException("Empty or null message content");
        }

        if (content.length() > MAXIMUM_LENGTH_OF_MESSAGE) {
            throw new BadRequestException("Invalid message content length");
        }

        log.info("Chat message is valid");
    }
}
