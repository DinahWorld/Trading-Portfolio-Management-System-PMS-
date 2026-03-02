package com.training.pms.trade.mapper;

import com.training.pms.model.domain.Trade;
import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import com.training.pms.trade.dto.request.TradeRequest;
import com.training.pms.trade.dto.response.TradeResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class TradeMapperTest {

    private final TradeMapper mapper = Mappers.getMapper(TradeMapper.class);

    @Test
    void should_map_trade_request_to_entity() {
        TradeRequest request = new TradeRequest("Apple", "US0378331005", InstrumentType.EQUITY, Currency.USD);

        Trade result = mapper.toEntity(request);

        assertThat(result).isNotNull();
        assertThat(result.getInstrument()).isNull();
        assertThat(result.getSide()).isNull();
        assertThat(result.getQuantity()).isNull();
        assertThat(result.getPrice()).isNull();
    }

    @Test
    void should_map_trade_entity_to_response() {
        Trade trade = new Trade();
        trade.setTradeDate(LocalDateTime.now());

        TradeResponse result = mapper.toResponse(trade);

        assertThat(result).isNotNull();
        assertThat(result.name()).isNull();
        assertThat(result.isin()).isNull();
        assertThat(result.type()).isNull();
        assertThat(result.currency()).isNull();
        assertThat(result.createdAt()).isNull();
    }
}
