package com.ercanbeyen.chatapplication.util;

import lombok.experimental.UtilityClass;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.Map;
import java.util.Objects;

@UtilityClass
public class SessionUtil {
    public Object getValueFromHeader(SimpMessageHeaderAccessor headerAccessor, String key) {
        return getSessionAttributes(headerAccessor)
                .get(key);
    }

    public void setValueToHeader(SimpMessageHeaderAccessor headerAccessor, String key, Object value) {
        getSessionAttributes(headerAccessor)
                .put(key, value);
    }

    private Map<String, Object> getSessionAttributes(SimpMessageHeaderAccessor headerAccessor) {
        return Objects.requireNonNull(headerAccessor.getSessionAttributes());
    }
}
