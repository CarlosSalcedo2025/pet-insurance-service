package org.prueba.petinsuranceservice.domain.port.out;

import org.prueba.petinsuranceservice.domain.model.event.PolicyIssuedEvent;
import reactor.core.publisher.Mono;

public interface DomainEventPublisher {
    Mono<Void> publishPolicyIssued(PolicyIssuedEvent event);
}
