package com.ercanbeyen.chatapplication.controller;

import com.ercanbeyen.chatapplication.model.ChatMessage;
import com.ercanbeyen.chatapplication.service.ChatService;
import com.ercanbeyen.chatapplication.util.SessionUtil;
import com.ercanbeyen.chatapplication.validaton.ChatValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Slf4j
@Controller
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat/users")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sender = chatMessage.getSender();

        SessionUtil.setValueToHeader(headerAccessor, "username", sender);
        ChatValidation.checkUsername(sender);
        chatService.addUser(sender);

        return chatMessage;
    }

    @MessageMapping("/chat/messages")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageExceptionHandler
    @SendTo("/topic/errors")
    public String handleException(Exception exception) {
        log.error("ErrorHandlerController::handleException --> Exception message: {}", exception.getMessage());
        return "Error: " + exception.getMessage();
    }
}
