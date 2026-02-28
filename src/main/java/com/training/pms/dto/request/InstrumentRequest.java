package com.training.pms.dto.request;

import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InstrumentRequest(
        @NotBlank String name,
        @NotBlank String isin,
        @NotNull InstrumentType type,
        @NotBlank Currency currency
) {
}