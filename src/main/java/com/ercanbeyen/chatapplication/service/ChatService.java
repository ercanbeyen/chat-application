package com.ercanbeyen.chatapplication.service;

import com.ercanbeyen.chatapplication.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ChatService {
    public void checkUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new BadRequestException("Empty or null username");
        }

        final int MINIMUM_LENGTH_OF_USERNAME = 3;
        final int MAXIMUM_LENGTH_OF_USERNAME = 30;

        if (username.length() < MINIMUM_LENGTH_OF_USERNAME || username.length() > MAXIMUM_LENGTH_OF_USERNAME) {
            throw new BadRequestException("Invalid username length");
        }

        log.info("Username is valid");
    }
}
