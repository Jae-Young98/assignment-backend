package org.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.backend.dto.response.NicknameCheckResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.socket.WebSocketSession;

@SpringBootTest(properties = "KAFKA_GROUP_ID=test")
class ChatRoomServiceTest {

    @Autowired
    private ChatRoomService chatRoomService;

    @Test
    void 사용_가능한_닉네임이라면_응답에_true가_포함된다() {
        // given
        String nickname = "available";

        // when
        NicknameCheckResponse response = chatRoomService.validateNickname(nickname);

        // then
        assertThat(response.nickname()).isEqualTo(nickname);
        assertThat(response.available()).isTrue();
    }

    @Test
    void 닉네임이_빈_값이라면_응답에_false가_포함된다() {
        // given
        String emptyNickname = "";

        // when
        NicknameCheckResponse response = chatRoomService.validateNickname(emptyNickname);

        // then
        assertThat(response.available()).isFalse();
    }

    @Test
    void 닉네임이_공백이라면_응답에_false가_포함된다() {
        // given
        String spaceNickname = " ";

        // when
        NicknameCheckResponse response = chatRoomService.validateNickname(spaceNickname);

        // then
        assertThat(response.available()).isFalse();
    }

    @Test
    void 닉네임이_null이라면_응답에_false가_포함된다() {
        // given
        String nullNickname = null;

        // when
        NicknameCheckResponse response = chatRoomService.validateNickname(nullNickname);

        // then
        assertThat(response.available()).isFalse();
    }

    @Test
    void 중복된_닉네임이라면_응답에_false가_포함된다() {
        // given
        String nickname = "duplicatedNickname";
        WebSocketSession session = mock(WebSocketSession.class);

        chatRoomService.register(nickname, session);

        // when
        NicknameCheckResponse response = chatRoomService.validateNickname(nickname);

        // then
        assertThat(response.available()).isFalse();
    }
}