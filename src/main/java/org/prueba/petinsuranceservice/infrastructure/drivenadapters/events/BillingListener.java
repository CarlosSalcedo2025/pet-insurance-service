package org.prueba.petinsuranceservice.infrastructure.drivenadapters.events;

import lombok.extern.slf4j.Slf4j;
import org.prueba.petinsuranceservice.domain.model.event.PolicyIssuedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BillingListener {

    @EventListener
    public void onPolicyIssued(PolicyIssuedEvent event) {
        log.info("**********************************************************");
        log.info("BILLING TRIGGERED!");
        log.info("Policy ID: {}", event.policyId());
        log.info("Amount to charge: ${} USD", event.amount());
        log.info("Owner Email: {}", event.ownerEmail());
        log.info("Timestamp: {}", event.occurredAt());
        log.info("**********************************************************");

        // Aquí se llamaría a una pasarela de pagos o se enviaría a una cola de
        // facturación.
    }
}
