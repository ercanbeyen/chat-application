package com.ercanbeyen.chatapplication.controller;

import com.ercanbeyen.chatapplication.model.ChatMessage;
import com.ercanbeyen.chatapplication.service.ChatService;
import com.ercanbeyen.chatapplication.util.SessionUtil;
import com.ercanbeyen.chatapplication.validation.ChatValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    public ChatMessage addUser(@Valid ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sender = chatMessage.getSender();

        SessionUtil.setValueToHeader(headerAccessor, "username", sender);
        chatService.addUser(sender);

        return chatMessage;
    }

    @MessageMapping("/chat/messages")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Valid ChatMessage chatMessage) {
        ChatValidation.checkMessage(chatMessage);
        chatService.checkUserInChatroom(chatMessage.getSender());
        return chatMessage;
    }

    @MessageExceptionHandler
    @SendTo("/topic/errors")
    public String handleException(Exception exception) {
        log.error("ErrorHandlerController::handleException --> Exception message: {}", exception.getMessage());
        return "Error: " + exception.getMessage();
    }
}
