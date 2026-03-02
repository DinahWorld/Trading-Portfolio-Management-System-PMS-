package com.training.pms.trade.application;

import com.training.pms.model.domain.Trade;
import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import com.training.pms.trade.dto.request.TradeRequest;
import com.training.pms.trade.dto.response.TradeResponse;
import com.training.pms.trade.mapper.TradeMapper;
import com.training.pms.trade.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository repository;

    @Mock
    private TradeMapper mapper;

    @InjectMocks
    private TradeService service;

    @Test
    void should_create_trade_and_return_response() {
        TradeRequest request = new TradeRequest(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD
        );

        Trade trade = new Trade();
        TradeResponse response = new TradeResponse(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD,
                LocalDateTime.now()
        );

        when(mapper.toEntity(request)).thenReturn(trade);
        when(repository.save(trade)).thenReturn(trade);
        when(mapper.toResponse(trade)).thenReturn(response);

        TradeResponse result = service.createTrade(request);

        assertThat(result).isSameAs(response);
        verify(mapper).toEntity(request);
        verify(repository).save(trade);
        verify(mapper).toResponse(trade);
    }

    @Test
    void should_map_response_from_saved_trade() {
        TradeRequest request = new TradeRequest(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD
        );

        Trade toSave = new Trade();
        Trade saved = new Trade();
        saved.setId(10L);

        TradeResponse expected = new TradeResponse(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD,
                LocalDateTime.now()
        );

        when(mapper.toEntity(request)).thenReturn(toSave);
        when(repository.save(toSave)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(expected);

        TradeResponse result = service.createTrade(request);

        assertThat(result).isEqualTo(expected);
        verify(mapper).toResponse(saved);
    }

    @Test
    void should_propagate_exception_when_mapper_to_entity_fails() {
        TradeRequest request = new TradeRequest(
                "Bad",
                "BAD",
                InstrumentType.EQUITY,
                Currency.USD
        );
        RuntimeException failure = new RuntimeException("mapping failed");
        when(mapper.toEntity(request)).thenThrow(failure);

        assertThatThrownBy(() -> service.createTrade(request)).isSameAs(failure);

        verifyNoInteractions(repository);
    }

    @Test
    void should_propagate_exception_when_repository_save_fails() {
        TradeRequest request = new TradeRequest(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD
        );
        Trade toSave = new Trade();
        RuntimeException failure = new RuntimeException("db unavailable");

        when(mapper.toEntity(request)).thenReturn(toSave);
        when(repository.save(toSave)).thenThrow(failure);

        assertThatThrownBy(() -> service.createTrade(request)).isSameAs(failure);

        verify(mapper, never()).toResponse(toSave);
    }

    @Test
    void should_propagate_exception_when_mapper_to_response_fails() {
        TradeRequest request = new TradeRequest(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD
        );
        Trade trade = new Trade();
        RuntimeException failure = new RuntimeException("response mapping failed");

        when(mapper.toEntity(request)).thenReturn(trade);
        when(repository.save(trade)).thenReturn(trade);
        when(mapper.toResponse(trade)).thenThrow(failure);

        assertThatThrownBy(() -> service.createTrade(request)).isSameAs(failure);

        verify(repository).save(trade);
    }
}