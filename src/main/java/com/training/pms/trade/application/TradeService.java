package com.training.pms.trade.application;

import com.training.pms.model.domain.Trade;
import com.training.pms.trade.dto.request.TradeRequest;
import com.training.pms.trade.dto.response.TradeResponse;
import com.training.pms.trade.mapper.TradeMapper;
import com.training.pms.trade.repository.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TradeService {
    private final TradeRepository repository;
    private final TradeMapper mapper;

    public TradeService(TradeRepository repository, TradeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public TradeResponse createTrade(TradeRequest tradeRequest) {
        Trade trade = mapper.toEntity(tradeRequest);
        Trade savedTrade = repository.save(trade);
        return mapper.toResponse(savedTrade);
    }
}