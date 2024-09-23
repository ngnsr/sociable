package com.rr.sociable.service;

import com.rr.sociable.dto.MessageDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerService {

    private static final String TOPIC = "group-messages";
    private final KafkaTemplate<String, MessageDto> kafkaTemplate;

    public MessageProducerService(KafkaTemplate<String, MessageDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageDto messageDto) {
        System.err.println("producer");
        kafkaTemplate.send(TOPIC, messageDto);
    }
}
