package com.toto.backend.entities.enums;

/**
 * Enum representing different types of customers in a Pakistani furniture store.
 */
public enum CustomerType {
    VIP("VIP"),
    CORPORATE("Corporate"),
    WHOLESALE("Wholesale"),
    FIRST_TIME("First Time"),
    REGULAR("Regular");

    private final String displayName;

    CustomerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}