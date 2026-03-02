package com.training.pms.instrument.mapper;

import com.training.pms.instrument.dto.request.InstrumentRequest;
import com.training.pms.instrument.dto.response.InstrumentResponse;
import com.training.pms.model.domain.Instrument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstrumentMapper {
    Instrument toEntity(InstrumentRequest instrumentRequest);

    InstrumentResponse toResponse(Instrument instrument);
}