package org.backend.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaChatConsumer {

    @KafkaListener(topics = "chat", groupId = "chat-group")
    public void consume(String message) {
        log.info("consume message: {}", message);
    }
}
