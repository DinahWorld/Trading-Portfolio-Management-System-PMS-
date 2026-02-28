package com.training.pms.dto;

import java.time.LocalDateTime;

public record UserResponse(
        String username,
        String email,
        LocalDateTime createdAt
) {
}