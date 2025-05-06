package com.toto.backend.entities.enums;

import lombok.Getter;

@Getter
public enum SupplierType {
    MANUFACTURER("Direct Manufacturer"),
    WHOLESALER("Wholesaler"),
    ARTISAN("Individual Artisan"),
    IMPORTER("Importer");

    private final String displayName;

    SupplierType(String displayName) {
        this.displayName = displayName;
    }
}
