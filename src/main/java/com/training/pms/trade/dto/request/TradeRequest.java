package com.training.pms.trade.dto.request;

import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TradeRequest(
        @NotBlank String name,
        @NotBlank String isin,
        @NotNull InstrumentType type,
        @NotNull Currency currency
) {
}