package com.training.pms.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiError(
        HttpStatus code,
        String message,
        String path,
        LocalDateTime timestamp
) {
}