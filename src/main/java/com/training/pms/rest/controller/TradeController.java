package com.training.pms.rest.controller;

import com.training.pms.dto.TradeRequest;
import com.training.pms.service.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trades")
public class TradeController {
    private final TradeService tradeService;

    public TradeController(TradeService service) {
        this.tradeService = service;
    }

    @PostMapping
    public ResponseEntity<?> createTrade(TradeRequest tradeRequest) {
        return new ResponseEntity<>(tradeService.createTrade(tradeRequest), HttpStatus.CREATED);
    }
}