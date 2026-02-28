package com.training.pms.service;

import com.training.pms.dto.request.PortfolioRequest;
import com.training.pms.dto.response.PortfolioResponse;
import com.training.pms.mapper.PortfolioMapper;
import com.training.pms.model.domain.Portfolio;
import com.training.pms.model.domain.Position;
import com.training.pms.model.domain.Trade;
import com.training.pms.repository.PortfolioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PortfolioService {
    private final PortfolioRepository repository;
    private final PortfolioMapper mapper;
    private final PortfolioCalculator calculator;

    public PortfolioService(PortfolioRepository repository, PortfolioMapper mapper, PortfolioCalculator calculator) {
        this.repository = repository;
        this.mapper = mapper;
        this.calculator = calculator;
    }

    @Transactional
    public PortfolioResponse createPortfolio(PortfolioRequest portfolioRequest, Long userId) {
        var ptf = mapper.toEntity(portfolioRequest, userId);
        var savedPtf = repository.save(ptf);
        return mapper.toResponse(savedPtf);
    }

    @Transactional(readOnly = true)
    public Set<Position> getPositions(Long userId) {
        var ptf = getPortfolio(userId);
        var tradesByInstrument = ptf.getTrades().stream()
                .collect(Collectors.groupingBy(Trade::getInstrument, Collectors.toSet()));

        return tradesByInstrument.entrySet().stream()
                .map(entry -> calculator.calculatePosition(entry.getKey(), entry.getValue()))
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public BigDecimal getPnl(Long userId) {
        var ptf = getPortfolio(userId);
        return ptf.getTrades().stream()
                .collect(Collectors.groupingBy(Trade::getInstrument, Collectors.toSet()))
                .entrySet()
                .stream()
                .map(entry -> calculator.calculatePosition(entry.getKey(), entry.getValue()).unrealizedPnl())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private Portfolio getPortfolio(Long userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Portfolio not found"));
    }


}