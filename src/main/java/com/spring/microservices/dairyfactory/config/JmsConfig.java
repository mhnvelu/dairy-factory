package com.spring.microservices.dairyfactory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String BUTTER_PRODUCE_QUEUE = "butter-produce-queue";
    public static final String NEW_INVENTORY_QUEUE = "new-inventory-queue";
    public static final String BUTTER_ORDER_VALIDATE_REQUEST_QUEUE = "validate-order-request-queue";
    public static final String BUTTER_ORDER_VALIDATE_RESPONSE_QUEUE = "validate-order-response-queue";

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }


}
