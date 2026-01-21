package org.prueba.petinsuranceservice.domain.port.out;

import org.prueba.petinsuranceservice.domain.model.Policy;
import reactor.core.publisher.Mono;

public interface PolicyRepository {
    Mono<Policy> save(Policy policy);
}
