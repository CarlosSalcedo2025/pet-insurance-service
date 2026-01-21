package org.prueba.petinsuranceservice.domain.model.event;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PolicyIssuedEvent(
        UUID policyId,
        UUID quoteId,
        BigDecimal amount,
        String ownerEmail,
        LocalDateTime occurredAt) {
}
