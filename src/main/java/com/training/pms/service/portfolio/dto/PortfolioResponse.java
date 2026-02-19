package com.training.pms.service.portfolio.dto;

import com.training.pms.model.enums.Currency;

import java.time.LocalDateTime;

public record PortfolioResponse(
        String username,
        String name,
        Currency baseCurrency,
        LocalDateTime createdAt
) {
}