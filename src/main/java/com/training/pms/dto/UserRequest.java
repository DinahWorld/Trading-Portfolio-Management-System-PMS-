package com.training.pms.dto;

public record UserRequest(
        String username,
        String email,
        String password
) {
}