package com.training.pms.instrument.mapper;

import com.training.pms.instrument.dto.request.InstrumentRequest;
import com.training.pms.instrument.dto.response.InstrumentResponse;
import com.training.pms.model.domain.Instrument;
import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InstrumentMapperTest {

    private final InstrumentMapper mapper = Mappers.getMapper(InstrumentMapper.class);

    @Test
    void should_map_instrument_request_to_entity() {
        InstrumentRequest request = new InstrumentRequest("Apple", "US0378331005", InstrumentType.EQUITY, Currency.USD);

        Instrument result = mapper.toEntity(request);

        assertThat(result.getName()).isEqualTo("Apple");
        assertThat(result.getIsin()).isEqualTo("US0378331005");
        assertThat(result.getType()).isEqualTo(InstrumentType.EQUITY);
        assertThat(result.getCurrency()).isEqualTo(Currency.USD);
    }

    @Test
    void should_map_instrument_entity_to_response() {
        Instrument instrument = new Instrument();
        instrument.setName("Apple");
        instrument.setIsin("US0378331005");
        instrument.setType(InstrumentType.EQUITY);
        instrument.setCurrency(Currency.USD);
        instrument.setCreatedAt(LocalDateTime.now());

        InstrumentResponse result = mapper.toResponse(instrument);

        assertThat(result.name()).isEqualTo("Apple");
        assertThat(result.isin()).isEqualTo("US0378331005");
        assertThat(result.type()).isEqualTo(InstrumentType.EQUITY);
        assertThat(result.currency()).isEqualTo(Currency.USD);
        assertThat(result.createdAt()).isEqualTo(instrument.getCreatedAt());
    }
}
