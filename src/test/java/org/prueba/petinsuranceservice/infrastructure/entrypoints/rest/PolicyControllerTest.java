package org.prueba.petinsuranceservice.infrastructure.entrypoints.rest;

import org.junit.jupiter.api.Test;
import org.prueba.petinsuranceservice.domain.model.Policy;
import org.prueba.petinsuranceservice.domain.model.PolicyStatus;
import org.prueba.petinsuranceservice.domain.port.in.IssuePolicyUseCase;
import org.prueba.petinsuranceservice.infrastructure.entrypoints.rest.dto.IssuePolicyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(PolicyController.class)
class PolicyControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private IssuePolicyUseCase issuePolicyUseCase;

    @Test
    void shouldReturnCreatedPolicy() {
        UUID quoteId = UUID.randomUUID();
        UUID policyId = UUID.randomUUID();
        Policy expectedPolicy = Policy.builder()
                .id(policyId)
                .quoteId(quoteId)
                .ownerName("Juan Perez")
                .status(PolicyStatus.ACTIVE)
                .issueDate(LocalDateTime.now())
                .build();

        IssuePolicyRequest request = new IssuePolicyRequest(
                quoteId, "Juan Perez", "12345", "juan@test.com");

        when(issuePolicyUseCase.execute(eq(quoteId), any(), any(), any()))
                .thenReturn(Mono.just(expectedPolicy));

        webTestClient.post()
                .uri("/api/policies")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(policyId.toString())
                .jsonPath("$.status").isEqualTo("ACTIVE");
    }

    @Test
    void shouldReturnBadRequestWhenValidationFails() {
        // Missing ownerEmail and empty ownerName
        IssuePolicyRequest invalidRequest = new IssuePolicyRequest(
                UUID.randomUUID(), "", "12345", "");

        webTestClient.post()
                .uri("/api/policies")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
