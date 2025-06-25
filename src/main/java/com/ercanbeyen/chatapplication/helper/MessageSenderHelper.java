package com.ercanbeyen.chatapplication.helper;

import com.ercanbeyen.chatapplication.model.ChatMessage;
import com.ercanbeyen.chatapplication.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MessageSenderHelper {
    private final SimpMessageSendingOperations messageSendingOperations;
    private final ChatService chatService;

    public void sendMessage(ChatMessage chatMessage) {
        messageSendingOperations.convertAndSend("/topic/public", chatMessage);
    }

    public void showUsers() {
        List<String> usersInChatroom = chatService.getUsersInChatroom();
        usersInChatroom.forEach(sendUser -> messageSendingOperations.convertAndSend("/topic/users", usersInChatroom));
    }
}
