package com.training.pms.service;

import com.training.pms.dto.request.InstrumentRequest;
import com.training.pms.dto.response.InstrumentResponse;
import com.training.pms.mapper.InstrumentMapper;
import com.training.pms.model.domain.Instrument;
import com.training.pms.repository.InstrumentRepository;
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