package com.training.pms.service.portfolio.mapper;

import com.training.pms.model.domain.Portfolio;
import com.training.pms.service.portfolio.dto.PortfolioRequest;
import com.training.pms.service.portfolio.dto.PortfolioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PortfolioMapper {
    @Mapping(target = "username", source = "user.username")
    PortfolioResponse toResponse(Portfolio portfolio);

    Portfolio toEntity(PortfolioRequest portfolioRequest, Long id);
}