package org.prueba.petinsuranceservice.domain.port.out;

import org.prueba.petinsuranceservice.domain.model.Quote;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface QuoteRepository {
    Mono<Quote> save(Quote quote);

    Mono<Quote> findById(UUID id);
}
