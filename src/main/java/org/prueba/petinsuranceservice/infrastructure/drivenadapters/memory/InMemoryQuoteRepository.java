package org.prueba.petinsuranceservice.infrastructure.drivenadapters.memory;

import org.prueba.petinsuranceservice.domain.model.Quote;
import org.prueba.petinsuranceservice.domain.port.out.QuoteRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryQuoteRepository implements QuoteRepository {

    private final Map<UUID, Quote> quotes = new ConcurrentHashMap<>();

    @Override
    public Mono<Quote> save(Quote quote) {
        quotes.put(quote.id(), quote);
        return Mono.just(quote);
    }

    @Override
    public Mono<Quote> findById(UUID id) {
        return Mono.justOrEmpty(quotes.get(id));
    }
}
