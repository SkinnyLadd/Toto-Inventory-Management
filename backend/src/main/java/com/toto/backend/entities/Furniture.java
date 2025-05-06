package com.toto.backend.entities;

import com.toto.backend.entities.enums.WoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@DiscriminatorColumn(name = "furniture_type")
public abstract class Furniture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private String material;
    private String manufacturer;

    @Enumerated(EnumType.STRING)
    private WoodType woodType;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    protected Supplier supplier;


    public abstract double calculateCost();
    public abstract void refurbish();

    // Helper method to manage bidirectional relationship with Supplier
    public void updateSupplier(Supplier newSupplier) {
        // Remove from old supplier's list if exists
        if (this.supplier != null && this.supplier.getSuppliedFurniture() != null) {
            this.supplier.getSuppliedFurniture().remove(this);
        }
        // Set new supplier
        this.supplier = newSupplier;
        // Add to new supplier's list if not null
        if (newSupplier != null && newSupplier.getSuppliedFurniture() != null) {
            newSupplier.getSuppliedFurniture().add(this);
        }
    }
}
