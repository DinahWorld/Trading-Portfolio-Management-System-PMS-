package com.training.pms.portfolio.mapper;

import com.training.pms.model.domain.Portfolio;
import com.training.pms.portfolio.dto.request.PortfolioRequest;
import com.training.pms.portfolio.dto.response.PortfolioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {
    @Mapping(target = "username", source = "user.username")
    PortfolioResponse toResponse(Portfolio portfolio);

    Portfolio toEntity(PortfolioRequest portfolioRequest, Long id);
}