package com.ercanbeyen.chatapplication.service.impl;

import com.ercanbeyen.chatapplication.constant.message.Logging;
import com.ercanbeyen.chatapplication.exception.NotFoundException;
import com.ercanbeyen.chatapplication.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
    private final List<String> usersInChatroom = new LinkedList<>();

    public void addUser(String username) {
        usersInChatroom.add(username);
        log.info(Logging.USERS_IN_CHATROOM, usersInChatroom);
    }

    public void removeUser(String username) {
        checkUserInChatroom(username);
        usersInChatroom.remove(username);
        log.info(Logging.USERS_IN_CHATROOM, usersInChatroom);
    }

    public void checkUserInChatroom(String username) {
        usersInChatroom.stream()
                .parallel()
                .filter(userInChatroom -> userInChatroom.equals(username))
                .findAny()
                .orElseThrow(() -> new NotFoundException("User is not found"));

        log.info("User {} is found", username);

    }
}
