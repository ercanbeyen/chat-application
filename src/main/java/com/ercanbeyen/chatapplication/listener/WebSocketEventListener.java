package com.ercanbeyen.chatapplication.listener;

import com.ercanbeyen.chatapplication.constant.enums.MessageType;
import com.ercanbeyen.chatapplication.model.ChatMessage;
import com.ercanbeyen.chatapplication.service.ChatService;
import com.ercanbeyen.chatapplication.util.SessionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageSendingOperations;
    private final ChatService chatService;

    @EventListener
    public void handleSessionConnectEvent(SessionConnectedEvent event) {
        String sessionId = (String) event.getMessage()
                .getHeaders()
                .get("simpSessionId");

        log.info("Session Connect Event:: Session id: {}", sessionId);
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        log.info("Session Disconnect Event:: Session id: {}", event.getSessionId());

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) SessionUtil.getValueFromHeader(headerAccessor, "username");

        if (Optional.ofNullable(username).isPresent()) {
            log.info("User disconnected: {}", username);

            chatService.removeUser(username);

            ChatMessage chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            List<String> usersInChatroom = chatService.getUsersInChatroom();
            usersInChatroom.forEach(sendUser -> messageSendingOperations.convertAndSend("/topic/users", usersInChatroom));

            messageSendingOperations.convertAndSend("/topic/public", chatMessage);
        }
    }
}
