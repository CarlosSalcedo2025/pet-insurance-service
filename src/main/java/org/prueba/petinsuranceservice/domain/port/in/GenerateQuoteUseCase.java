package org.prueba.petinsuranceservice.domain.port.in;

import org.prueba.petinsuranceservice.domain.model.Pet;
import org.prueba.petinsuranceservice.domain.model.Quote;
import reactor.core.publisher.Mono;

public interface GenerateQuoteUseCase {
    Mono<Quote> execute(Pet pet);
}
