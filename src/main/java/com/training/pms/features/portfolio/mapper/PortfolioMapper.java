package com.training.pms.features.portfolio.mapper;

import com.training.pms.features.portfolio.dto.request.PortfolioRequest;
import com.training.pms.features.portfolio.dto.response.PortfolioResponse;
import com.training.pms.model.domain.Portfolio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {
    @Mapping(target = "username", source = "user.username")
    PortfolioResponse toResponse(Portfolio portfolio);

    Portfolio toEntity(PortfolioRequest portfolioRequest, Long id);
}