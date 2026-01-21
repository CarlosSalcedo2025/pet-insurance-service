package org.prueba.petinsuranceservice.infrastructure.drivenadapters.r2dbc;

import org.prueba.petinsuranceservice.infrastructure.drivenadapters.r2dbc.entity.PolicyEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ReactivePolicyRepository extends ReactiveCrudRepository<PolicyEntity, UUID> {
}
