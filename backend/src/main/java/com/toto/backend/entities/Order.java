package com.toto.backend.entities;

import com.toto.backend.entities.enums.*;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "furniture_id")
    )
    private List<Furniture> items = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    // Payment related fields
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentPlan paymentPlan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private Double advancePayment;

    private Double remainingPayment;

    private Integer installmentMonths;

    private Double monthlyInstallmentAmount;

    @Column(columnDefinition = "TEXT")
    private String paymentNotes;  // For tracking post-dated cheques or other payment details

    // Delivery related fields
    private LocalDateTime expectedDeliveryDate;

    private LocalDateTime actualDeliveryDate;

    @Column(nullable = false)
    private String deliveryCity;

    private String deliveryArea;  // e.g., DHA, Gulberg, etc.

    @Column(columnDefinition = "TEXT", nullable = false)
    private String completeDeliveryAddress;

    @Pattern(regexp = "^(\\+92|0)[0-9]{10}$")
    private String deliveryContactNumber;

    private Double deliveryCharges;

    private String deliveryNotes;

    // Installation and Assembly
    private boolean requiresAssembly;

    private boolean requiresInstallation;

    private Double installationCharges;

    private LocalDateTime installationDate;

    @Column(columnDefinition = "TEXT")
    private String installationNotes;

    // Special Requirements
    @Column(columnDefinition = "TEXT")
    private String specialInstructions;

    private String salesPerson;  // Track who made the sale
    private Double totalAmount;

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
        if (status == null) {
            status = OrderStatus.PENDING;
        }
        if (paymentPlan == null) {
            paymentPlan = PaymentPlan.FULL_PAYMENT;
        }
    }

    // Helper methods
    public void addItem(Furniture furniture) {
        items.add(furniture);
        recalculateTotal();
    }

    public void removeItem(Furniture furniture) {
        items.remove(furniture);
        recalculateTotal();
    }

    private void recalculateTotal() {
        this.totalAmount = items.stream()
                .map(Furniture::getPrice)
               .reduce(0.0, Double::sum);
    }
}
