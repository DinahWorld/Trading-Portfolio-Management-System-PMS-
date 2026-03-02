package com.training.pms.portfolio.api;

import com.training.pms.model.domain.Position;
import com.training.pms.portfolio.application.PortfolioService;
import com.training.pms.portfolio.dto.request.PortfolioRequest;
import com.training.pms.portfolio.dto.response.PortfolioResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping()
    public ResponseEntity<PortfolioResponse> createPortfolio(@RequestBody @Valid PortfolioRequest portfolioRequest,
                                                             @RequestParam Long userId) {
        PortfolioResponse ptf = portfolioService.createPortfolio(portfolioRequest, userId);
        return new ResponseEntity<>(ptf, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/positions")
    public Set<Position> getPositions(@Validated @PathVariable Long id) {
        return portfolioService.getPositions(id);
    }

    @GetMapping("/{id}/pnl")
    public BigDecimal getPnl(@PathVariable Long id) {
        return portfolioService.getPnl(id);
    }
}