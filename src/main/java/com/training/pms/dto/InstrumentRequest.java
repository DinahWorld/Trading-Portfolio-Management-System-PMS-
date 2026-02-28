package com.training.pms.dto;

import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;

public record InstrumentRequest(
        String name,
        String isin,
        InstrumentType type,
        Currency currency
) {
}