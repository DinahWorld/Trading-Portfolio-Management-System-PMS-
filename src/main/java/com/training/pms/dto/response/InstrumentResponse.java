package com.training.pms.dto.response;

import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;

import java.time.LocalDateTime;

public record InstrumentResponse(
        String name,
        String isin,
        InstrumentType type,
        Currency currency,
        LocalDateTime createdAt
) {
}