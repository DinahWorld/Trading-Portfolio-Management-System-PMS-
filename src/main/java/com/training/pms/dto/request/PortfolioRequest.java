package com.training.pms.dto.request;

import com.training.pms.model.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PortfolioRequest(
        @NotBlank String name,
        @NotNull Currency baseCurrency
) {
}