package org.prueba.petinsuranceservice.infrastructure.entrypoints.rest;

import lombok.RequiredArgsConstructor;
import org.prueba.petinsuranceservice.domain.model.Pet;
import org.prueba.petinsuranceservice.domain.port.in.GenerateQuoteUseCase;
import org.prueba.petinsuranceservice.infrastructure.entrypoints.rest.dto.QuoteRequestDTO;
import org.prueba.petinsuranceservice.infrastructure.entrypoints.rest.dto.QuoteResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final GenerateQuoteUseCase generateQuoteUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<QuoteResponseDTO> generateQuote(@RequestBody QuoteRequestDTO request) {
        Pet pet = Pet.builder()
                .name(request.name())
                .species(request.species())
                .breed(request.breed())
                .age(request.age())
                .plan(request.plan())
                .build();

        return generateQuoteUseCase.execute(pet)
                .map(quote -> new QuoteResponseDTO(
                        quote.id(),
                        quote.amount(),
                        quote.expirationDate()));
    }
}
