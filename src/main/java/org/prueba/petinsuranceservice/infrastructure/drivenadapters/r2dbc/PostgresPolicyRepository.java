package org.prueba.petinsuranceservice.infrastructure.drivenadapters.r2dbc;

import lombok.RequiredArgsConstructor;
import org.prueba.petinsuranceservice.domain.model.Policy;
import org.prueba.petinsuranceservice.domain.model.PolicyStatus;
import org.prueba.petinsuranceservice.domain.port.out.PolicyRepository;
import org.prueba.petinsuranceservice.infrastructure.drivenadapters.r2dbc.entity.PolicyEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PostgresPolicyRepository implements PolicyRepository {

    private final ReactivePolicyRepository reactivePolicyRepository;

    @Override
    public Mono<Policy> save(Policy policy) {
        PolicyEntity entity = PolicyEntity.builder()
                .id(policy.id())
                .quoteId(policy.quoteId())
                .ownerName(policy.ownerName())
                .ownerId(policy.ownerId())
                .ownerEmail(policy.ownerEmail())
                .status(policy.status().name())
                .issueDate(policy.issueDate())
                .build();

        return reactivePolicyRepository.save(entity)
                .map(this::toDomain);
    }

    private Policy toDomain(PolicyEntity entity) {
        return Policy.builder()
                .id(entity.getId())
                .quoteId(entity.getQuoteId())
                .ownerName(entity.getOwnerName())
                .ownerId(entity.getOwnerId())
                .ownerEmail(entity.getOwnerEmail())
                .status(PolicyStatus.valueOf(entity.getStatus()))
                .issueDate(entity.getIssueDate())
                .build();
    }
}
