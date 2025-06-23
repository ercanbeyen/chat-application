package com.ercanbeyen.chatapplication.service;

import java.util.List;

public interface ChatService {
    void addUser(String username);
    void removeUser(String username);
    void checkUserInChatroom(String username);
    List<String> getUsersInChatroom();
}
