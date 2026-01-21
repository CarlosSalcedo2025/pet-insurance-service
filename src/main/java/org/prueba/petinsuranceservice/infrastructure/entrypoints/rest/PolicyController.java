package org.prueba.petinsuranceservice.infrastructure.entrypoints.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prueba.petinsuranceservice.domain.model.Policy;
import org.prueba.petinsuranceservice.domain.port.in.IssuePolicyUseCase;
import org.prueba.petinsuranceservice.infrastructure.entrypoints.rest.dto.IssuePolicyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final IssuePolicyUseCase issuePolicyUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Policy> issuePolicy(@Valid @RequestBody IssuePolicyRequest request) {

        return issuePolicyUseCase.execute(
                request.quoteId(),
                request.ownerName(),
                request.ownerId(),
                request.ownerEmail());
    }
}
