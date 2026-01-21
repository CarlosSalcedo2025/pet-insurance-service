package org.prueba.petinsuranceservice.application.usecase;

import lombok.RequiredArgsConstructor;
import org.prueba.petinsuranceservice.domain.exception.DomainException;
import org.prueba.petinsuranceservice.domain.model.Policy;
import org.prueba.petinsuranceservice.domain.model.PolicyStatus;
import org.prueba.petinsuranceservice.domain.model.event.PolicyIssuedEvent;
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
                .switchIfEmpty(Mono.error(new DomainException("Quote not found with ID: " + quoteId)))
                .flatMap(quote -> {
                    validateOwnerData(ownerName, ownerId, ownerEmail);

                    if (quote.expirationDate().isBefore(LocalDateTime.now())) {
                        return Mono.error(new DomainException("Quote has expired. Please generate a new one."));
                    }

                    Policy policy = Policy.builder()
                            .id(UUID.randomUUID())
                            .quoteId(quoteId)
                            .ownerName(ownerName)
                            .ownerId(ownerId)
                            .ownerEmail(ownerEmail)
                            .status(PolicyStatus.ACTIVE)
                            .issueDate(LocalDateTime.now())
                            .build();

                    return policyRepository.save(policy)
                            .flatMap(savedPolicy -> {
                                PolicyIssuedEvent event = PolicyIssuedEvent.builder()
                                        .policyId(savedPolicy.id())
                                        .quoteId(quoteId)
                                        .amount(quote.amount())
                                        .ownerEmail(ownerEmail)
                                        .occurredAt(LocalDateTime.now())
                                        .build();

                                return eventPublisher.publishPolicyIssued(event)
                                        .thenReturn(savedPolicy);
                            });
                });
    }

    private void validateOwnerData(String name, String id, String email) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Owner name is required.");
        }
        if (id == null || id.isBlank()) {
            throw new DomainException("Owner ID is required.");
        }
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new DomainException("A valid owner email is required.");
        }
    }
}
