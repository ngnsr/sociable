package com.rr.sociable.config;

import com.rr.sociable.dto.MessageDto;
import com.rr.sociable.dto.MessageUpdateDto;
import org.springframework.beans.factory.annotation.Value;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    public KafkaConfig(ProducerFactory<String, MessageDto> messageDtoProducerFactory,
                       ProducerFactory<String, Long> stringProducerFactory, ProducerFactory<String, MessageUpdateDto> messageUpdateDtoProducerFactory) {
        this.messageDtoProducerFactory = messageDtoProducerFactory;
        this.longProducerFactory = stringProducerFactory;
        this.messageUpdateDtoProducerFactory = messageUpdateDtoProducerFactory;
    }

    private final ProducerFactory<String, MessageDto> messageDtoProducerFactory;
    private final ProducerFactory<String, Long> longProducerFactory;
    private final ProducerFactory<String, MessageUpdateDto> messageUpdateDtoProducerFactory;


    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("group-messages-create", 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("group-messages-delete", 1, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic("group-messages-update", 1, (short) 1);
    }


    @Bean
    public KafkaTemplate<String, MessageDto> messageDtoKafkaTemplate() {
        return new KafkaTemplate<>(messageDtoProducerFactory);
    }

    @Bean
    public KafkaTemplate<String, Long> stringKafkaTemplate() {
        return new KafkaTemplate<>(longProducerFactory);
    }

    @Bean
    public KafkaTemplate<String, MessageUpdateDto> messageUpdateDtoKafkaTemplate() {
        return new KafkaTemplate<>(messageUpdateDtoProducerFactory);
    }
}