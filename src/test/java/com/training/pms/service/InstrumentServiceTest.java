package com.training.pms.service;

import com.training.pms.dto.request.InstrumentRequest;
import com.training.pms.dto.response.InstrumentResponse;
import com.training.pms.mapper.InstrumentMapper;
import com.training.pms.model.domain.Instrument;
import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import com.training.pms.repository.InstrumentRepository;
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
class InstrumentServiceTest {

    @Mock
    private InstrumentRepository repository;

    @Mock
    private InstrumentMapper mapper;

    @InjectMocks
    private InstrumentService service;

    @Test
    void should_create_instrument_and_return_response() {
        // GIVEN
        InstrumentRequest request = new InstrumentRequest(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD
        );

        Instrument instrument = new Instrument();
        instrument.setName("Apple Inc.");
        instrument.setIsin("US0378331005");
        instrument.setType(InstrumentType.EQUITY);
        instrument.setCurrency(Currency.USD);

        InstrumentResponse response = new InstrumentResponse(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD,
                LocalDateTime.now()
        );

        when(mapper.toEntity(request)).thenReturn(instrument);
        when(repository.save(instrument)).thenReturn(instrument);
        when(mapper.toResponse(instrument)).thenReturn(response);

        // WHEN
        InstrumentResponse result = service.createInstrument(request);

        // THEN
        verify(mapper).toEntity(request);
        verify(repository).save(instrument);
        verify(mapper).toResponse(instrument);
        assertThat(result).isSameAs(response);
    }

    @Test
    void should_map_response_from_saved_entity() {
        InstrumentRequest request = new InstrumentRequest(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD
        );

        Instrument toSave = new Instrument();
        toSave.setName("Apple Inc.");
        toSave.setIsin("US0378331005");
        toSave.setType(InstrumentType.EQUITY);
        toSave.setCurrency(Currency.USD);

        Instrument saved = new Instrument();
        saved.setId(42L);
        saved.setName("Apple Inc.");
        saved.setIsin("US0378331005");
        saved.setType(InstrumentType.EQUITY);
        saved.setCurrency(Currency.USD);

        InstrumentResponse expected = new InstrumentResponse(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD,
                LocalDateTime.now()
        );

        when(mapper.toEntity(request)).thenReturn(toSave);
        when(repository.save(toSave)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(expected);

        InstrumentResponse result = service.createInstrument(request);

        assertThat(result).isEqualTo(expected);
        verify(mapper).toResponse(saved);
    }

    @Test
    void should_propagate_exception_when_mapper_to_entity_fails() {
        InstrumentRequest request = new InstrumentRequest(
                "Bad instrument",
                "BAD",
                InstrumentType.EQUITY,
                Currency.USD
        );
        RuntimeException failure = new RuntimeException("mapping failed");
        when(mapper.toEntity(request)).thenThrow(failure);

        assertThatThrownBy(() -> service.createInstrument(request))
                .isSameAs(failure);

        verifyNoInteractions(repository);
    }

    @Test
    void should_propagate_exception_when_repository_save_fails() {
        InstrumentRequest request = new InstrumentRequest(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD
        );
        Instrument toSave = new Instrument();
        when(mapper.toEntity(request)).thenReturn(toSave);

        RuntimeException failure = new RuntimeException("db unavailable");
        when(repository.save(toSave)).thenThrow(failure);

        assertThatThrownBy(() -> service.createInstrument(request))
                .isSameAs(failure);

        verify(mapper, never()).toResponse(toSave);
    }

    @Test
    void should_propagate_exception_when_mapper_to_response_fails() {
        InstrumentRequest request = new InstrumentRequest(
                "Apple Inc.",
                "US0378331005",
                InstrumentType.EQUITY,
                Currency.USD
        );
        Instrument instrument = new Instrument();
        when(mapper.toEntity(request)).thenReturn(instrument);
        when(repository.save(instrument)).thenReturn(instrument);

        RuntimeException failure = new RuntimeException("response mapping failed");
        when(mapper.toResponse(instrument)).thenThrow(failure);

        assertThatThrownBy(() -> service.createInstrument(request))
                .isSameAs(failure);

        verify(repository).save(instrument);
    }
}