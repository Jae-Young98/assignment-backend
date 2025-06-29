package org.backend.controller;

import lombok.RequiredArgsConstructor;
import org.backend.dto.BaseResponse;
import org.backend.dto.response.NicknameCheckResponse;
import org.backend.service.ChatRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/nicknames/{nickname}/availability")
    public ResponseEntity<BaseResponse<NicknameCheckResponse>> checkNicknameAvailability(@PathVariable String nickname) {
        NicknameCheckResponse response = chatRoomService.validateNickname(nickname);
        return ResponseEntity.ok(new BaseResponse<>(response));
    }
}
