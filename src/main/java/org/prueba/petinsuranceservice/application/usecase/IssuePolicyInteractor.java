package org.prueba.petinsuranceservice.application.usecase;

import lombok.RequiredArgsConstructor;
import org.prueba.petinsuranceservice.domain.exception.DomainException;
import org.prueba.petinsuranceservice.domain.model.Policy;
import org.prueba.petinsuranceservice.domain.model.PolicyFactory;
import org.prueba.petinsuranceservice.domain.model.Quote;
import org.prueba.petinsuranceservice.domain.port.in.IssuePolicyUseCase;
import org.prueba.petinsuranceservice.domain.port.out.DomainEventPublisher;
import org.prueba.petinsuranceservice.domain.port.out.PolicyRepository;
import org.prueba.petinsuranceservice.domain.port.out.QuoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssuePolicyInteractor implements IssuePolicyUseCase {

    private final QuoteRepository quoteRepository;
    private final PolicyRepository policyRepository;
    private final DomainEventPublisher eventPublisher;

    @Override
    @Transactional
    public Mono<Policy> execute(UUID quoteId, String ownerName, String ownerId, String ownerEmail) {
        return quoteRepository.findById(quoteId)
                .switchIfEmpty(Mono.error(new DomainException("Quote not found: " + quoteId)))
                .flatMap(quote -> processIssuance(quote, ownerName, ownerId, ownerEmail));
    }

    private Mono<Policy> processIssuance(Quote quote, String name, String id, String email) {
        checkExpiration(quote);

        Policy policy = PolicyFactory.createActivePolicy(quote, name, id, email);

        return policyRepository.save(policy)
                .flatMap(savedPolicy -> publishEvent(savedPolicy, quote));
    }

    private void checkExpiration(Quote quote) {
        if (quote.expirationDate().isBefore(LocalDateTime.now())) {
            throw new DomainException("Quote has expired.");
        }
    }

    private Mono<Policy> publishEvent(Policy policy, Quote quote) {
        return eventPublisher.publishPolicyIssued(PolicyFactory.createIssuedEvent(policy, quote))
                .thenReturn(policy);
    }
}
