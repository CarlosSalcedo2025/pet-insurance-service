package org.prueba.petinsuranceservice.domain.model;

import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record Quote(
        UUID id,
        BigDecimal amount,
        LocalDateTime expirationDate,
        Pet pet) {
}
