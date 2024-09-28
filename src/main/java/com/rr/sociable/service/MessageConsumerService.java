package com.rr.sociable.service;

import com.rr.sociable.dto.Action;
import com.rr.sociable.dto.MessageDto;
import com.rr.sociable.dto.MessageResponseDto;
import com.rr.sociable.dto.MessageUpdateDto;
import com.rr.sociable.entity.Group;
import com.rr.sociable.entity.Message;
import com.rr.sociable.entity.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageConsumerService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final UserService userService;
    private final GroupService groupService;

    public MessageConsumerService(SimpMessagingTemplate simpMessagingTemplate, MessageService messageService, UserService userService, GroupService groupService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
        this.userService = userService;
        this.groupService = groupService;
    }

    @KafkaListener(topics = "group-messages-create", groupId = "chat_group")
    public void consume(MessageDto messageDto) {
        Optional<User> u = userService.findById(messageDto.getUserId());
        Optional<Group> g = groupService.findById(messageDto.getGroupId());
        if(u.isEmpty() || g.isEmpty()) {
            return;
        }

        if(!u.get().getGroupsIds().contains(messageDto.getGroupId()) ||
            !g.get().getMemberIds().contains(messageDto.getUserId())) {
            return;
        }

        Optional<Message> m = messageService.save(messageDto);
        if(m.isEmpty()) return;

        MessageResponseDto r = new MessageResponseDto(Action.CREATE, m.get());

        simpMessagingTemplate.convertAndSend("/topic/messages/" + r.getGroupId(), r);
    }

    @KafkaListener(topics = "group-messages-update", groupId = "chat_group")
    public void consumeUpdate(MessageUpdateDto messageUpdateDto) {
        Optional<Message> m = messageService.findById(messageUpdateDto.getMessageId());
        if(m.isEmpty()) return;

        Message message = m.get();
        message.setContent(messageUpdateDto.getContent());

        Optional<Message> saved = messageService.update(message);
        if(saved.isEmpty()) return;

        MessageResponseDto r = new MessageResponseDto(Action.UPDATE, saved.get());

        simpMessagingTemplate.convertAndSend("/topic/messages/" + r.getGroupId(), r);
    }

    @KafkaListener(topics = "group-messages-delete", groupId = "chat_group")
    public void consumeDelete(Long messageId) {
        MessageResponseDto r = new MessageResponseDto(Action.DELETE);
        r.setMessageId(messageId);

        Optional<Message> group = messageService.getGroupIdByMessageId(messageId);
        if (group.isEmpty()) return;

        Long groupId = group.get().getGroupId();

        messageService.delete(messageId);

        simpMessagingTemplate.convertAndSend("/topic/messages/" + groupId, r);
    }
}