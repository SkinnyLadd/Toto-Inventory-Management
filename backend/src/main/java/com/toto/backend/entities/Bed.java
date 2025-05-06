package com.toto.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("BED")
@Table(name = "beds")
public class Bed extends Furniture {
    private String size; // single, double, queen, king
    private boolean hasHeadboard;
    private boolean hasFootboard;
    private boolean hasStorageDrawers;
    private String mattressType;
    private boolean isAdjustable;

    @Override
    public double calculateCost() {
        double baseCost = getPrice();
        if (hasHeadboard) baseCost += 75;
        if (hasFootboard) baseCost += 50;
        if (hasStorageDrawers) baseCost += 100;
        if (isAdjustable) baseCost += 200;

        // Size-based cost adjustment
        switch (size.toLowerCase()) {
            case "king": baseCost *= 1.4; break;
            case "queen": baseCost *= 1.2; break;
            case "double": baseCost *= 1.1; break;
            default: break; // single size - no additional cost
        }

        return baseCost;
    }

    @Override
    public void refurbish() {
        // For example, we can reduce the price by 20% for refurbishing
        setPrice(getPrice() * 0.8);

    }


}
