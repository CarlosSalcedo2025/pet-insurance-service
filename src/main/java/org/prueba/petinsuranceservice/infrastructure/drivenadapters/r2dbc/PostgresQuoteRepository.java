package org.prueba.petinsuranceservice.infrastructure.drivenadapters.r2dbc;

import lombok.RequiredArgsConstructor;
import org.prueba.petinsuranceservice.domain.model.Pet;
import org.prueba.petinsuranceservice.domain.model.Plan;
import org.prueba.petinsuranceservice.domain.model.Quote;
import org.prueba.petinsuranceservice.domain.model.Species;
import org.prueba.petinsuranceservice.domain.port.out.QuoteRepository;
import org.prueba.petinsuranceservice.infrastructure.drivenadapters.r2dbc.entity.QuoteEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Primary
@RequiredArgsConstructor
public class PostgresQuoteRepository implements QuoteRepository {

    private final ReactiveQuoteRepository reactiveQuoteRepository;

    @Override
    public Mono<Quote> save(Quote quote) {
        QuoteEntity entity = QuoteEntity.builder()
                .id(quote.id())
                .amount(quote.amount())
                .expirationDate(quote.expirationDate())
                .petName(quote.pet().name())
                .petSpecies(quote.pet().species().name())
                .petBreed(quote.pet().breed())
                .petAge(quote.pet().age())
                .petPlan(quote.pet().plan().name())
                .build();

        return reactiveQuoteRepository.save(entity)
                .map(this::toDomain);
    }

    @Override
    public Mono<Quote> findById(UUID id) {
        return reactiveQuoteRepository.findById(id)
                .map(this::toDomain);
    }

    private Quote toDomain(QuoteEntity entity) {
        return Quote.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .expirationDate(entity.getExpirationDate())
                .pet(Pet.builder()
                        .name(entity.getPetName())
                        .species(Species.valueOf(entity.getPetSpecies()))
                        .breed(entity.getPetBreed())
                        .age(entity.getPetAge())
                        .plan(Plan.valueOf(entity.getPetPlan()))
                        .build())
                .build();
    }
}
