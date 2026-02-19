package com.training.pms.model.domain;

import com.training.pms.model.enums.TradeSide;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "trades")
public class Trade {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    @Enumerated(EnumType.STRING)
    private TradeSide side;

    private BigDecimal quantity;

    private BigDecimal price;

    private LocalDateTime tradeDate;

    @PrePersist
    protected void onCreate() {
        tradeDate = LocalDateTime.now();
    }
}