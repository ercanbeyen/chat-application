package com.ercanbeyen.chatapplication.controller;

import com.ercanbeyen.chatapplication.model.ChatMessage;
import com.ercanbeyen.chatapplication.util.SessionUtil;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/chat/users")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        SessionUtil.setValueToHeader(headerAccessor, "username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat/messages")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
}
