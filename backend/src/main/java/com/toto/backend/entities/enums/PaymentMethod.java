package com.toto.backend.entities.enums;

/**
 * Enum representing different payment methods available in a Pakistani furniture store.
 */
public enum PaymentMethod {
    CASH("Cash"),
    BANK_TRANSFER("Bank Transfer"),
    EASY_PAISA("Easy Paisa"),
    JAZZ_CASH("Jazz Cash"),
    POST_DATED_CHEQUE("Post-dated Cheque");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
