package org.backend.dto.response;

public record NicknameCheckResponse(String nickname, String message, boolean available) {

}
