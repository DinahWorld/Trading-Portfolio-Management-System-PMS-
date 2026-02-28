package com.training.pms.mapper;

import com.training.pms.dto.TradeRequest;
import com.training.pms.dto.TradeResponse;
import com.training.pms.model.domain.Trade;
import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-28T16:56:55+0100",
    comments = "version: 1.7.0.Beta1, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class TradeMapperImpl implements TradeMapper {

    @Override
    public Trade toEntity(TradeRequest tradeRequest) {
        if ( tradeRequest == null ) {
            return null;
        }

        Trade trade = new Trade();

        return trade;
    }

    @Override
    public TradeResponse toResponse(Trade trade) {
        if ( trade == null ) {
            return null;
        }

        String name = null;
        String isin = null;
        InstrumentType type = null;
        Currency currency = null;
        LocalDateTime createdAt = null;

        TradeResponse tradeResponse = new TradeResponse( name, isin, type, currency, createdAt );

        return tradeResponse;
    }
}
