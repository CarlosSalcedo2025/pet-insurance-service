package org.prueba.petinsuranceservice.application.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prueba.petinsuranceservice.domain.exception.DomainException;
import org.prueba.petinsuranceservice.domain.model.Pet;
import org.prueba.petinsuranceservice.domain.model.Plan;
import org.prueba.petinsuranceservice.domain.model.Policy;
import org.prueba.petinsuranceservice.domain.model.Quote;
import org.prueba.petinsuranceservice.domain.model.Species;
import org.prueba.petinsuranceservice.domain.model.event.PolicyIssuedEvent;
import org.prueba.petinsuranceservice.domain.port.out.DomainEventPublisher;
import org.prueba.petinsuranceservice.domain.port.out.PolicyRepository;
import org.prueba.petinsuranceservice.domain.port.out.QuoteRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssuePolicyInteractorTest {

    @Mock
    private QuoteRepository quoteRepository;
    @Mock
    private PolicyRepository policyRepository;
    @Mock
    private DomainEventPublisher eventPublisher;

    @InjectMocks
    private IssuePolicyInteractor interactor;

    private UUID quoteId;
    private Quote validQuote;

    @BeforeEach
    void setUp() {
        quoteId = UUID.randomUUID();
        validQuote = Quote.builder()
                .id(quoteId)
                .amount(new BigDecimal("12.00"))
                .expirationDate(LocalDateTime.now().plusHours(24))
                .pet(Pet.builder().name("Rex").species(Species.DOG).age(3).plan(Plan.BASIC).build())
                .build();
    }

    @Test
    void shouldIssuePolicySuccessfully() {
        when(quoteRepository.findById(quoteId)).thenReturn(Mono.just(validQuote));
        when(policyRepository.save(any(Policy.class))).thenAnswer(i -> Mono.just((Policy) i.getArguments()[0]));
        when(eventPublisher.publishPolicyIssued(any(PolicyIssuedEvent.class))).thenReturn(Mono.empty());

        Mono<Policy> result = interactor.execute(quoteId, "Juan", "123", "juan@test.com");

        StepVerifier.create(result)
                .expectNextMatches(policy -> policy.ownerName().equals("Juan") && policy.quoteId().equals(quoteId))
                .verifyComplete();

        verify(eventPublisher, times(1)).publishPolicyIssued(any());
    }

    @Test
    void shouldThrowErrorWhenQuoteNotFound() {
        when(quoteRepository.findById(quoteId)).thenReturn(Mono.empty());

        Mono<Policy> result = interactor.execute(quoteId, "Juan", "123", "juan@test.com");

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof DomainException && e.getMessage().contains("Quote not found"))
                .verify();
    }

    @Test
    void shouldThrowErrorWhenQuoteIsExpired() {
        Quote expiredQuote = Quote.builder()
                .id(quoteId)
                .expirationDate(LocalDateTime.now().minusHours(1))
                .build();

        when(quoteRepository.findById(quoteId)).thenReturn(Mono.just(expiredQuote));

        Mono<Policy> result = interactor.execute(quoteId, "Juan", "123", "juan@test.com");

        StepVerifier.create(result)
                .expectErrorMatches(e -> e instanceof DomainException && e.getMessage().contains("expired"))
                .verify();
    }

    @Test
    void shouldThrowErrorWhenOwnerDataIsInvalid() {
        when(quoteRepository.findById(quoteId)).thenReturn(Mono.just(validQuote));

        // Test name empty
        Mono<Policy> result = interactor.execute(quoteId, "", "123", "juan@test.com");

        StepVerifier.create(result)
                .expectErrorMatches(
                        e -> e instanceof DomainException && e.getMessage().contains("Owner name is required"))
                .verify();
    }
}
