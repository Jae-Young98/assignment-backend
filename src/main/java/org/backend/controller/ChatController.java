package org.backend.controller;

import lombok.RequiredArgsConstructor;
import org.backend.adapter.KafkaChatProducer;
import org.backend.dto.ChatMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final KafkaChatProducer kafkaChatProducer;

    @PostMapping("/chat")
    public ResponseEntity<Void> sendMessage(@RequestBody ChatMessage chatMessage) {
        kafkaChatProducer.send(chatMessage);
        return ResponseEntity.ok().build();
    }
}
