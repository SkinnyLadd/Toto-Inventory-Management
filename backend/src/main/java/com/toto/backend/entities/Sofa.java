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
@DiscriminatorValue("SOFA")
@Table(name = "sofas")
public class Sofa extends Furniture {
    private int seatingCapacity;
    private boolean isConvertible; // can be converted to bed
    private String upholsteryType; // leather, fabric, etc.
    private int numberOfCushions;
    private boolean hasRecliners;

    @Override
    public double calculateCost() {
        double baseCost = getPrice();
        if (isConvertible) baseCost += 100;
        if (hasRecliners) baseCost += 150;
        baseCost += (numberOfCushions * 10);
        return baseCost;
    }

    @Override
    public void refurbish() {
        setPrice(getPrice() * 0.75);
    }

}
