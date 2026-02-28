package com.training.pms.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
        String username,
        String email,
        LocalDateTime createdAt
) {
}