package com.training.pms.portfolio.mapper;

import com.training.pms.model.domain.Portfolio;
import com.training.pms.model.domain.User;
import com.training.pms.model.enums.Currency;
import com.training.pms.portfolio.dto.request.PortfolioRequest;
import com.training.pms.portfolio.dto.response.PortfolioResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PortfolioMapperTest {

    private final PortfolioMapper mapper = Mappers.getMapper(PortfolioMapper.class);

    @Test
    void should_map_portfolio_entity_to_response() {
        User user = new User();
        user.setUsername("alice");

        Portfolio portfolio = new Portfolio();
        portfolio.setName("Main");
        portfolio.setBaseCurrency(Currency.USD);
        portfolio.setCreatedAt(LocalDateTime.now());
        portfolio.setUser(user);

        PortfolioResponse result = mapper.toResponse(portfolio);

        assertThat(result.username()).isEqualTo("alice");
        assertThat(result.name()).isEqualTo("Main");
        assertThat(result.baseCurrency()).isEqualTo(Currency.USD);
        assertThat(result.createdAt()).isEqualTo(portfolio.getCreatedAt());
    }

    @Test
    void should_map_portfolio_request_to_entity() {
        PortfolioRequest request = new PortfolioRequest("Main", Currency.USD);

        Portfolio result = mapper.toEntity(request, 42L);

        assertThat(result.getName()).isEqualTo("Main");
        assertThat(result.getBaseCurrency()).isEqualTo(Currency.USD);
    }
}
