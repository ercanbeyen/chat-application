package com.ercanbeyen.chatapplication.model;

import com.ercanbeyen.chatapplication.constant.enums.MessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {
    @NotNull(message = "Message type is mandatory")
    private MessageType type;
    @Size(max = 160, message = "Length of content should be less than equal to {max}")
    private String content;
    @NotBlank(message = "Sender is mandatory")
    @Size(min = 3, max = 30, message = "Length of sender should be between {min} and {max}")
    private String sender;
}
