package com.training.pms.trade.mapper;

import com.training.pms.model.domain.Trade;
import com.training.pms.trade.dto.request.TradeRequest;
import com.training.pms.trade.dto.response.TradeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradeMapper {
    Trade toEntity(TradeRequest tradeRequest);

    TradeResponse toResponse(Trade trade);
}