package org.backend.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.backend.common.exception.CustomException;
import org.backend.common.exception.ErrorCode;
import org.backend.dto.ChatMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaChatProducer {

    private static final String TOPIC = "chat";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(ChatMessage message) {
        try {
            String msg = objectMapper.writeValueAsString(message);
            kafkaTemplate.send(TOPIC, msg);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.KAFKA_JSON_PROCESSING_EXCEPTION);
        }
    }
}
