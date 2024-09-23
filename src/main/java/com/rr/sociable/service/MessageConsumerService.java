package com.rr.sociable.service;

import com.rr.sociable.dto.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageConsumerService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @KafkaListener(topics = "group-messages", groupId = "chat_group")
    public void consume(MessageDto messageDto) {
        System.err.println("consumer");
        Long groupId = messageDto.getGroupId();
        String messageContent = messageDto.getContent();

        simpMessagingTemplate.convertAndSend("/topic/messages/" + groupId, messageContent);
    }
}