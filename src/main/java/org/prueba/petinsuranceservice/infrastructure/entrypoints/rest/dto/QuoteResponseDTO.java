package org.prueba.petinsuranceservice.infrastructure.entrypoints.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record QuoteResponseDTO(
        UUID id,
        BigDecimal amount,
        LocalDateTime expirationDate) {
}
