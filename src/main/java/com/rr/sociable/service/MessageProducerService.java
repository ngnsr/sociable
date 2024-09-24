package com.rr.sociable.service;

import com.rr.sociable.dto.MessageDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerService {

    private static final String TOPIC = "group-messages";
    private static final String DELETE_TOPIC = "group-messages-delete";

    private final KafkaTemplate<String, MessageDto> messageDtoKafkaTemplate;
    private final KafkaTemplate<String, Long> idKafkaTemplate;


    public MessageProducerService(KafkaTemplate<String, MessageDto> kafkaTemplate, KafkaTemplate<String, Long> idKafkaTemplate) {
        this.messageDtoKafkaTemplate = kafkaTemplate;
        this.idKafkaTemplate = idKafkaTemplate;
    }

    public void sendMessage(MessageDto messageDto) {
        messageDtoKafkaTemplate.send(TOPIC, messageDto);
    }

    public void deleteMessage(Long id) {
        idKafkaTemplate.send(DELETE_TOPIC, id);
    }
}
