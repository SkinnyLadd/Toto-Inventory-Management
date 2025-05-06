package com.toto.backend.entities;

import com.toto.backend.entities.enums.PaymentMethod;
import com.toto.backend.entities.enums.SupplierStatus;
import com.toto.backend.entities.enums.SupplierType;
import com.toto.backend.entities.enums.WoodType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "suppliers")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String ownerName;

    @NotBlank
    @Column(nullable = false)
    private String contactPerson;

    @Email
    @Column(unique = true)
    private String email;

    @Pattern(regexp = "^(\\+92|0)[0-9]{10}$")
    @Column(nullable = false)
    private String primaryPhone;

    @Pattern(regexp = "^(\\+92|0)[0-9]{10}$")
    private String secondaryPhone;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String area;  // For specific areas like Johar Town, Defence, etc.

    @Column(nullable = false)
    private String completeAddress;

    @Column(length = 13)
    private String ntnNumber;  // National Tax Number

    @Column(length = 15)
    private String cnicNumber;  // CNIC of the owner

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplierType supplierType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplierStatus status;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Furniture> suppliedFurniture = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "supplier_specialties")
    private List<String> specialties = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "supplier_wood_types")
    private List<WoodType> woodTypesOffered = new ArrayList<>();

    @Column(nullable = false)
    private double minimumOrderAmount;

    private Integer standardLeadTimeInDays;

    private double bulkOrderDiscountRate;

    @Column(nullable = false)
    private boolean providesCustomWork;

    private boolean providesInstallation;

    @Column(columnDefinition = "TEXT")
    private String paymentTerms;

    @Enumerated(EnumType.STRING)
    private PaymentMethod preferredPaymentMethod;

    // Geographical coverage
    @ElementCollection
    @CollectionTable(name = "supplier_service_cities")
    private List<String> serviceCities = new ArrayList<>();

    // Helper methods
    public void addFurniture(Furniture furniture) {
        suppliedFurniture.add(furniture);
        furniture.setSupplier(this);
    }

    public void removeFurniture(Furniture furniture) {
        suppliedFurniture.remove(furniture);
        furniture.setSupplier(null);
    }

    public double calculateOrderTotal(List<Furniture> items) {
        double total = items.stream()
                .mapToDouble(Furniture::getPrice)
                .sum();

        return applyBulkDiscount(total);
    }

    private double applyBulkDiscount(double amount) {
        return amount * (1 - bulkOrderDiscountRate);
    }

    public boolean canServiceLocation(String city) {
        return serviceCities.contains(city.toLowerCase());
    }
}
