package com.training.pms.mapper;

import com.training.pms.dto.request.TradeRequest;
import com.training.pms.dto.response.TradeResponse;
import com.training.pms.model.domain.Trade;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradeMapper {
    Trade toEntity(TradeRequest tradeRequest);

    TradeResponse toResponse(Trade trade);
}