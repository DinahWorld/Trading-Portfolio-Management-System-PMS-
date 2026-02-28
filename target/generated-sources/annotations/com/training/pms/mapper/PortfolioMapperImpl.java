package com.training.pms.mapper;

import com.training.pms.dto.request.PortfolioRequest;
import com.training.pms.dto.response.PortfolioResponse;
import com.training.pms.model.domain.Portfolio;
import com.training.pms.model.domain.User;
import com.training.pms.model.enums.Currency;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-28T21:47:07+0100",
    comments = "version: 1.7.0.Beta1, compiler: javac, environment: Java 25.0.2 (Oracle Corporation)"
)
@Component
public class PortfolioMapperImpl implements PortfolioMapper {

    @Override
    public PortfolioResponse toResponse(Portfolio portfolio) {
        if ( portfolio == null ) {
            return null;
        }

        String username = null;
        String name = null;
        Currency baseCurrency = null;
        LocalDateTime createdAt = null;

        username = portfolioUserUsername( portfolio );
        name = portfolio.getName();
        baseCurrency = portfolio.getBaseCurrency();
        createdAt = portfolio.getCreatedAt();

        PortfolioResponse portfolioResponse = new PortfolioResponse( username, name, baseCurrency, createdAt );

        return portfolioResponse;
    }

    @Override
    public Portfolio toEntity(PortfolioRequest portfolioRequest, Long id) {
        if ( portfolioRequest == null && id == null ) {
            return null;
        }

        Portfolio.PortfolioBuilder portfolio = Portfolio.builder();

        if ( portfolioRequest != null ) {
            portfolio.name( portfolioRequest.name() );
            portfolio.baseCurrency( portfolioRequest.baseCurrency() );
        }
        portfolio.id( id );

        return portfolio.build();
    }

    private String portfolioUserUsername(Portfolio portfolio) {
        User user = portfolio.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getUsername();
    }
}
