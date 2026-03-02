package com.training.pms.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String username,
        @NotBlank String email,
        @NotBlank String password
) {
}