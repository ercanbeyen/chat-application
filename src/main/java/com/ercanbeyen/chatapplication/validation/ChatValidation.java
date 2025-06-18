package com.ercanbeyen.chatapplication.validation;

import com.ercanbeyen.chatapplication.exception.BadRequestException;
import com.ercanbeyen.chatapplication.model.ChatMessage;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@UtilityClass
public class ChatValidation {
    public void checkMessage(ChatMessage chatMessage) {
        String content = chatMessage.getContent();

        if (StringUtils.isBlank(content)) {
            throw new BadRequestException("Empty or null message content");
        }

        log.info("Chat message is valid");
    }
}
