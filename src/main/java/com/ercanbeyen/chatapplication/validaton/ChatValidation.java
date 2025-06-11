package com.ercanbeyen.chatapplication.validaton;

import com.ercanbeyen.chatapplication.exception.BadRequestException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@UtilityClass
public class ChatValidation {
    private final int MINIMUM_LENGTH_OF_USERNAME = 3;
    private final int MAXIMUM_LENGTH_OF_USERNAME = 30;

    public void checkUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new BadRequestException("Empty or null username");
        }

        if (username.length() < MINIMUM_LENGTH_OF_USERNAME || username.length() > MAXIMUM_LENGTH_OF_USERNAME) {
            throw new BadRequestException("Invalid username length");
        }

        log.info("Username is valid");
    }
}
