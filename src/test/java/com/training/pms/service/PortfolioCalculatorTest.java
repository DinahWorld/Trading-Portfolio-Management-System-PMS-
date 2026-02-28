package com.training.pms.service;

import com.training.pms.model.domain.Instrument;
import com.training.pms.model.domain.Position;
import com.training.pms.model.domain.Trade;
import com.training.pms.model.enums.TradeSide;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PortfolioCalculatorTest {

    private final PortfolioCalculator calculator = new PortfolioCalculator();

    @Test
    void should_calculate_position_from_buy_and_sell_trades() {
        Instrument instrument = instrument("US0378331005", "Apple Inc.");
        Trade buy10 = trade(instrument, TradeSide.BUY, "10", "100");
        Trade buy5 = trade(instrument, TradeSide.BUY, "5", "110");
        Trade sell4 = trade(instrument, TradeSide.SELL, "4", "120");

        Position result = calculator.calculatePosition(instrument, Set.of(buy10, buy5, sell4));

        assertThat(result.instrument()).isEqualTo(instrument);
        assertThat(result.quantity()).isEqualByComparingTo(new BigDecimal("11"));
        assertThat(result.averagePrice()).isEqualByComparingTo(new BigDecimal("103.33333333"));
        assertThat(result.unrealizedPnl()).isEqualByComparingTo(new BigDecimal("-794.19266663"));
    }

    @Test
    void should_return_zero_average_price_when_no_buy_trade() {
        Instrument instrument = instrument("US88160R1014", "Tesla Inc.");
        Trade sell3 = trade(instrument, TradeSide.SELL, "3", "50");

        Position result = calculator.calculatePosition(instrument, Set.of(sell3));

        assertThat(result.quantity()).isEqualByComparingTo(new BigDecimal("-3"));
        assertThat(result.averagePrice()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(result.unrealizedPnl()).isEqualByComparingTo(new BigDecimal("-93.402"));
    }

    private Instrument instrument(String isin, String name) {
        Instrument instrument = new Instrument();
        instrument.setIsin(isin);
        instrument.setName(name);
        return instrument;
    }

    private Trade trade(Instrument instrument, TradeSide side, String quantity, String price) {
        Trade trade = new Trade();
        trade.setInstrument(instrument);
        trade.setSide(side);
        trade.setQuantity(new BigDecimal(quantity));
        trade.setPrice(new BigDecimal(price));
        return trade;
    }
}
