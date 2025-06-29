package org.backend.service;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;

@Service
public class ChatRoomService {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void register(String nickname, WebSocketSession session) {
        sessions.put(nickname, session);
    }

    public void unregister(String nickname) {
        sessions.remove(nickname);
    }

    public ConcurrentHashMap<String, WebSocketSession> getAllSessions() {
        return sessions;
    }
}
