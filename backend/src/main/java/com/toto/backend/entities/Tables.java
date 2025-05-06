package com.toto.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Represents a table in the furniture inventory.
 * This class combines the functionality of the previous Table and DiningTable classes.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("TABLES")
@Table(name = "tables")
public class Tables extends Furniture {
    private String shape; // round, rectangular, square
    private int seatingCapacity;
    private boolean isExtendable;
    private double length;
    private double width;
    private double height;
    private boolean hasGlassTop;

    @Override
    public double calculateCost() {
        double baseCost = getPrice();
        if (isExtendable) baseCost += 80;
        if (hasGlassTop) baseCost += 100;
        baseCost += (seatingCapacity * 15);
        return baseCost;
    }

    @Override
    public void refurbish() {
        setPrice(getPrice() * 0.85);
    }
}
