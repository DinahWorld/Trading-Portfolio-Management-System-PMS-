package com.training.pms.service;

import com.training.pms.dto.TradeRequest;
import com.training.pms.dto.TradeResponse;
import com.training.pms.mapper.TradeMapper;
import com.training.pms.model.domain.Trade;
import com.training.pms.repository.TradeRepository;
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