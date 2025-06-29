package org.backend.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.common.exception.CustomException;
import org.backend.common.exception.ErrorCode;
import org.backend.dto.ChatMessage;
import org.backend.service.ChatRoomService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaChatConsumer {

    private final ObjectMapper objectMapper;
    private final ChatRoomService chatRoomService;

    @KafkaListener(topics = "chat", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
            String json = objectMapper.writeValueAsString(chatMessage);

            for (WebSocketSession session : chatRoomService.getAllSessions().values()) {
                log.info("Send to {}, Message: {}", session.getAttributes().get("nickname"), json);
                session.send(Mono.just(session.textMessage(json))).subscribe();
            }
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.KAFKA_JSON_PROCESSING_EXCEPTION);
        }
    }
}
