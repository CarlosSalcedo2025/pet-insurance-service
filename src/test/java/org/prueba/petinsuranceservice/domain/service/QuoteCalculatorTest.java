package org.prueba.petinsuranceservice.domain.service;

import org.junit.jupiter.api.Test;
import org.prueba.petinsuranceservice.domain.exception.DomainException;
import org.prueba.petinsuranceservice.domain.model.Pet;
import org.prueba.petinsuranceservice.domain.model.Plan;
import org.prueba.petinsuranceservice.domain.model.Quote;
import org.prueba.petinsuranceservice.domain.model.Species;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class QuoteCalculatorTest {

    private final QuoteCalculator calculator = new QuoteCalculator();

    @Test
    void shouldCalculateBasicDogUnder5() {
        Pet pet = Pet.builder()
                .name("Rex")
                .species(Species.DOG)
                .age(3)
                .plan(Plan.BASIC)
                .build();

        Quote quote = calculator.calculate(pet);

        // 10 base * 1.2 (dog) = 12.00
        assertEquals(new BigDecimal("12.00"), quote.amount());
    }

    @Test
    void shouldCalculatePremiumDogUnder5() {
        Pet pet = Pet.builder()
                .name("Rex")
                .species(Species.DOG)
                .age(3)
                .plan(Plan.PREMIUM)
                .build();

        Quote quote = calculator.calculate(pet);

        // 10 base * 1.2 (dog) * 2 (premium) = 24.00
        assertEquals(new BigDecimal("24.00"), quote.amount());
    }

    @Test
    void shouldCalculateBasicCatOver5() {
        Pet pet = Pet.builder()
                .name("Mittens")
                .species(Species.CAT)
                .age(6)
                .plan(Plan.BASIC)
                .build();

        Quote quote = calculator.calculate(pet);

        // 10 base * 1.1 (cat) * 1.5 (age) = 16.50
        assertEquals(new BigDecimal("16.50"), quote.amount());
    }

    @Test
    void shouldCalculatePremiumCatOver5() {
        Pet pet = Pet.builder()
                .name("Mittens")
                .species(Species.CAT)
                .age(6)
                .plan(Plan.PREMIUM)
                .build();

        Quote quote = calculator.calculate(pet);

        // 10 base * 1.1 (cat) * 1.5 (age) * 2 (premium) = 33.00
        assertEquals(new BigDecimal("33.00"), quote.amount());
    }

    @Test
    void shouldThrowExceptionWhenPetIsTooOld() {
        Pet pet = Pet.builder()
                .name("Oldy")
                .species(Species.DOG)
                .age(11)
                .plan(Plan.BASIC)
                .build();

        assertThrows(DomainException.class, () -> calculator.calculate(pet));
    }
}
