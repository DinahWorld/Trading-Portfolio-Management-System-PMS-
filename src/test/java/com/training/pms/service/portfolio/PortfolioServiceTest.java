package com.training.pms.service.portfolio;

import com.training.pms.model.domain.Portfolio;
import com.training.pms.model.enums.Currency;
import com.training.pms.repository.PortfolioRepository;
import com.training.pms.service.portfolio.dto.PortfolioRequest;
import com.training.pms.service.portfolio.dto.PortfolioResponse;
import com.training.pms.service.portfolio.mapper.PortfolioMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    private PortfolioRepository repository;

    @Mock
    private PortfolioMapper mapper;

    @InjectMocks
    private PortfolioService portfolioService;

    @Test
    void should_create_portfolio_and_return_response() {
        // GIVEN
        PortfolioRequest request = new PortfolioRequest("User", Currency.HKD);
        Long userId = 1L;

        Portfolio portfolio = Portfolio.builder()
                .name("User").build();
        PortfolioResponse response = new PortfolioResponse("User", "Userr", Currency.HKD, portfolio.getCreatedAt());

        when(mapper.mapToPortfolioObject(request, userId)).thenReturn(portfolio);
        when(mapper.mapToPortfolioResponse(portfolio)).thenReturn(response);

        // WHEN
        PortfolioResponse result = portfolioService.createPortfolio(request, userId);

        // THEN
        verify(mapper).mapToPortfolioObject(request, userId);
        verify(repository).save(portfolio);
        verify(mapper).mapToPortfolioResponse(portfolio);

        assertThat(result).isSameAs(response);
    }
}