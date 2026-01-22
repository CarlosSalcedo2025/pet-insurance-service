package org.prueba.petinsuranceservice.domain.service;

import org.prueba.petinsuranceservice.domain.exception.DomainException;
import org.prueba.petinsuranceservice.domain.model.Pet;
import org.prueba.petinsuranceservice.domain.model.Plan;
import org.prueba.petinsuranceservice.domain.model.Quote;
import org.prueba.petinsuranceservice.domain.model.constant.InsuranceConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.prueba.petinsuranceservice.domain.model.constant.InsuranceConstants.*;

public class QuoteCalculator {

    public Quote calculate(Pet pet) {
        validatePet(pet);

        BigDecimal finalAmount = calculateFinalAmount(pet);

        return buildQuote(pet, finalAmount);
    }

    private BigDecimal calculateFinalAmount(Pet pet) {
        BigDecimal amount = BASE_PRICE;

        amount = applySpeciesRisk(amount, pet);
        amount = applyAgeRisk(amount, pet);
        amount = applyPlanMultiplier(amount, pet);

        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal applySpeciesRisk(BigDecimal currentAmount, Pet pet) {
        BigDecimal factor = switch (pet.species()) {
            case DOG -> DOG_RISK_FACTOR;
            case CAT -> CAT_RISK_FACTOR;
        };
        return currentAmount.multiply(factor);
    }

    private BigDecimal applyAgeRisk(BigDecimal currentAmount, Pet pet) {
        if (pet.age() > AGE_RISK_THRESHOLD) {
            return currentAmount.multiply(AGE_RISK_FACTOR);
        }
        return currentAmount;
    }

    private BigDecimal applyPlanMultiplier(BigDecimal currentAmount, Pet pet) {
        if (pet.plan() == Plan.PREMIUM) {
            return currentAmount.multiply(PREMIUM_PLAN_MULTIPLIER);
        }
        return currentAmount;
    }

    private Quote buildQuote(Pet pet, BigDecimal amount) {
        return Quote.builder()
                .id(UUID.randomUUID())
                .amount(amount)
                .expirationDate(LocalDateTime.now().plusHours(QUOTE_EXPIRATION_HOURS))
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
        if (pet.age() > MAX_INSURABLE_AGE) {
            throw new DomainException("Pets older than " + MAX_INSURABLE_AGE + " years cannot be insured.");
        }
    }
}
