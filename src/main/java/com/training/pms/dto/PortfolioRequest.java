package com.training.pms.dto;

import com.training.pms.model.enums.Currency;

public record PortfolioRequest(
        String name,
        Currency baseCurrency
) {
}