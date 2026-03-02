package com.training.pms.instrument.application;

import com.training.pms.instrument.dto.request.InstrumentRequest;
import com.training.pms.instrument.dto.response.InstrumentResponse;
import com.training.pms.instrument.mapper.InstrumentMapper;
import com.training.pms.instrument.repository.InstrumentRepository;
import com.training.pms.model.domain.Instrument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InstrumentService {
    private final InstrumentRepository repository;
    private final InstrumentMapper mapper;

    public InstrumentService(InstrumentRepository repository, InstrumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public InstrumentResponse createInstrument(InstrumentRequest instrumentRequest) {
        Instrument instrument = mapper.toEntity(instrumentRequest);
        Instrument instrumentSaved = repository.save(instrument);
        return mapper.toResponse(instrumentSaved);
    }
}