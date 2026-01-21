package org.prueba.petinsuranceservice.infrastructure.entrypoints.rest.dto;

import java.util.UUID;

public record IssuePolicyRequest(
        UUID quoteId,
        String ownerName,
        String ownerId,
        String ownerEmail) {
}
