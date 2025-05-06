package com.toto.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("MISC")
@Table(name = "misc_furniture")
public class MiscFurniture extends Furniture {

    private String category; // Custom category name
    private String description;

    @ElementCollection
    @CollectionTable(name = "misc_furniture_attributes")
    @MapKeyColumn(name = "attribute_name")
    @Column(name = "attribute_value")
    private Map<String, String> customAttributes = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "misc_furniture_price_modifiers")
    @MapKeyColumn(name = "modifier_name")
    @Column(name = "modifier_value")
    private Map<String, Double> priceModifiers = new HashMap<>();

    public void addCustomAttribute(String name, String value) {
        customAttributes.put(name, value);
    }

    public void removeCustomAttribute(String name) {
        customAttributes.remove(name);
    }

    public void addPriceModifier(String name, Double value) {
        priceModifiers.put(name, value);
    }

    public void removePriceModifier(String name) {
        priceModifiers.remove(name);
    }

    @Override
    public double calculateCost() {
        double baseCost = getPrice();
        // Apply all price modifiers
        for (Double modifier : priceModifiers.values()) {
            baseCost += modifier;
        }
        return baseCost;
    }

    @Override
    public void refurbish() {
        setPrice(getPrice() * 0.8);
    }
}