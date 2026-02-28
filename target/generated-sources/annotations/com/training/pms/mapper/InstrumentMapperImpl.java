package com.training.pms.mapper;

import com.training.pms.dto.InstrumentRequest;
import com.training.pms.dto.InstrumentResponse;
import com.training.pms.model.domain.Instrument;
import com.training.pms.model.enums.Currency;
import com.training.pms.model.enums.InstrumentType;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-28T16:52:50+0100",
    comments = "version: 1.7.0.Beta1, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class InstrumentMapperImpl implements InstrumentMapper {

    @Override
    public Instrument toEntity(InstrumentRequest instrumentRequest) {
        if ( instrumentRequest == null ) {
            return null;
        }

        Instrument instrument = new Instrument();

        instrument.setName( instrumentRequest.name() );
        instrument.setIsin( instrumentRequest.isin() );
        instrument.setType( instrumentRequest.type() );
        instrument.setCurrency( instrumentRequest.currency() );

        return instrument;
    }

    @Override
    public InstrumentResponse toResponse(Instrument instrument) {
        if ( instrument == null ) {
            return null;
        }

        String name = null;
        String isin = null;
        InstrumentType type = null;
        Currency currency = null;
        LocalDateTime createdAt = null;

        name = instrument.getName();
        isin = instrument.getIsin();
        type = instrument.getType();
        currency = instrument.getCurrency();
        createdAt = instrument.getCreatedAt();

        InstrumentResponse instrumentResponse = new InstrumentResponse( name, isin, type, currency, createdAt );

        return instrumentResponse;
    }
}
