package org.prueba.petinsuranceservice.domain.model;

import lombok.Builder;

@Builder
public record Pet(
        String name,
        Species species,
        String breed,
        int age,
        Plan plan) {
}
