package org.prueba.petinsuranceservice.domain.model;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Policy(
        UUID id,
        UUID quoteId,
        String ownerName,
        String ownerId,
        String ownerEmail,
        PolicyStatus status,
        LocalDateTime issueDate) {
}
