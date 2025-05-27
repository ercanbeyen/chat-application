package com.ercanbeyen.chatapplication.model;

import com.ercanbeyen.chatapplication.constant.enums.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
}
