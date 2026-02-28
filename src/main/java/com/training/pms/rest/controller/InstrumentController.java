package com.training.pms.rest.controller;

import com.training.pms.dto.InstrumentRequest;
import com.training.pms.service.InstrumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/instruments")
public class InstrumentController {
    private final InstrumentService instrumentService;

    public InstrumentController(InstrumentService service) {
        this.instrumentService = service;
    }

    @PostMapping
    public ResponseEntity<?> createInstrument(@RequestBody @Validated InstrumentRequest instrumentRequest) {
        return new ResponseEntity<>(instrumentService.createInstrument(instrumentRequest), HttpStatus.CREATED);
    }
}