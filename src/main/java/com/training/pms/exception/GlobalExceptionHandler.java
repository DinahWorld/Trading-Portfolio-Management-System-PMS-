package com.training.pms.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
@NoArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest rq) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), rq.getRequestURI(), LocalDateTime.now()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AuthUnauthorizedException.class)
    public ResponseEntity<ApiError> handleAuthUnauthorizedException(AuthUnauthorizedException ex, HttpServletRequest rq) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(
                new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage(), rq.getRequestURI(), LocalDateTime.now()),
                HttpStatus.UNAUTHORIZED
        );
    }
}