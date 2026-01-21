package org.prueba.petinsuranceservice.infrastructure.drivenadapters.r2dbc;

import org.prueba.petinsuranceservice.infrastructure.drivenadapters.r2dbc.entity.QuoteEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReactiveQuoteRepository extends ReactiveCrudRepository<QuoteEntity, UUID> {
}
