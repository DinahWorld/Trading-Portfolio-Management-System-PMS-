package com.training.pms.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class Position {
    private Instrument instrument;
    private BigDecimal quantity;
    private BigDecimal averagePrice;
    private BigDecimal unrealizedPnl;
}