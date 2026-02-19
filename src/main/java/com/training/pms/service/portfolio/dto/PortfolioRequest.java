package com.training.pms.service.portfolio.dto;

import com.training.pms.model.enums.Currency;

public record PortfolioRequest(
        String name,
        Currency baseCurrency
) {
}