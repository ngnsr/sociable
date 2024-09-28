package com.rr.sociable.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageUpdateDto extends MessageDto{
    Long messageId;

    public MessageUpdateDto(Long id, MessageDto message) {
        this.setMessageId(id);
        setContent(message.getContent());
        setUserId(message.getUserId());
        setGroupId(message.getGroupId());
    }
}
