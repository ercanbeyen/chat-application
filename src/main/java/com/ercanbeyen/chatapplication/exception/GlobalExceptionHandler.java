package com.ercanbeyen.chatapplication.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @MessageExceptionHandler
    @SendTo("/topic/errors")
    public String handleException(Exception exception) {
        log.error("ErrorHandlerController::handleException --> Exception message: {}", exception.getMessage());
        return "Error: " + exception.getMessage();
    }
}
