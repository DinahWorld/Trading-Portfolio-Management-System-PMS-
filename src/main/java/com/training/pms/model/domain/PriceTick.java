package com.training.pms.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceTick(
        String instrumentIsin,
        BigDecimal price,
        LocalDateTime timestamp
) {
}