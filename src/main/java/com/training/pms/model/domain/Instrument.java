package com.training.pms.model.domain;

import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "instruments")
public class Instrument {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String isin;
    @Enumerated(EnumType.STRING)
    private InstrumentType type;
    private Currency currency;
}