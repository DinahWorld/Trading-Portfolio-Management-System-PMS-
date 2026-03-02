package com.training.pms.portfolio.api;

import com.training.pms.model.domain.Instrument;
import com.training.pms.model.domain.Position;
import com.training.pms.model.enums.Currency;
import com.training.pms.portfolio.application.PortfolioService;
import com.training.pms.portfolio.dto.request.PortfolioRequest;
import com.training.pms.portfolio.dto.response.PortfolioResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioControllerTest {

    @Mock
    private PortfolioService portfolioService;

    @InjectMocks
    private PortfolioController portfolioController;

    @Test
    void should_create_portfolio_and_return_created_response() {
        PortfolioRequest request = new PortfolioRequest("Main", Currency.USD);
        Long userId = 1L;
        PortfolioResponse response = new PortfolioResponse("alice", "Main", Currency.USD, LocalDateTime.now());
        when(portfolioService.createPortfolio(request, userId)).thenReturn(response);

        ResponseEntity<PortfolioResponse> result = portfolioController.createPortfolio(request, userId);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(portfolioService).createPortfolio(request, userId);
    }

    @Test
    void should_return_positions_for_portfolio() {
        Long portfolioId = 1L;
        Instrument instrument = new Instrument();
        instrument.setIsin("US0378331005");
        Position position = new Position(instrument, new BigDecimal("5"), new BigDecimal("100"), new BigDecimal("10"));
        Set<Position> expected = Set.of(position);
        when(portfolioService.getPositions(portfolioId)).thenReturn(expected);

        Set<Position> result = portfolioController.getPositions(portfolioId);

        assertThat(result).isEqualTo(expected);
        verify(portfolioService).getPositions(portfolioId);
    }

    @Test
    void should_return_pnl_for_portfolio() {
        Long portfolioId = 1L;
        BigDecimal expected = new BigDecimal("123.45");
        when(portfolioService.getPnl(portfolioId)).thenReturn(expected);

        BigDecimal result = portfolioController.getPnl(portfolioId);

        assertThat(result).isEqualByComparingTo(expected);
        verify(portfolioService).getPnl(portfolioId);
    }
}
