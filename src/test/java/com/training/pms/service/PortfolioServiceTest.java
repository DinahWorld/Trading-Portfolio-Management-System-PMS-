package com.training.pms.service;

import com.training.pms.dto.PortfolioRequest;
import com.training.pms.dto.PortfolioResponse;
import com.training.pms.mapper.PortfolioMapper;
import com.training.pms.model.domain.Instrument;
import com.training.pms.model.domain.Portfolio;
import com.training.pms.model.domain.Position;
import com.training.pms.model.domain.Trade;
import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.TradeSide;
import com.training.pms.repository.PortfolioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    private PortfolioRepository repository;

    @Mock
    private PortfolioMapper mapper;

    @Mock
    private PortfolioCalculator calculator;

    @InjectMocks
    private PortfolioService portfolioService;

    @Test
    void should_create_portfolio_and_return_response() {
        PortfolioRequest request = new PortfolioRequest("User", Currency.HKD);
        Long userId = 1L;

        Portfolio portfolio = Portfolio.builder()
                .name("User").build();
        Portfolio savedPortfolio = Portfolio.builder()
                .name("User")
                .build();

        PortfolioResponse response = new PortfolioResponse(
                "User",
                "Userr",
                Currency.HKD,
                LocalDateTime.now()
        );

        when(mapper.toEntity(request, userId)).thenReturn(portfolio);
        when(repository.save(portfolio)).thenReturn(savedPortfolio);
        when(mapper.toResponse(savedPortfolio)).thenReturn(response);

        PortfolioResponse result = portfolioService.createPortfolio(request, userId);

        verify(mapper).toEntity(request, userId);
        verify(repository).save(portfolio);
        verify(mapper).toResponse(savedPortfolio);
        assertThat(result).isSameAs(response);
    }

    @Test
    void should_return_positions_for_each_instrument() {
        Long userId = 10L;
        Instrument apple = instrument("US0378331005", "Apple Inc.");
        Instrument tesla = instrument("US88160R1014", "Tesla Inc.");

        Trade buyApple10 = trade(apple, TradeSide.BUY, "10", "100");
        Trade buyApple5 = trade(apple, TradeSide.BUY, "5", "110");
        Trade sellApple4 = trade(apple, TradeSide.SELL, "4", "120");
        Trade sellTesla3 = trade(tesla, TradeSide.SELL, "3", "50");

        Portfolio portfolio = Portfolio.builder()
                .name("Growth")
                .trades(Set.of(buyApple10, buyApple5, sellApple4, sellTesla3))
                .build();

        when(repository.findByUserId(userId)).thenReturn(Optional.of(portfolio));
        when(calculator.calculatePosition(any(Instrument.class), any(Set.class)))
                .thenAnswer(invocation -> {
                    Instrument instrument = invocation.getArgument(0);
                    if ("US0378331005".equals(instrument.getIsin())) {
                        return new Position(instrument, new BigDecimal("11"), new BigDecimal("103.33333333"), new BigDecimal("-794.19333363"));
                    }
                    return new Position(instrument, new BigDecimal("-3"), BigDecimal.ZERO, new BigDecimal("-93.402"));
                });

        Set<Position> result = portfolioService.getPositions(userId);

        assertThat(result).hasSize(2);

        Position applePosition = result.stream()
                .filter(position -> "US0378331005".equals(position.instrument().getIsin()))
                .findFirst()
                .orElseThrow();

        Position teslaPosition = result.stream()
                .filter(position -> "US88160R1014".equals(position.instrument().getIsin()))
                .findFirst()
                .orElseThrow();

        assertThat(applePosition.quantity()).isEqualByComparingTo(new BigDecimal("11"));
        assertThat(applePosition.averagePrice()).isEqualByComparingTo(new BigDecimal("103.33333333"));
        assertThat(applePosition.unrealizedPnl()).isEqualByComparingTo(new BigDecimal("-794.19333363"));

        assertThat(teslaPosition.quantity()).isEqualByComparingTo(new BigDecimal("-3"));
        assertThat(teslaPosition.averagePrice()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(teslaPosition.unrealizedPnl()).isEqualByComparingTo(new BigDecimal("-93.402"));
    }

    @Test
    void should_throw_not_found_when_portfolio_does_not_exist() {
        Long userId = 404L;
        when(repository.findByUserId(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> portfolioService.getPositions(userId))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> {
                    ResponseStatusException error = (ResponseStatusException) ex;
                    assertThat(error.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                    assertThat(error.getReason()).isEqualTo("Portfolio not found");
                });
    }

    @Test
    void should_return_total_unrealized_pnl_as_sum_of_positions() {
        Long userId = 77L;
        Instrument apple = instrument("US0378331005", "Apple Inc.");
        Instrument tesla = instrument("US88160R1014", "Tesla Inc.");

        Trade buyApple10 = trade(apple, TradeSide.BUY, "10", "100");
        Trade sellApple4 = trade(apple, TradeSide.SELL, "4", "120");
        Trade buyTesla2 = trade(tesla, TradeSide.BUY, "2", "50");

        Portfolio portfolio = Portfolio.builder()
                .name("Growth")
                .trades(Set.of(buyApple10, sellApple4, buyTesla2))
                .build();

        when(repository.findByUserId(userId)).thenReturn(Optional.of(portfolio));
        when(calculator.calculatePosition(any(Instrument.class), any(Set.class)))
                .thenAnswer(invocation -> {
                    Instrument instrument = invocation.getArgument(0);
                    if ("US0378331005".equals(instrument.getIsin())) {
                        return new Position(instrument, new BigDecimal("6"), new BigDecimal("100"), new BigDecimal("-413.196"));
                    }
                    return new Position(instrument, new BigDecimal("2"), new BigDecimal("50"), new BigDecimal("-37.732"));
                });

        BigDecimal pnl = portfolioService.getPnl(userId);

        BigDecimal expectedTotalPnl = new BigDecimal("-450.928");

        assertThat(pnl).isEqualByComparingTo(expectedTotalPnl);
    }

    private Instrument instrument(String isin, String name) {
        Instrument instrument = new Instrument();
        instrument.setIsin(isin);
        instrument.setName(name);
        return instrument;
    }

    private Trade trade(Instrument instrument, TradeSide side, String quantity, String price) {
        Trade trade = new Trade();
        trade.setInstrument(instrument);
        trade.setSide(side);
        trade.setQuantity(new BigDecimal(quantity));
        trade.setPrice(new BigDecimal(price));
        return trade;
    }
}
