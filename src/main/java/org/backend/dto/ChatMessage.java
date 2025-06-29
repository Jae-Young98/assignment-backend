package org.backend.dto;

import org.backend.constant.ChatType;

public record ChatMessage(ChatType type, String nickname, String message, String timestamp) {

}
