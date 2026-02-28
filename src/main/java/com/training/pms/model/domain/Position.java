package com.training.pms.model.domain;

import java.math.BigDecimal;

public record Position(
        Instrument instrument,
        BigDecimal quantity,
        BigDecimal averagePrice,
        BigDecimal unrealizedPnl
) {
}