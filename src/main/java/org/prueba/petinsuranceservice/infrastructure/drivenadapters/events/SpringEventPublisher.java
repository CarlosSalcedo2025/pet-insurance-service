package org.prueba.petinsuranceservice.infrastructure.drivenadapters.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prueba.petinsuranceservice.domain.model.event.PolicyIssuedEvent;
import org.prueba.petinsuranceservice.domain.port.out.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpringEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Mono<Void> publishPolicyIssued(PolicyIssuedEvent event) {
        return Mono.fromRunnable(() -> {
            log.info("Publishing policy issued event for policy: {}", event.policyId());
            eventPublisher.publishEvent(event);
        });
    }
}
