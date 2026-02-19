package com.training.pms.service.portfolio;

import com.training.pms.model.domain.Portfolio;
import com.training.pms.repository.PortfolioRepository;
import com.training.pms.service.portfolio.dto.PortfolioRequest;
import com.training.pms.service.portfolio.dto.PortfolioResponse;
import com.training.pms.service.portfolio.mapper.PortfolioMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PortfolioService {
    private final PortfolioRepository repository;
    private final PortfolioMapper mapper;

    public PortfolioService(PortfolioRepository repository, PortfolioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public PortfolioResponse createPortfolio(PortfolioRequest portfolioRequest, Long userId) {
        Portfolio ptf = mapper.toEntity(portfolioRequest, userId);
        Portfolio savedPtf = repository.save(ptf);
        return mapper.toResponse(savedPtf);
    }

}