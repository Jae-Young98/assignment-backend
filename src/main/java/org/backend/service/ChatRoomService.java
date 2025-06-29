package org.backend.service;

import java.util.concurrent.ConcurrentHashMap;
import org.backend.dto.response.NicknameCheckResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketSession;

@Service
public class ChatRoomService {

    private static final String AVAILABLE_NICKNAME = "사용 가능한 닉네임이에요";
    private static final String UNAVAILABLE_NICKNAME = "사용할 수 없는 닉네임이에요";

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

    /**
     * 닉네임을 검증한다.
     * @param nickname 닉네임
     * @return 닉네임, 메시지, 사용 가능 여부
     */
    public NicknameCheckResponse validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            return new NicknameCheckResponse(nickname, UNAVAILABLE_NICKNAME, false);
        }

        if (sessions.containsKey(nickname)) {
            return new NicknameCheckResponse(nickname, UNAVAILABLE_NICKNAME, false);
        }

        return new NicknameCheckResponse(nickname, AVAILABLE_NICKNAME, true);
    }
}
