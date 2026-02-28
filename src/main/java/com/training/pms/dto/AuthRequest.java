package com.training.pms.dto;

public record AuthRequest(
        String username,
        String password
) {
}
