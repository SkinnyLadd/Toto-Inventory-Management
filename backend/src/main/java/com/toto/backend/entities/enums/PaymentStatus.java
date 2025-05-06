package com.toto.backend.entities.enums;

public enum PaymentStatus {
    PENDING("Pending"),
    PARTIAL("Partial Payment"),
    ADVANCE_PAID("Advance Paid"),
    COMPLETED("Completed"),
    INSTALLMENTS_ONGOING("Installments Ongoing"),
    DEFAULTED("Payment Defaulted");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}