package com.ercanbeyen.chatapplication.service;

public interface ChatService {
    void addUser(String username);
    void removeUser(String username);
    void checkUserInChatroom(String username);
}
