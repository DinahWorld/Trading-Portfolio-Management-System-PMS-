package com.training.pms.instrument.api;

import com.training.pms.instrument.application.InstrumentService;
import com.training.pms.instrument.dto.request.InstrumentRequest;
import com.training.pms.instrument.dto.response.InstrumentResponse;
import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
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
class InstrumentControllerTest {

    @Mock
    private InstrumentService instrumentService;

    @InjectMocks
    private InstrumentController instrumentController;

    @Test
    void should_create_instrument_and_return_created_response() {
        InstrumentRequest request = new InstrumentRequest("Apple", "US0378331005", InstrumentType.EQUITY, Currency.USD);
        InstrumentResponse response = new InstrumentResponse(
                "Apple",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD,
                LocalDateTime.now()
        );
        when(instrumentService.createInstrument(request)).thenReturn(response);

        ResponseEntity<InstrumentResponse> result = instrumentController.createInstrument(request);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);
        verify(instrumentService).createInstrument(request);
    }
}
