package com.training.pms.features.trade.api;

import com.training.pms.features.trade.dto.request.TradeRequest;
import com.training.pms.features.trade.dto.response.TradeResponse;
import com.training.pms.features.trade.services.TradeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<TradeResponse> createTrade(@RequestBody @Valid TradeRequest tradeRequest) {
        return new ResponseEntity<>(tradeService.createTrade(tradeRequest), HttpStatus.CREATED);
    }
}