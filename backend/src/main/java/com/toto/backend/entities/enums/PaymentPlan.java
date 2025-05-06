package com.toto.backend.entities.enums;

/**
 * Enum representing different payment plans available in a Pakistani furniture store.
 */
public enum PaymentPlan {
    FULL_PAYMENT("Full Payment"),
    INSTALLMENTS("Installments"),
    ADVANCE_PAYMENT("Advance Payment"),
    LEASE_TO_OWN("Lease to Own");

    private final String displayName;

    PaymentPlan(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}