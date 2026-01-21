package org.prueba.petinsuranceservice.application.usecase;

import lombok.RequiredArgsConstructor;
import org.prueba.petinsuranceservice.domain.model.Pet;
import org.prueba.petinsuranceservice.domain.model.Quote;
import org.prueba.petinsuranceservice.domain.port.in.GenerateQuoteUseCase;
import org.prueba.petinsuranceservice.domain.port.out.QuoteRepository;
import org.prueba.petinsuranceservice.domain.service.QuoteCalculator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GenerateQuoteInteractor implements GenerateQuoteUseCase {

    private final QuoteRepository quoteRepository;
    private final QuoteCalculator quoteCalculator = new QuoteCalculator();

    @Override
    public Mono<Quote> execute(Pet pet) {
        return Mono.fromCallable(() -> quoteCalculator.calculate(pet))
                .flatMap(quoteRepository::save);
    }
}
