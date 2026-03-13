package com.training.pms.features.auth.dto.response;

import java.time.LocalDateTime;

public record UserResponse(
        String username,
        String email,
        LocalDateTime createdAt
) {
}