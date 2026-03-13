package com.training.pms.features.pricing;

import com.training.pms.model.domain.PriceTick;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PriceFeedService {
    private final ExecutorService executor = Executors.newFixedThreadPool(8);
    private final BlockingQueue<PriceTick> queue;

    public PriceFeedService(BlockingQueue<PriceTick> queue) {
        this.queue = queue;
    }

    public void startFeeds(Set<String> isins) {
        isins.forEach(isin -> executor.execute(() -> runFeed(isin)));
    }

    private void runFeed(String isin) {
        BigDecimal price = BigDecimal.valueOf(100.0);

        try {
            while (!Thread.currentThread().isInterrupted()) {
                double variation = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
                price = price.add(BigDecimal.valueOf(variation)).max(BigDecimal.ONE);

                PriceTick tick = new PriceTick(
                        isin,
                        price,
                        LocalDateTime.now()
                );

                queue.put(tick);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}