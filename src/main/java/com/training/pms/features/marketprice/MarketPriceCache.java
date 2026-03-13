package com.training.pms.features.marketprice;

import com.training.pms.model.domain.PriceTick;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MarketPriceCache {

    private final ConcurrentHashMap<String, PriceTick> lastPrices = new ConcurrentHashMap<>();

    public void updatePrice(PriceTick tick) {
        lastPrices.put(tick.instrumentIsin(), tick);
    }

    public Optional<PriceTick> getLastPrice(String isin) {
        return Optional.ofNullable(lastPrices.get(isin));
    }
}