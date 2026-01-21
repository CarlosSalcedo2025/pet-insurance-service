package org.prueba.petinsuranceservice.domain.port.in;

import org.prueba.petinsuranceservice.domain.model.Policy;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IssuePolicyUseCase {
    Mono<Policy> execute(UUID quoteId, String ownerName, String ownerId, String ownerEmail);
}
