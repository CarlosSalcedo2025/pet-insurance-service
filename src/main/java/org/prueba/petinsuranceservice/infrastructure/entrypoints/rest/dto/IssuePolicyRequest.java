package org.prueba.petinsuranceservice.infrastructure.entrypoints.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record IssuePolicyRequest(
                @NotNull(message = "Quote ID is required") UUID quoteId,

                @NotBlank(message = "Owner name is required") String ownerName,

                @NotBlank(message = "Owner ID is required") String ownerId,

                @NotBlank(message = "Owner email is required") @Email(message = "Invalid email format") String ownerEmail) {
}
