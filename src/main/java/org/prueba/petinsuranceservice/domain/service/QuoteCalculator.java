package org.prueba.petinsuranceservice.domain.service;

import org.prueba.petinsuranceservice.domain.exception.DomainException;
import org.prueba.petinsuranceservice.domain.model.Pet;
import org.prueba.petinsuranceservice.domain.model.Plan;
import org.prueba.petinsuranceservice.domain.model.Quote;
import org.prueba.petinsuranceservice.domain.model.Species;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

public class QuoteCalculator {

    private static final BigDecimal BASE_PRICE = BigDecimal.valueOf(10);
    private static final BigDecimal DOG_INCREASE = BigDecimal.valueOf(1.20);
    private static final BigDecimal CAT_INCREASE = BigDecimal.valueOf(1.10);
    private static final BigDecimal AGE_INCREASE = BigDecimal.valueOf(1.50);
    private static final int MAX_AGE = 10;
    private static final int AGE_THRESHOLD = 5;

    public Quote calculate(Pet pet) {
        validatePet(pet);

        BigDecimal amount = BASE_PRICE;

        // Species increase
        if (pet.species() == Species.DOG) {
            amount = amount.multiply(DOG_INCREASE);
        } else if (pet.species() == Species.CAT) {
            amount = amount.multiply(CAT_INCREASE);
        }

        // Age increase
        if (pet.age() > AGE_THRESHOLD) {
            amount = amount.multiply(AGE_INCREASE);
        }

        // Plan increase
        if (pet.plan() == Plan.PREMIUM) {
            amount = amount.multiply(BigDecimal.valueOf(2));
        }

        return Quote.builder()
                .id(UUID.randomUUID())
                .amount(amount.setScale(2, RoundingMode.HALF_UP))
                .expirationDate(LocalDateTime.now().plusHours(24))
                .pet(pet)
                .build();
    }

    private void validatePet(Pet pet) {
        if (pet.name() == null || pet.name().isBlank()) {
            throw new DomainException("Pet name is required.");
        }
        if (pet.age() < 0) {
            throw new DomainException("Age cannot be negative.");
        }
        if (pet.age() > MAX_AGE) {
            throw new DomainException("Pets older than " + MAX_AGE + " years cannot be insured.");
        }
    }
}
