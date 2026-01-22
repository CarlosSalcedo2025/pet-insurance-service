package org.prueba.petinsuranceservice.domain.model;

import org.prueba.petinsuranceservice.domain.exception.DomainException;
import org.prueba.petinsuranceservice.domain.model.event.PolicyIssuedEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public final class PolicyFactory {

    private PolicyFactory() {
    }

    public static Policy createActivePolicy(Quote quote, String ownerName, String ownerId, String ownerEmail) {
        validateOwner(ownerName, ownerId, ownerEmail);

        return Policy.builder()
                .id(UUID.randomUUID())
                .quoteId(quote.id())
                .ownerName(ownerName)
                .ownerId(ownerId)
                .ownerEmail(ownerEmail)
                .status(PolicyStatus.ACTIVE)
                .issueDate(LocalDateTime.now())
                .build();
    }

    public static PolicyIssuedEvent createIssuedEvent(Policy policy, Quote quote) {
        return PolicyIssuedEvent.builder()
                .policyId(policy.id())
                .quoteId(policy.quoteId())
                .amount(quote.amount())
                .ownerEmail(policy.ownerEmail())
                .occurredAt(LocalDateTime.now())
                .build();
    }

    private static void validateOwner(String name, String id, String email) {
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
