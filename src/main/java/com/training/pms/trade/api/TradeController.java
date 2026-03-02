package com.training.pms.trade.api;

import com.training.pms.trade.application.TradeService;
import com.training.pms.trade.dto.request.TradeRequest;
import com.training.pms.trade.dto.response.TradeResponse;
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