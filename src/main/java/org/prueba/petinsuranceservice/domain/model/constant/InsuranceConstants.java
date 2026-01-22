package org.prueba.petinsuranceservice.domain.model.constant;

import java.math.BigDecimal;

public final class InsuranceConstants {
    private InsuranceConstants() {
    }

    public static final BigDecimal BASE_PRICE = BigDecimal.valueOf(10);
    public static final BigDecimal DOG_RISK_FACTOR = BigDecimal.valueOf(1.20);
    public static final BigDecimal CAT_RISK_FACTOR = BigDecimal.valueOf(1.10);
    public static final BigDecimal AGE_RISK_FACTOR = BigDecimal.valueOf(1.50);
    public static final BigDecimal PREMIUM_PLAN_MULTIPLIER = BigDecimal.valueOf(2.0);

    public static final int MAX_INSURABLE_AGE = 10;
    public static final int AGE_RISK_THRESHOLD = 5;
    public static final int QUOTE_EXPIRATION_HOURS = 24;
}
