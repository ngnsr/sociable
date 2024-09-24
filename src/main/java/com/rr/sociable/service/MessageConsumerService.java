package com.rr.sociable.service;

import com.rr.sociable.dto.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    public MessageConsumerService(SimpMessagingTemplate simpMessagingTemplate, MessageService messageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageService = messageService;
    }

    @KafkaListener(topics = "group-messages", groupId = "chat_group")
    public void consume(MessageDto messageDto) {
        // todo
        var s = messageService.save(messageDto);

        simpMessagingTemplate.convertAndSend("/topic/messages/" + messageDto.getGroupId(), s);
    }

    @KafkaListener(topics = "group-messages-delete", groupId = "chat_group")
    public void consumeDelete(Long messageId) {
        messageService.delete(messageId);

        simpMessagingTemplate.convertAndSend("/topic/messages/" + 2, "delete: " + messageId);
    }
}