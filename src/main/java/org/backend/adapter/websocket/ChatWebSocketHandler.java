package org.backend.adapter.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.backend.adapter.KafkaChatProducer;
import org.backend.constant.ChatType;
import org.backend.dto.ChatMessage;
import org.backend.service.ChatRoomService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    private final ObjectMapper objectMapper;
    private final KafkaChatProducer kafkaChatProducer;
    private final ChatRoomService chatRoomService;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.receive()
                .flatMap(message -> {
                    try {
                        String payload = message.getPayloadAsText();
                        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
                        String nickname = chatMessage.nickname();
                        ChatType type = chatMessage.type();

                        switch (type) {
                            case JOIN: {
                                session.getAttributes().put("nickname", nickname);
                                chatRoomService.register(nickname, session);
                                ChatMessage joinNotice = new ChatMessage(
                                        ChatType.JOIN,
                                        nickname,
                                        nickname + "님이 입장하셨습니다.",
                                        LocalDateTime.now().toString()
                                );

                                kafkaChatProducer.send(joinNotice);
                                return session.send(Mono.just(session.textMessage("JOIN_SUCCESS")));
                            }
                            case CHAT: {
                                ChatMessage chat = new ChatMessage(
                                        ChatType.CHAT,
                                        nickname,
                                        chatMessage.message(),
                                        LocalDateTime.now().toString()
                                );
                                kafkaChatProducer.send(chat);
                                return Mono.empty();
                            }
                            default: {
                                return session.send(Mono.just(session.textMessage("UNSUPPORTED_TYPE")));
                            }
                        }
                    } catch (Exception e) {
                        return session.send(Mono.just(session.textMessage("UNSUPPORTED_TYPE")));
                    }
                })
                .doFinally(signal -> {
                    String nickname = (String) session.getAttributes().get("nickname");
                    chatRoomService.unregister(nickname);
                    ChatMessage leaveNotice = new ChatMessage(
                            ChatType.LEAVE,
                            nickname,
                            nickname + "님이 퇴장하셨습니다.",
                            LocalDateTime.now().toString()
                    );
                    kafkaChatProducer.send(leaveNotice);
                })
                .then();
    }
}
