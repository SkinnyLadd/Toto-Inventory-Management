
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
@DiscriminatorValue("CHAIR")
@Table(name = "chairs")
public class Chair extends Furniture {
    private int seatingCapacity;
    private boolean hasArmrests;
    private String chairStyle; // dining, office, rocking, etc.
    private boolean isAdjustable;
    private boolean hasWheels;

    @Override
    public double calculateCost() {
        double baseCost = getPrice();
        if (hasArmrests) baseCost += 20;
        if (isAdjustable) baseCost += 50;
        if (hasWheels) baseCost += 15;
        return baseCost;
    }

    @Override
    public void refurbish() {
        setPrice(getPrice() * 0.8);
    }

}
