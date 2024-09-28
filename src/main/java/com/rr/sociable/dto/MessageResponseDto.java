package com.rr.sociable.dto;

import com.rr.sociable.entity.Message;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MessageResponseDto extends MessageDto {
    Action action;
    Long messageId;

   public MessageResponseDto(Action action) {
       this.action = action;
   }

    public MessageResponseDto(Action action, Message message){
        this.setAction(action);
        this.setMessageId(message.getId());
        this.setGroupId(message.getGroupId());
        this.setUserId(message.getUserId());
        this.setContent(message.getContent());
    }

    public MessageResponseDto(Message message){
       this.setMessageId(message.getId());
       this.setGroupId(message.getGroupId());
       this.setUserId(message.getUserId());
       this.setContent(message.getContent());
    }
}
