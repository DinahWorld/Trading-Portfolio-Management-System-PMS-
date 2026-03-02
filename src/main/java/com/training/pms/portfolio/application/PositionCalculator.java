package com.training.pms.portfolio.application;

import com.training.pms.model.domain.Instrument;
import com.training.pms.model.domain.Position;
import com.training.pms.model.domain.Trade;
import com.training.pms.model.enums.TradeSide;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;


@Service
public class PositionCalculator {
    private static final BigDecimal MARKET_PRICE = BigDecimal.valueOf(31.134);

    public Position calculatePosition(Instrument instrument, Set<Trade> trades) {
        var buyTrades = totalTrade(trades, TradeSide.BUY);
        var sellTrades = totalTrade(trades, TradeSide.SELL);
        var qty = buyTrades.subtract(sellTrades);

        var avgPrice = avgTradePrice(trades, buyTrades);
        var unrealizedPnl = (MARKET_PRICE.subtract(avgPrice)).multiply(qty);

        return new Position(instrument, qty, avgPrice, unrealizedPnl);
    }


    private BigDecimal totalTrade(Set<Trade> trades, TradeSide tradeSide) {
        return trades.stream()
                .filter(trade -> trade.getSide() == tradeSide)
                .map(Trade::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal avgTradePrice(Set<Trade> trades, BigDecimal sumBuyTrades) {
        if (sumBuyTrades == null || sumBuyTrades.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal totalBuyNotional = trades.stream()
                .filter(trade -> trade.getSide() == TradeSide.BUY)
                .map(trade -> trade.getQuantity().multiply(trade.getPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalBuyNotional.divide(sumBuyTrades, 8, RoundingMode.HALF_UP);
    }
}