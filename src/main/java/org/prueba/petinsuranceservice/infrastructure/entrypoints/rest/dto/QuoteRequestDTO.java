package org.prueba.petinsuranceservice.infrastructure.entrypoints.rest.dto;

import org.prueba.petinsuranceservice.domain.model.Plan;
import org.prueba.petinsuranceservice.domain.model.Species;

public record QuoteRequestDTO(
        String name,
        Species species,
        String breed,
        int age,
        Plan plan) {
}
