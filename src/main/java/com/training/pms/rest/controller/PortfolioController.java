package com.training.pms.rest.controller;

import com.training.pms.dto.PortfolioRequest;
import com.training.pms.dto.PortfolioResponse;
import com.training.pms.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping()
    public ResponseEntity<?> createPortfolio(@RequestBody @Validated PortfolioRequest portfolioRequest,
                                             @RequestParam Long userId
    ) {
        PortfolioResponse ptf = portfolioService.createPortfolio(portfolioRequest, userId);
        return new ResponseEntity<>(ptf, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/positions")
    public ResponseEntity<?> getPositions(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.getPositions(id));
    }

    @GetMapping("/{id}/pnl")
    public ResponseEntity<?> getPnl(@PathVariable Long id) {
        return ResponseEntity.ok(portfolioService.getPnl(id));
    }
}