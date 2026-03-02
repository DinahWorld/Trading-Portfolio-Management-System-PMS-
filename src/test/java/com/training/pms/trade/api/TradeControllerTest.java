package com.training.pms.trade.api;

import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import com.training.pms.trade.application.TradeService;
import com.training.pms.trade.dto.request.TradeRequest;
import com.training.pms.trade.dto.response.TradeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeControllerTest {

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeController tradeController;

    @Test
    void should_create_trade_and_return_created_response() {
        TradeRequest request = new TradeRequest("Apple", "US0378331005", InstrumentType.EQUITY, Currency.USD);
        TradeResponse response = new TradeResponse(
                "Apple",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD,
                LocalDateTime.now()
        );
        when(tradeService.createTrade(request)).thenReturn(response);

        ResponseEntity<TradeResponse> result = tradeController.createTrade(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(tradeService).createTrade(request);
    }
}
