package org.prueba.petinsuranceservice.infrastructure.entrypoints.rest;

import org.junit.jupiter.api.Test;
import org.prueba.petinsuranceservice.domain.model.Plan;
import org.prueba.petinsuranceservice.domain.model.Species;
import org.prueba.petinsuranceservice.infrastructure.entrypoints.rest.dto.QuoteRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuoteControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldGenerateQuote() {
        QuoteRequestDTO request = new QuoteRequestDTO("Fido", Species.DOG, "Labrador", 3, Plan.BASIC);

        webTestClient.post()
                .uri("/api/quotes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.amount").isEqualTo(12.0)
                .jsonPath("$.expirationDate").exists();
    }

    @Test
    void shouldReturnErrorWhenPetIsTooOld() {
        QuoteRequestDTO request = new QuoteRequestDTO("Oldy", Species.DOG, "Labrador", 12, Plan.BASIC);

        webTestClient.post()
                .uri("/api/quotes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Pets older than 10 years cannot be insured.");
    }
}
