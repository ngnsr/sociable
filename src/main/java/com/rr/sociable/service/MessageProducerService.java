package com.rr.sociable.service;

import com.rr.sociable.dto.MessageDto;
import com.rr.sociable.dto.MessageUpdateDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerService {

    private static final String CREATE_TOPIC = "group-messages-create";
    private static final String UPDATE_TOPIC = "group-messages-update";
    private static final String DELETE_TOPIC = "group-messages-delete";

    private final KafkaTemplate<String, Long> longKafkaTemplate;
    private final KafkaTemplate<String, MessageDto> messageDtoKafkaTemplate;
    private final KafkaTemplate<String, MessageUpdateDto> messageUpdateDtoKafkaTemplate;


    public MessageProducerService(KafkaTemplate<String, Long> kafkaTemplate, KafkaTemplate<String, MessageDto> messageDtoKafkaTemplate, KafkaTemplate<String, MessageUpdateDto> messageUpdateDtoKafkaTemplate) {
        this.longKafkaTemplate = kafkaTemplate;
        this.messageDtoKafkaTemplate = messageDtoKafkaTemplate;
        this.messageUpdateDtoKafkaTemplate = messageUpdateDtoKafkaTemplate;
    }

    public void sendMessage(MessageDto messageDto) {
        messageDtoKafkaTemplate.send(CREATE_TOPIC, messageDto);
    }

    public void deleteMessage(Long id) {
        longKafkaTemplate.send(DELETE_TOPIC, id);
    }

    public void updateMessage(MessageUpdateDto messageUpdateDto) {
        messageUpdateDtoKafkaTemplate.send(UPDATE_TOPIC, messageUpdateDto);
    }
}
